package org.phenoscape.scowl

import scala.collection.JavaConversions._
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationSubject
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLAnonymousIndividual
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLNamedObject
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom
import org.semanticweb.owlapi.model.OWLObjectComplementOf
import org.semanticweb.owlapi.model.OWLObjectExactCardinality
import org.semanticweb.owlapi.model.OWLObjectHasValue
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality
import org.semanticweb.owlapi.model.OWLObjectMinCardinality
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom
import org.semanticweb.owlapi.model.OWLObjectUnionOf
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom
import org.semanticweb.owlapi.model.OWLDataProperty
import org.semanticweb.owlapi.model.OWLEntity
import org.semanticweb.owlapi.model.OWLDeclarationAxiom
import org.semanticweb.owlapi.model.OWLObjectOneOf
import org.semanticweb.owlapi.model.OWLObjectHasSelf
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLLiteral
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.OWLDatatype
import org.semanticweb.owlapi.model.OWLDataRange
import org.semanticweb.owlapi.model.OWLDataComplementOf
import org.semanticweb.owlapi.model.OWLDataOneOf
import org.semanticweb.owlapi.model.OWLDataIntersectionOf
import org.semanticweb.owlapi.model.OWLDataUnionOf
import org.semanticweb.owlapi.vocab.OWLFacet
import org.semanticweb.owlapi.model.OWLFacetRestriction
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom
import org.semanticweb.owlapi.model.OWLDatatypeRestriction
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom

object OWL {

  private val factory = OWLManager.getOWLDataFactory

  def Ontology(iri: String, axioms: Set[OWLAxiom]): OWLOntology = OWLManager.createOWLOntologyManager().createOntology(axioms, IRI.create(iri))

  def Declare(entity: OWLEntity): OWLDeclarationAxiom = factory.getOWLDeclarationAxiom(entity)

  trait NamedObjectConstructor[T <: OWLNamedObject] {

    def apply(iri: IRI): T

    def apply(iri: String): T = apply(IRI.create(iri))

    def unapply(entity: T): Option[IRI] = Option(entity.getIRI)

  }

  object Class extends NamedObjectConstructor[OWLClass] {

    def apply(iri: IRI): OWLClass = factory.getOWLClass(iri)

  }

  def Individual(iri: IRI): OWLNamedIndividual = factory.getOWLNamedIndividual(iri)

  def Individual(iri: String): OWLNamedIndividual = Individual(IRI.create(iri))

  def Individual(): OWLAnonymousIndividual = factory.getOWLAnonymousIndividual()

  object ObjectProperty extends NamedObjectConstructor[OWLObjectProperty] {

    def apply(iri: IRI): OWLObjectProperty = factory.getOWLObjectProperty(iri)

  }

  object AnnotationProperty extends NamedObjectConstructor[OWLAnnotationProperty] {

    def apply(iri: IRI): OWLAnnotationProperty = factory.getOWLAnnotationProperty(iri)

  }

  object DataProperty extends NamedObjectConstructor[OWLDataProperty] {

    def apply(iri: IRI): OWLDataProperty = factory.getOWLDataProperty(iri)

  }

  def not(classExpression: OWLClassExpression): OWLObjectComplementOf = factory.getOWLObjectComplementOf(classExpression)

  def not(dataRange: OWLDataRange): OWLDataComplementOf = factory.getOWLDataComplementOf(dataRange)

  def oneOf(individuals: OWLNamedIndividual*): OWLObjectOneOf = factory.getOWLObjectOneOf(individuals.toSet)

  def oneOf(literals: OWLLiteral*): OWLDataOneOf = factory.getOWLDataOneOf(literals.toSet)

  implicit class ScowlClassExpression(val self: OWLClassExpression) extends AnyVal {

    def and(other: OWLClassExpression): OWLObjectIntersectionOf = factory.getOWLObjectIntersectionOf(self.asConjunctSet + other)

    def or(other: OWLClassExpression): OWLObjectUnionOf = factory.getOWLObjectUnionOf(self.asDisjunctSet + other)

    def SubClassOf(other: OWLClassExpression): OWLSubClassOfAxiom = factory.getOWLSubClassOfAxiom(self, other)

    def EquivalentTo(other: OWLClassExpression): OWLEquivalentClassesAxiom = factory.getOWLEquivalentClassesAxiom(self, other)

    def DisjointFrom(other: OWLClassExpression): OWLDisjointClassesAxiom = factory.getOWLDisjointClassesAxiom(self, other)

  }

  implicit class ScowlObjectOneOf(val self: OWLObjectOneOf) extends AnyVal {

    def +(ind: OWLIndividual) = factory.getOWLObjectOneOf(self.getIndividuals + ind)

  }

