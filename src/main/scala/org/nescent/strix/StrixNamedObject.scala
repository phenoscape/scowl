package org.nescent.strix

import scala.collection.JavaConversions._
import org.semanticweb.owlapi.model.OWLNamedObject
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom
import org.semanticweb.owlapi.apibinding.OWLManager

class StrixNamedObject(val self: OWLNamedObject) {

	val factory = OWLManager.getOWLDataFactory();

	def Annotation(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAnnotationAssertionAxiom = {
			factory.getOWLAnnotationAssertionAxiom(property, self.getIRI(), value);
	}

}