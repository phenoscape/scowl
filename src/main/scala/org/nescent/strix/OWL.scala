package org.nescent.strix

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLObjectComplementOf
import org.semanticweb.owlapi.model.OWLObjectProperty

object OWL {

	def Class(iri: IRI): OWLClass = {
			return OWLManager.getOWLDataFactory().getOWLClass(iri);
	}

	def Class(iri: String): OWLClass = {
			return Class(IRI.create(iri));
	}
	
	def ObjectProperty(iri: IRI): OWLObjectProperty = {
			return OWLManager.getOWLDataFactory().getOWLObjectProperty(iri);
	}

	def ObjectProperty(iri: String): OWLObjectProperty = {
			return ObjectProperty(IRI.create(iri));
	}

	implicit def OWLObjectPropertyToProperty(value: OWLObjectProperty) = { new StrixObjectProperty(value); }

	implicit def OWLClassExpressionToClassExpression(value: OWLClassExpression) = { new StrixClassExpression(value); }

	def not(classExpression: OWLClassExpression): OWLObjectComplementOf = {
		OWLManager.getOWLDataFactory().getOWLObjectComplementOf(classExpression);
	}

}