  implicit class ScowlDataRange(val self: OWLDataRange) extends AnyVal {

    def and(other: OWLDataRange): OWLDataIntersectionOf = (self, other) match {
      case (s: OWLDataIntersectionOf, o: OWLDataIntersectionOf) => factory.getOWLDataIntersectionOf(s.getOperands ++ o.getOperands)
      case (s: OWLDataIntersectionOf, _) => factory.getOWLDataIntersectionOf(s.getOperands + other)
      case (_, o: OWLDataIntersectionOf) => factory.getOWLDataIntersectionOf(o.getOperands + self)
      case _ => factory.getOWLDataIntersectionOf(Set(self, other))
    }

    def or(other: OWLDataRange): OWLDataUnionOf = (self, other) match {
      case (s: OWLDataUnionOf, o: OWLDataIntersectionOf) => factory.getOWLDataUnionOf(s.getOperands ++ o.getOperands)
      case (s: OWLDataUnionOf, _) => factory.getOWLDataUnionOf(s.getOperands + other)
      case (_, o: OWLDataUnionOf) => factory.getOWLDataUnionOf(o.getOperands + self)
      case _ => factory.getOWLDataUnionOf(Set(self, other))
    }

  }

  implicit class ScowlDataType(val self: OWLDatatype) extends AnyVal {

    def EquivalentTo(range: OWLDataRange): OWLDatatypeDefinitionAxiom = factory.getOWLDatatypeDefinitionAxiom(self, range)

    def |(facet: OWLFacetRestriction) = factory.getOWLDatatypeRestriction(self, facet)

    def |(facets: OWLFacetRestriction*) = factory.getOWLDatatypeRestriction(self, facets.toSet)

  }

