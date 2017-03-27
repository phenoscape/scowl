package org.phenoscape.scowltest

import scala.collection.JavaConversions._
import scala.language.postfixOps

import org.phenoscape.scowl._
import org.scalatest._
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.vocab.OWLFacet
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary
import org.semanticweb.owlapi.vocab.XSDVocabulary
import org.semanticweb.owlapi.search.EntitySearcher

class ScowlTest extends UnitSpec {

  val factory = OWLManager.getOWLDataFactory
  val class1 = Class("http://example.org/class1")
  val class2 = Class("http://example.org/class2")
  val class3 = Class("http://example.org/class3")
  val ind1 = Individual("http://example.org/ind1")
  val ind2 = Individual("http://example.org/ind2")
  val ind3 = Individual("http://example.org/ind3")
  val prop = ObjectProperty("http://example.org/prop")
  val prop1 = ObjectProperty("http://example.org/prop1")
  val prop2 = ObjectProperty("http://example.org/prop2")
  val prop3 = ObjectProperty("http://example.org/prop3")
  val prop4 = ObjectProperty("http://example.org/prop4")
  val prop5 = DataProperty("http://example.org/prop5")

  "Scowl class expressions" should "equal standard API" in {
    (class1 and class2) should equal(factory.getOWLObjectIntersectionOf(class1, class2))
    (class1 and class2 and class3) should equal(factory.getOWLObjectIntersectionOf(class1, class2, class3))
    (org.phenoscape.scowl.not(class1)) should equal(factory.getOWLObjectComplementOf(class1))
    org.phenoscape.scowl.oneOf(ind1, ind2) should equal(factory.getOWLObjectOneOf(ind1, ind2))
    (ind1 ~ ind2 ~ ind3) should equal(factory.getOWLObjectOneOf(ind1, ind2, ind3))
    (class1 and org.phenoscape.scowl.not(prop some class2)) should equal(factory.getOWLObjectIntersectionOf(class1, factory.getOWLObjectComplementOf(factory.getOWLObjectSomeValuesFrom(prop, class2))))
  }

  "Scowl axioms" should "equal standard API" in {
    (class1 SubClassOf class2) should equal(factory.getOWLSubClassOfAxiom(class1, class2))
    val Narcissist = Class("http://example.org/Narcissist")
    val loves = ObjectProperty("http://example.org/loves")
    (Narcissist EquivalentTo (loves Self)) should equal(factory.getOWLEquivalentClassesAxiom(Narcissist, factory.getOWLObjectHasSelf(loves)))
    ((ind1 Fact (prop1, ind2)) Annotation (RDFSLabel, AnonymousIndividual("urn:anon:1"))) should equal(factory.getOWLObjectPropertyAssertionAxiom(prop1, ind1, ind2, Set(factory.getOWLAnnotation(factory.getRDFSLabel, factory.getOWLAnonymousIndividual("urn:anon:1")))))
    (ind1 SameAs ind2) should equal(factory.getOWLSameIndividualAxiom(ind1, ind2))
    (ind1 SameAs (ind2, ind3)) should equal(factory.getOWLSameIndividualAxiom(ind1, ind2, ind3))
    (ind1 DifferentFrom ind2) should equal(factory.getOWLDifferentIndividualsAxiom(ind1, ind2))
    ind1 DifferentFrom (ind2, ind3) should equal(factory.getOWLDifferentIndividualsAxiom(ind1, ind2, ind3))
  }

  "Scowl property chains" should "equal standard API" in {
    (prop4 SubPropertyChain (prop1 o prop2)) should equal(factory.getOWLSubPropertyChainOfAxiom(List(prop1, prop2), prop4))
    (prop4 SubPropertyChain (prop1 o prop2 o prop3)) should equal(factory.getOWLSubPropertyChainOfAxiom(List(prop1, prop2, prop3), prop4))
  }

