package org.phenoscape.scowl.omn

import scala.collection.JavaConversions._
import org.phenoscape.scowl.converters.Literalable
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataComplementOf
import org.semanticweb.owlapi.model.OWLDataOneOf
import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.OWLDataRange
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLLiteral
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLObjectComplementOf
import org.semanticweb.owlapi.model.OWLObjectInverseOf
import org.semanticweb.owlapi.model.OWLObjectOneOf
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.apibinding.OWLManager

trait ClassExpressions {
  
  private val factory = OWLManager.getOWLDataFactory

  def not(classExpression: OWLClassExpression): OWLObjectComplementOf = factory.getOWLObjectComplementOf(classExpression)

  def not(dataRange: OWLDataRange): OWLDataComplementOf = factory.getOWLDataComplementOf(dataRange)

  def not(property: OWLObjectPropertyExpression, value: OWLIndividual): ScowlNegativeObjectPropertyValue = ScowlNegativeObjectPropertyValue(property, value)

  def not[T: Literalable](property: OWLDataPropertyExpression, value: T): ScowlNegativeDataPropertyValue[T] = ScowlNegativeDataPropertyValue(property, value)

  def oneOf(individuals: OWLNamedIndividual*): OWLObjectOneOf = factory.getOWLObjectOneOf(individuals.toSet)

  def oneOf(literals: OWLLiteral*): OWLDataOneOf = factory.getOWLDataOneOf(literals.toSet)

  def inverse(property: OWLObjectPropertyExpression): OWLObjectInverseOf = factory.getOWLObjectInverseOf(property)

}

