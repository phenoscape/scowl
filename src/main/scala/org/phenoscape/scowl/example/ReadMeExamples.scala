package org.phenoscape.scowl.example

import org.phenoscape.scowl._
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.apibinding.OWLManager
import scala.collection.JavaConverters._
import org.semanticweb.owlapi.model.parameters.Imports

object ReadMeExamples {

  val factory = OWLManager.getOWLDataFactory

  /**
   * Scowl expressions use and return native OWL API objects
   */

  val hasParent = ObjectProperty("http://www.co-ode.org/roberts/family-tree.owl#hasParent")

  val isParentOf = ObjectProperty("http://www.co-ode.org/roberts/family-tree.owl#isParentOf")

  val isSiblingOf = ObjectProperty("http://www.co-ode.org/roberts/family-tree.owl#isSiblingOf")

  val Person = Class("http://www.co-ode.org/roberts/family-tree.owl#Person")

  val FirstCousin = Class("http://www.co-ode.org/roberts/family-tree.owl#FirstCousin")

  val axiom = FirstCousin EquivalentTo (Person and (hasParent some (Person and (isSiblingOf some (Person and (isParentOf some Person))))))

  factory.getOWLEquivalentClassesAxiom(FirstCousin,
    factory.getOWLObjectIntersectionOf(
      Person,
      factory.getOWLObjectSomeValuesFrom(hasParent, factory.getOWLObjectIntersectionOf(
        Person,
        factory.getOWLObjectSomeValuesFrom(isSiblingOf, factory.getOWLObjectIntersectionOf(
          Person,
          factory.getOWLObjectSomeValuesFrom(isParentOf, Person)))))))

  /**
   * Add some axioms and programmatically generated GCIs to an ontology
   */
  val manager = OWLManager.createOWLOntologyManager()
  val ontology = manager.createOntology()
  val PartOf = ObjectProperty("http://example.org/part_of")
  val HasPart = ObjectProperty("http://example.org/has_part")
  val DevelopsFrom = ObjectProperty("http://example.org/develops_from")
  val Eye = Class("http://example.org/eye")
  val Head = Class("http://example.org/head")
  val Tail = Class("http://example.org/tail")

  manager.addAxiom(ontology, Eye SubClassOf (PartOf some Head))
  manager.addAxiom(ontology, Eye SubClassOf (not(PartOf some Tail)))

  val gcis = for {
    term <- ontology.getClassesInSignature(Imports.INCLUDED).asScala
  } yield {
    (not(HasPart some term)) SubClassOf (not(HasPart some (DevelopsFrom some term)))
  }
  manager.addAxioms(ontology, gcis.toSet.asJava)

  /**
   * Using pattern matching extractors to implement negation normal form
   */
  def nnf(given: OWLClassExpression): OWLClassExpression = given match {
    case Class(_) => given
    case ObjectComplementOf(Class(_)) => given
    case ObjectComplementOf(ObjectComplementOf(expression)) => nnf(expression)
    case ObjectUnionOf(operands) => ObjectUnionOf(operands.map(nnf))
    case ObjectIntersectionOf(operands) => ObjectIntersectionOf(operands.map(nnf))
    case ObjectComplementOf(ObjectUnionOf(operands)) => ObjectIntersectionOf(operands.map(c => nnf(ObjectComplementOf(c))))
    case ObjectComplementOf(ObjectIntersectionOf(operands)) => ObjectUnionOf(operands.map(c => nnf(ObjectComplementOf(c))))
    case ObjectAllValuesFrom(property, filler) => ObjectAllValuesFrom(property, nnf(filler))
    case ObjectSomeValuesFrom(property, filler) => ObjectSomeValuesFrom(property, nnf(filler))
    case ObjectMinCardinality(num, property, filler) => ObjectMinCardinality(num, property, nnf(filler))
    case ObjectMaxCardinality(num, property, filler) => ObjectMaxCardinality(num, property, nnf(filler))
    case ObjectExactCardinality(num, property, filler) => ObjectExactCardinality(num, property, nnf(filler))
    case ObjectComplementOf(ObjectAllValuesFrom(property, filler)) => ObjectSomeValuesFrom(property, nnf(ObjectComplementOf(filler)))
    case ObjectComplementOf(ObjectSomeValuesFrom(property, filler)) => ObjectAllValuesFrom(property, nnf(ObjectComplementOf(filler)))
    case ObjectComplementOf(ObjectMinCardinality(num, property, filler)) => ObjectMaxCardinality(math.max(num - 1, 0), property, filler)
    case ObjectComplementOf(ObjectMaxCardinality(num, property, filler)) => ObjectMinCardinality(num + 1, property, filler)
    case ObjectComplementOf(ObjectExactCardinality(num, property, filler)) => ObjectUnionOf(ObjectMinCardinality(num + 1, property, filler), ObjectMaxCardinality(math.max(num - 1, 0), property, filler))
    case _ => ???
  }

  /**
   * Using pattern matching extractors in for comprehensions
   */
  // Print all properties and fillers used in existential restrictions in subclass axioms
  for {
    SubClassOf(_, subclass, ObjectSomeValuesFrom(property, filler)) <- ontology.getAxioms(Imports.INCLUDED).asScala
  } yield {
    println(s"$property $filler")
  }

  // Make an index of language tags to label values
  val langValuePairs = for {
    AnnotationAssertion(_, RDFSLabel, _, value @@ Some(lang)) <- ontology.getAxioms(Imports.INCLUDED).asScala
  } yield {
    lang -> value
  }
  val langToValues: Map[String, Set[String]] = langValuePairs.foldLeft(Map.empty[String, Set[String]]) {
    case (langIndex, (lang, value)) =>
      langIndex.updated(lang, langIndex.getOrElse(value, Set.empty) ++ Set(value))
  }

}