  "Scowl data properties and literals" should "equal standard API" in {
    val hasAge = DataProperty("http://example.org/has_age")
    val countryName = DataProperty("http://example.org/country_name")
    (ind1 Fact (hasAge, "20" ^^ XSDInteger)) should equal(factory.getOWLDataPropertyAssertionAxiom(hasAge, ind1, factory.getOWLLiteral(20)))
    (ind1 Fact (hasAge, "20.5" ^^ XSDDouble)) should equal(factory.getOWLDataPropertyAssertionAxiom(hasAge, ind1, factory.getOWLLiteral(20.5)))
    (ind1 Fact (hasAge, 20.5)) should equal(factory.getOWLDataPropertyAssertionAxiom(hasAge, ind1, factory.getOWLLiteral(20.5)))
    (ind1 Fact (countryName, "Republic of France" @@ "en")) should equal(factory.getOWLDataPropertyAssertionAxiom(countryName, ind1, factory.getOWLLiteral("Republic of France", "en")))
    ind1 Fact (countryName, "République française" @@ "fr")
    (ind1 Annotation (RDFSLabel, "Robespierre" @@ "en")) should equal(factory.getOWLAnnotationAssertionAxiom(factory.getRDFSLabel, ind1.getIRI, factory.getOWLLiteral("Robespierre", "en")))
    ind1 Annotation (RDFSLabel, "1" ^^ XSDInteger)

    (ind1 Type (hasAge some (XSDInteger(org.phenoscape.scowl.>(1))))) should equal(factory.getOWLClassAssertionAxiom(factory.getOWLDataSomeValuesFrom(hasAge, factory.getOWLDatatypeMinExclusiveRestriction(1)), ind1))
    (XSDInteger(org.phenoscape.scowl.>(1), org.phenoscape.scowl.<=(10))) should equal(factory.getOWLDatatypeRestriction(factory.getOWLDatatype(XSDVocabulary.INTEGER.getIRI), factory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, 1), factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, 10)))

    val literalNumber = "20" ^^ XSDInteger
    val lexicalForm ^^ datatype = literalNumber
    datatype should equal(factory.getOWLDatatype(XSDVocabulary.INTEGER.getIRI))
    (class1 HasKey (prop1)) should equal(factory.getOWLHasKeyAxiom(class1, prop1))
    class1 HasKey (hasAge) should equal(factory.getOWLHasKeyAxiom(class1, hasAge))
    (class1 HasKey (prop1, hasAge)) should equal(factory.getOWLHasKeyAxiom(class1, prop1, hasAge))
  }

  "Extractors" should "extract the right things" in {
    val axioms = Set(
      class1 Annotation (RDFSLabel, "cat" @@ "en"),
      class1 Annotation (RDFSLabel, "chat" @@ "fr"),
      class2 Annotation (RDFSLabel, 42),
      class2 Annotation (RDFSComment, "hello"),
      class1 SubClassOf class2)
    val langValuePairs = for {
      AnnotationAssertion(_, RDFSLabel, _, value @@ Some(lang)) <- axioms
    } yield lang -> value
    langValuePairs should equal(Set("en" -> "cat", "fr" -> "chat"))

    val ont = OWLManager.createOWLOntologyManager().createOntology(Set[OWLAxiom](class1 Annotation (RDFSLabel, "cat" @@ "en")))
    val values = for {
      Annotation(_, _, value @@ _) <- EntitySearcher.getAnnotations(class1, ont)
    } yield value
    values.toSet should equal(Set("cat"))
  }

  "SWRL syntax" should "equal standard API" in {
    (((class1('x) ^ class1('y)) --> class2('x)) Annotation (RDFSLabel, "X and Y rule")) should equal(
      factory.getSWRLRule(
        Set(
          factory.getSWRLClassAtom(class1, factory.getSWRLVariable(IRI.create("urn:swrl#x"))),
          factory.getSWRLClassAtom(class1, factory.getSWRLVariable(IRI.create("urn:swrl#y")))),
        Set(
          factory.getSWRLClassAtom(class2, factory.getSWRLVariable(IRI.create("urn:swrl#x")))),
        Set(
          factory.getOWLAnnotation(factory.getRDFSLabel, factory.getOWLLiteral("X and Y rule")))))
    (prop1(ind1, 'x) ^ class1('x) ^ class2(ind1) ^ class1('y)) --> prop2('x, 'y)
    ((prop5(ind1, 42) ^ class1(ind1) ^ sameAs(ind1, ind2) ^ differentFrom(ind1, 'x) ^ swrlbAbs('x)) --> prop5(ind1, "text")) should equal(
      factory.getSWRLRule(
        Set(
          factory.getSWRLDataPropertyAtom(prop5, factory.getSWRLIndividualArgument(ind1), factory.getSWRLLiteralArgument(factory.getOWLLiteral(42))),
          factory.getSWRLClassAtom(class1, factory.getSWRLIndividualArgument(ind1)),
          factory.getSWRLSameIndividualAtom(factory.getSWRLIndividualArgument(ind1), factory.getSWRLIndividualArgument(ind2)),
          factory.getSWRLDifferentIndividualsAtom(factory.getSWRLIndividualArgument(ind1), factory.getSWRLVariable(IRI.create("urn:swrl#x"))),
          factory.getSWRLBuiltInAtom(SWRLBuiltInsVocabulary.ABS.getIRI, List(factory.getSWRLVariable(IRI.create("urn:swrl#x"))))),
        Set(
          factory.getSWRLDataPropertyAtom(prop5, factory.getSWRLIndividualArgument(ind1), factory.getSWRLLiteralArgument(factory.getOWLLiteral("text"))))))
  }

}