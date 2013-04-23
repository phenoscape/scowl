package org.nescent.strix

import org.semanticweb.owlapi.model.OWLAnnotationSubject
import org.semanticweb.owlapi.model.OWLAnonymousIndividual
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.apibinding.OWLManager

class StrixAnnotationSubject(val self: OWLAnnotationSubject) {

	val factory = OWLManager.getOWLDataFactory();

	def Annotation(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAnnotationAssertionAxiom = {
			factory.getOWLAnnotationAssertionAxiom(property, self, value);
	}

}