package org.phenoscape.scowl

import org.semanticweb.owlapi.model.OWLNamedObject
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom
import org.semanticweb.owlapi.apibinding.OWLManager

class ScowlNamedObject(val self: OWLNamedObject) {

  val factory = OWLManager.getOWLDataFactory

  def Annotation(property: OWLAnnotationProperty, value: String): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

  def Annotation(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAnnotationAssertionAxiom = factory.getOWLAnnotationAssertionAxiom(property, self.getIRI, value)

}