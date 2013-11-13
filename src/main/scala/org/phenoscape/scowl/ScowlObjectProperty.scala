package org.phenoscape.scowl

import scala.collection.JavaConversions._
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom
import org.semanticweb.owlapi.model.OWLObjectExactCardinality
import org.semanticweb.owlapi.model.OWLObjectHasValue
import org.semanticweb.owlapi.model.OWLObjectMinCardinality
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality

class ScowlObjectProperty(val objectProperty: OWLObjectPropertyExpression) {
  
  val factory = OWLManager.getOWLDataFactory

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

}