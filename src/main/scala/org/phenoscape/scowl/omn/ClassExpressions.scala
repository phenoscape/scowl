package org.phenoscape.scowl.omn

import org.phenoscape.scowl.converters.Literalable
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model._

import scala.jdk.CollectionConverters._

trait ClassExpressions {

  private val factory = OWLManager.getOWLDataFactory

  def not(classExpression: OWLClassExpression): OWLObjectComplementOf = factory.getOWLObjectComplementOf(classExpression)

  def not(dataRange: OWLDataRange): OWLDataComplementOf = factory.getOWLDataComplementOf(dataRange)

  def not(property: OWLObjectPropertyExpression, value: OWLIndividual): ScowlNegativeObjectPropertyValue = ScowlNegativeObjectPropertyValue(property, value)

  def not[T: Literalable](property: OWLDataPropertyExpression, value: T): ScowlNegativeDataPropertyValue[T] = ScowlNegativeDataPropertyValue(property, value)

  def oneOf(individuals: OWLNamedIndividual*): OWLObjectOneOf = factory.getOWLObjectOneOf(individuals.toSet.asJava)

  def oneOf(literals: OWLLiteral*): OWLDataOneOf = factory.getOWLDataOneOf(literals.toSet.asJava)

  def inverse(property: OWLObjectPropertyExpression): OWLObjectInverseOf = factory.getOWLObjectInverseOf(property)

}

