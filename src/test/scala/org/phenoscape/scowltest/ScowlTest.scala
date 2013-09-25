package org.phenoscape.scowltest

import scala.collection.JavaConversions._

import org.junit.Test
import org.junit.Assert
import org.phenoscape.scowl.OWL._
import org.semanticweb.owlapi.apibinding.OWLManager

class ScowlTest {

  @Test
  def testIntersectionOf(): Unit = {
    val class1 = Class("http://example.org/class1");
    val class2 = Class("http://example.org/class2");
    val oneAndTwo = class1 and class2;
    val longOneAndTwo = OWLManager.getOWLDataFactory().getOWLObjectIntersectionOf(class1, class2);
    Assert.assertEquals(longOneAndTwo, oneAndTwo);
  }

  @Test
  def testComplementOf(): Unit = {
    val class1 = Class("http://example.org/class1");
    val notClass1 = not(class1);
    val longNotClass1 = OWLManager.getOWLDataFactory().getOWLObjectComplementOf(class1);
    Assert.assertEquals(longNotClass1, notClass1);
  }

  @Test
  def testComplexExpressions(): Unit = {
    val prop = ObjectProperty("http://example.org/prop");
    val class1 = Class("http://example.org/class1");
    val class2 = Class("http://example.org/class2");
    val factory = OWLManager.getOWLDataFactory();
    val longClass = factory.getOWLObjectIntersectionOf(class1,
      factory.getOWLObjectComplementOf(
        factory.getOWLObjectSomeValuesFrom(prop, class2)));
    val class1AndNotPropClass2a = class1 and not(prop some class2);
    val class1AndNotPropClass2b = class1 and (not(prop some class2));
    Assert.assertEquals(longClass, class1AndNotPropClass2a);
    Assert.assertEquals(longClass, class1AndNotPropClass2b);
  }

  @Test
  def testSubClassOf(): Unit = {
    val factory = OWLManager.getOWLDataFactory();
    val class1 = Class("http://example.org/class1");
    val class2 = Class("http://example.org/class2");
    val prop = ObjectProperty("http://example.org/prop");
    val axiom1 = class1 SubClassOf class2;
    val longAxiom1 = factory.getOWLSubClassOfAxiom(class1, class2);
    Assert.assertEquals(longAxiom1, axiom1);
  }

  @Test
  def testPropertyChains(): Unit = {
    val factory = OWLManager.getOWLDataFactory();
    val prop1 = ObjectProperty("http://example.org/prop1");
    val prop2 = ObjectProperty("http://example.org/prop2");
    val prop3 = ObjectProperty("http://example.org/prop3");
    val prop4 = ObjectProperty("http://example.org/prop4");
    val chainAxiom1 = prop4 SubPropertyChain (prop1 o prop2);
    val longChainAxiom1 = factory.getOWLSubPropertyChainOfAxiom(List(prop1, prop2), prop4);
    Assert.assertEquals(longChainAxiom1, chainAxiom1);
    val chainAxiom2 = prop4 SubPropertyChain (prop1 o prop2 o prop3);
    val longChainAxiom2 = factory.getOWLSubPropertyChainOfAxiom(List(prop1, prop2, prop3), prop4);
    Assert.assertEquals(longChainAxiom2, chainAxiom2);
  }

  @Test
  def testPropertyAssertions(): Unit = {
    val factory = OWLManager.getOWLDataFactory();
    val rdfsLabel = factory.getRDFSLabel();
    val prop1 = ObjectProperty("http://example.org/prop1");
    val ind1 = Individual("http://example.org/ind1");
    val ind2 = Individual("http://example.org/ind2");
    ind1 Fact (prop1, ind2); //TODO
    (ind1 Fact (prop1, ind2)) Annotation (rdfsLabel, Individual());
  }

}