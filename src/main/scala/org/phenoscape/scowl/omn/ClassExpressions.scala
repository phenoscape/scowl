package org.phenoscape.scowl.omn

import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom
import org.semanticweb.owlapi.model.OWLObjectOneOf
import org.semanticweb.owlapi.model.OWLObjectUnionOf
import org.semanticweb.owlapi.model.OWLObjectComplementOf
import org.semanticweb.owlapi.model.OWLDataComplementOf
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom
import org.semanticweb.owlapi.model.OWLPropertyExpression
import org.semanticweb.owlapi.model.OWLDataOneOf
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
import org.semanticweb.owlapi.model.OWLDataRange
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLHasKeyAxiom
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLLiteral
import org.phenoscape.scowl.factory
import scala.collection.JavaConversions._
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.OWLObjectInverseOf

trait ClassExpressions {

  def not(classExpression: OWLClassExpression): OWLObjectComplementOf = factory.getOWLObjectComplementOf(classExpression)

  def not(dataRange: OWLDataRange): OWLDataComplementOf = factory.getOWLDataComplementOf(dataRange)

  def not(property: OWLObjectPropertyExpression, value: OWLIndividual): ScowlNegativeObjectPropertyValue = ScowlNegativeObjectPropertyValue(property, value)

  def not(property: OWLDataPropertyExpression, value: OWLLiteral): ScowlNegativeDataPropertyValue = ScowlNegativeDataPropertyValue(property, value)

  def oneOf(individuals: OWLNamedIndividual*): OWLObjectOneOf = factory.getOWLObjectOneOf(individuals.toSet)

  def oneOf(literals: OWLLiteral*): OWLDataOneOf = factory.getOWLDataOneOf(literals.toSet)

  def inverse(property: OWLObjectPropertyExpression): OWLObjectInverseOf = factory.getOWLObjectInverseOf(property)

}

