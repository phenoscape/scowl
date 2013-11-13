package org.phenoscape.scowl

import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLAnonymousIndividual

class ScowlIndividual(val self: OWLIndividual) {

  val factory = OWLManager.getOWLDataFactory

  def Fact(property: OWLObjectProperty, value: OWLIndividual): OWLObjectPropertyAssertionAxiom = factory.getOWLObjectPropertyAssertionAxiom(property, self, value)

  def Facts(facts: (OWLObjectProperty, OWLIndividual)*): Set[OWLObjectPropertyAssertionAxiom] =
    (facts map { case (property, value) => factory.getOWLObjectPropertyAssertionAxiom(property, self, value) }).toSet

  def Type(owlClass: OWLClassExpression): OWLClassAssertionAxiom = factory.getOWLClassAssertionAxiom(owlClass, self)

}