package org.phenoscape.scowltest

import scala.collection.JavaConversions._

import org.junit.Test
import org.junit.Assert
import org.phenoscape.scowl._
import org.semanticweb.owlapi.apibinding.OWLManager

class ScowlTest {

  val factory = OWLManager.getOWLDataFactory

  @Test
  def testIntersectionOf(): Unit = {
    val class1 = Class("http://example.org/class1")
    val class2 = Class("http://example.org/class2")
    val class3 = Class("http://example.org/class3")
    val oneAndTwo = class1 and class2
    val longOneAndTwo = factory.getOWLObjectIntersectionOf(class1, class2)
    Assert.assertEquals(longOneAndTwo, oneAndTwo)
    val oneAndTwoAndThree = class1 and class2 and class3
    val longOneAndTwoAndThree = factory.getOWLObjectIntersectionOf(class1, class2, class3)
    Assert.assertEquals(longOneAndTwoAndThree, oneAndTwoAndThree)
  }

  @Test
  def testComplementOf(): Unit = {
    val class1 = Class("http://example.org/class1")
    val notClass1 = not(class1)
    val longNotClass1 = factory.getOWLObjectComplementOf(class1)
    Assert.assertEquals(longNotClass1, notClass1)
  }

  @Test
  def testOneOf(): Unit = {
    val ind1 = Individual("http://example.org/ind1")
    val ind2 = Individual("http://example.org/ind2")
    val ind3 = Individual("http://example.org/ind3")
    val oneAndTwo = oneOf(ind1, ind2)
    val oneAndTwoAndThree = ind1 + ind2 + ind3
    val longOneAndTwo = factory.getOWLObjectOneOf(ind1, ind2)
    Assert.assertEquals(longOneAndTwo, oneAndTwo)
    Assert.assertEquals(factory.getOWLObjectOneOf(ind1), oneOf(ind1))
  }

  @Test
  def testComplexExpressions(): Unit = {
    val prop = ObjectProperty("http://example.org/prop")
    val class1 = Class("http://example.org/class1")
    val class2 = Class("http://example.org/class2")
    val longClass = factory.getOWLObjectIntersectionOf(class1,
      factory.getOWLObjectComplementOf(
        factory.getOWLObjectSomeValuesFrom(prop, class2)))
    val class1AndNotPropClass2a = class1 and not(prop some class2)
    val class1AndNotPropClass2b = class1 and (not(prop some class2))
    Assert.assertEquals(longClass, class1AndNotPropClass2a)
    Assert.assertEquals(longClass, class1AndNotPropClass2b)
  }

  @Test
  def testSubClassOf(): Unit = {
    val class1 = Class("http://example.org/class1")
    val class2 = Class("http://example.org/class2")
    val prop = ObjectProperty("http://example.org/prop")
    val axiom1 = class1 SubClassOf class2
    val longAxiom1 = factory.getOWLSubClassOfAxiom(class1, class2)
    Assert.assertEquals(longAxiom1, axiom1)
  }

  @Test
  def testHasSelf(): Unit = {
    val loves = ObjectProperty("http://example.org/loves")
    val Narcissist = Class("http://example.org/Narcissist")
    val narcissismAxiom = Narcissist EquivalentTo (loves Self)
    val longNarcissismAxiom = factory.getOWLEquivalentClassesAxiom(Narcissist, factory.getOWLObjectHasSelf(loves))
    Assert.assertEquals(longNarcissismAxiom, narcissismAxiom)
  }

  @Test
  def testPropertyChains(): Unit = {
    val prop1 = ObjectProperty("http://example.org/prop1")
    val prop2 = ObjectProperty("http://example.org/prop2")
    val prop3 = ObjectProperty("http://example.org/prop3")
    val prop4 = ObjectProperty("http://example.org/prop4")
    val chainAxiom1 = prop4 SubPropertyChain (prop1 o prop2)
    val longChainAxiom1 = factory.getOWLSubPropertyChainOfAxiom(List(prop1, prop2), prop4)
    Assert.assertEquals(longChainAxiom1, chainAxiom1)
    val chainAxiom2 = prop4 SubPropertyChain (prop1 o prop2 o prop3)
    val longChainAxiom2 = factory.getOWLSubPropertyChainOfAxiom(List(prop1, prop2, prop3), prop4)
    Assert.assertEquals(longChainAxiom2, chainAxiom2)
  }

  @Test
  def testPropertyAssertions(): Unit = {
    val rdfsLabel = factory.getRDFSLabel
    val prop1 = ObjectProperty("http://example.org/prop1")
    val ind1 = Individual("http://example.org/ind1")
    val ind2 = Individual("http://example.org/ind2")
    ind1 Fact (prop1, ind2) //TODO
    (ind1 Fact (prop1, ind2)) Annotation (rdfsLabel, Individual())
  }

  @Test
  def testDataPropertiesAndLiterals(): Unit = {
    val hasAge = DataProperty("http://example.org/has_age")
    val countryName = DataProperty("http://example.org/country_name")
    val ind1 = Individual("http://example.org/ind1")
    ind1 Fact (hasAge, "20" ^^ XSDInteger)
    ind1 Fact (hasAge, "20.5" ^^ XSDDouble)
    ind1 Fact (hasAge, 20.5)
    ind1 Fact (countryName, "Republic of France" @@ "en")
    ind1 Fact (countryName, "République française" @@ "fr")
    ind1 Annotation (RDFSLabel, "Robespierre" @@ "en")
    ind1 Annotation (RDFSLabel, "1" ^^ XSDInteger)

    ind1 Type (hasAge some (XSDInteger(>(1))))
    XSDInteger(>(1), <=(10)) //TODO

    val literalNumber = "20" ^^ XSDInteger
    val lexicalForm ^^ datatype = literalNumber
  }

  @Test
  def testKeys(): Unit = {
    val prop1 = ObjectProperty("http://example.org/prop1")
    val prop2 = ObjectProperty("http://example.org/prop2")
    val hasAge = DataProperty("http://example.org/has_age")
    val class1 = Class("http://example.org/class1")
    class1 HasKey (prop1)
    class1 HasKey (hasAge)
    class1 HasKey (prop1, hasAge)
  }

  @Test
  def testSameAndDifferent(): Unit = {
    val ind1 = Individual("http://example.org/ind1")
    val ind2 = Individual("http://example.org/ind2")
    val ind3 = Individual("http://example.org/ind3")
    ind1 SameAs ind2
    ind1 SameAs (ind2, ind3)
    ind1 DifferentFrom ind2
    ind1 DifferentFrom (ind2, ind3)

  }

}