  def <(value: OWLLiteral) = factory.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, value)

  def <=(value: OWLLiteral) = factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, value)

  def >(value: OWLLiteral) = factory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, value)

  def >=(value: OWLLiteral) = factory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, value)

  def <(value: Int) = factory.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, value)

  def <=(value: Int) = factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, value)

  def >(value: Int) = factory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, value)

  def >=(value: Int) = factory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, value)

  def <(value: Float) = factory.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, value)

  def <=(value: Float) = factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, value)

  def >(value: Float) = factory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, value)

  def >=(value: Float) = factory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, value)

  def <(value: Double) = factory.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, value)

  def <=(value: Double) = factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, value)

  def >(value: Double) = factory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, value)

  def >=(value: Double) = factory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, value)

  implicit class ScowlIndividual(val self: OWLIndividual) extends AnyVal {

    def Fact(property: OWLObjectPropertyExpression, value: OWLIndividual): OWLObjectPropertyAssertionAxiom = factory.getOWLObjectPropertyAssertionAxiom(property, self, value)

    def Fact(property: OWLDataPropertyExpression, value: OWLLiteral): OWLDataPropertyAssertionAxiom = factory.getOWLDataPropertyAssertionAxiom(property, self, value)

    def Fact(property: OWLDataPropertyExpression, value: String): OWLDataPropertyAssertionAxiom = factory.getOWLDataPropertyAssertionAxiom(property, self, value)

    def Fact(property: OWLDataPropertyExpression, value: Int): OWLDataPropertyAssertionAxiom = factory.getOWLDataPropertyAssertionAxiom(property, self, value)

    def Fact(property: OWLDataPropertyExpression, value: Float): OWLDataPropertyAssertionAxiom = factory.getOWLDataPropertyAssertionAxiom(property, self, value)

    def Fact(property: OWLDataPropertyExpression, value: Double): OWLDataPropertyAssertionAxiom = factory.getOWLDataPropertyAssertionAxiom(property, self, value)

    def Fact(property: OWLDataPropertyExpression, value: Boolean): OWLDataPropertyAssertionAxiom = factory.getOWLDataPropertyAssertionAxiom(property, self, value)

    def Facts(facts: (OWLObjectPropertyExpression, OWLIndividual)*): Set[OWLObjectPropertyAssertionAxiom] =
      (facts map { case (property, value) => factory.getOWLObjectPropertyAssertionAxiom(property, self, value) }).toSet

    def Type(owlClass: OWLClassExpression): OWLClassAssertionAxiom = factory.getOWLClassAssertionAxiom(owlClass, self)

    def +(other: OWLIndividual): OWLObjectOneOf = factory.getOWLObjectOneOf(self, other)

  }

  implicit class ScowlObjectProperty(val objectProperty: OWLObjectPropertyExpression) extends AnyVal {

    def some(classExpression: OWLClassExpression): OWLObjectSomeValuesFrom = factory.getOWLObjectSomeValuesFrom(objectProperty, classExpression)

    def only(classExpression: OWLClassExpression): OWLObjectAllValuesFrom = factory.getOWLObjectAllValuesFrom(objectProperty, classExpression)

    def exactly(cardinality: Int, classExpression: OWLClassExpression): OWLObjectExactCardinality = factory.getOWLObjectExactCardinality(cardinality, objectProperty, classExpression)

    def exactly(cardinality: Int): OWLObjectExactCardinality = factory.getOWLObjectExactCardinality(cardinality, objectProperty)

    def min(cardinality: Int, classExpression: OWLClassExpression): OWLObjectMinCardinality = factory.getOWLObjectMinCardinality(cardinality, objectProperty, classExpression)

    def min(cardinality: Int): OWLObjectMinCardinality = factory.getOWLObjectMinCardinality(cardinality, objectProperty)

    def max(cardinality: Int, classExpression: OWLClassExpression): OWLObjectMaxCardinality = factory.getOWLObjectMaxCardinality(cardinality, objectProperty, classExpression)

    def max(cardinality: Int): OWLObjectMaxCardinality = factory.getOWLObjectMaxCardinality(cardinality, objectProperty)

    def value(individual: OWLIndividual): OWLObjectHasValue = factory.getOWLObjectHasValue(objectProperty, individual)

    def o(property: OWLObjectPropertyExpression): ScowlPropertyChain = new ScowlPropertyChain(objectProperty, property)

    def SubPropertyChain(chain: ScowlPropertyChain): OWLSubPropertyChainOfAxiom = factory.getOWLSubPropertyChainOfAxiom(chain.properties, objectProperty)

    def Self: OWLObjectHasSelf = factory.getOWLObjectHasSelf(objectProperty)

  }

  implicit class ScowlDataProperty(val self: OWLDataPropertyExpression) extends AnyVal {

    def some(range: OWLDataRange): OWLDataSomeValuesFrom = factory.getOWLDataSomeValuesFrom(self, range)

    def only(range: OWLDataRange): OWLDataAllValuesFrom = factory.getOWLDataAllValuesFrom(self, range)

  }

  implicit class ScowlAnnotationSubject(val self: OWLAnnotationSubject) extends AnyVal {

    def Annotation(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAnnotationAssertionAxiom = factory.getOWLAnnotationAssertionAxiom(property, self, value)

    def Annotation(property: OWLAnnotationProperty, value: String): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

    def Annotation(property: OWLAnnotationProperty, value: Int): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

    def Annotation(property: OWLAnnotationProperty, value: Float): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

    def Annotation(property: OWLAnnotationProperty, value: Double): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

    def Annotation(property: OWLAnnotationProperty, value: Boolean): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

  }

  implicit class ScowlAxiom(val self: OWLAxiom) extends AnyVal {

    def Annotation(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAxiom = {
      self.getAnnotatedAxiom(Set(factory.getOWLAnnotation(property, value)))
    }

    def Annotation(property: OWLAnnotationProperty, value: OWLNamedObject): OWLAxiom = {
      self.getAnnotatedAxiom(Set(factory.getOWLAnnotation(property, value.getIRI)))
    }

    def Annotations(annotations: (OWLAnnotationProperty, OWLNamedObject)*): OWLAxiom = {
      self.getAnnotatedAxiom(annotations.map { case (property, value) => factory.getOWLAnnotation(property, value.getIRI) }.toSet[OWLAnnotation])
    }

  }

  implicit class ScowlNamedObject(val self: OWLNamedObject) extends AnyVal {

    def Annotation(property: OWLAnnotationProperty, value: String): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

    def Annotation(property: OWLAnnotationProperty, value: Int): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

    def Annotation(property: OWLAnnotationProperty, value: Float): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

    def Annotation(property: OWLAnnotationProperty, value: Double): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

    def Annotation(property: OWLAnnotationProperty, value: Boolean): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

    def Annotation(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAnnotationAssertionAxiom = factory.getOWLAnnotationAssertionAxiom(property, self.getIRI, value)

  }

  implicit class ScowlLiteralString(val self: String) extends AnyVal {

    def ^^(datatype: OWLDatatype): OWLLiteral = factory.getOWLLiteral(self, datatype)

    def ^^(datatypeIRI: IRI): OWLLiteral = factory.getOWLLiteral(self, factory.getOWLDatatype(datatypeIRI))

    def ^^(datatypeIRI: String): OWLLiteral = factory.getOWLLiteral(self, factory.getOWLDatatype(IRI.create(datatypeIRI)))

    /**
     * Create plain literal with language tag
     * '@' by itself is not a valid identifier in Scala
     */
    def @@(lang: String): OWLLiteral = factory.getOWLLiteral(self, lang)

  }

}