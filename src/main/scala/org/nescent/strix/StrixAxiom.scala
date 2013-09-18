package org.nescent.strix

import scala.collection.JavaConversions._
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLAnnotationAxiom
import org.semanticweb.owlapi.model.OWLNamedObject

class StrixAxiom(val self: OWLAxiom) {

  val factory = OWLManager.getOWLDataFactory();

  def Annotation(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAxiom = {
    self.getAnnotatedAxiom(Set(factory.getOWLAnnotation(property, value)));
  }

  def Annotation(property: OWLAnnotationProperty, value: OWLNamedObject): OWLAxiom = {
    self.getAnnotatedAxiom(Set(factory.getOWLAnnotation(property, value.getIRI())));
  }

}