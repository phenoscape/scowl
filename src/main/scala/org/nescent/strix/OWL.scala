package org.nescent.strix

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLObjectComplementOf
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLAnonymousIndividual
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLNamedObject
import org.semanticweb.owlapi.model.OWLAnnotationSubject

object OWL {

	val factory = OWLManager.getOWLDataFactory();

	def Class(iri: IRI): OWLClass = {
			return factory.getOWLClass(iri);
	}

	def Class(iri: String): OWLClass = {
			return Class(IRI.create(iri));
	}

	def Individual(iri: IRI): OWLNamedIndividual = {
			return factory.getOWLNamedIndividual(iri);
	}

	def Individual(iri: String): OWLNamedIndividual = {
			return Individual(IRI.create(iri));
	}

	def Individual(): OWLAnonymousIndividual = {
			return factory.getOWLAnonymousIndividual();
	}

	def ObjectProperty(iri: IRI): OWLObjectProperty = {
			return OWLManager.getOWLDataFactory().getOWLObjectProperty(iri);
	}

	def ObjectProperty(iri: String): OWLObjectProperty = {
			return ObjectProperty(IRI.create(iri));
	}

	implicit def OWLObjectPropertyToProperty(value: OWLObjectProperty) = { new StrixObjectProperty(value); }

	implicit def OWLClassExpressionToClassExpression(value: OWLClassExpression) = { new StrixClassExpression(value); }
	
	implicit def OWLIndividualToIndividual(value: OWLIndividual) = { new StrixIndividual(value); }
	
	implicit def OWLAxiomToStrixAxiom(value: OWLAxiom) = { new StrixAxiom(value); }
	
	implicit def OWLAnnotationSubjectToStrixAnnotationSubject(value: OWLAnnotationSubject) = { new StrixAnnotationSubject(value); }
	
	implicit def OWLNamedObjectToStrixNamedObject(value: OWLNamedObject) = { new StrixNamedObject(value); }

	def not(classExpression: OWLClassExpression): OWLObjectComplementOf = {
		OWLManager.getOWLDataFactory().getOWLObjectComplementOf(classExpression);
	}

}