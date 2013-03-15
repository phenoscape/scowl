package org.nescent.strix

import scala.collection.JavaConversions._
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom
import org.semanticweb.owlapi.model.OWLObjectExactCardinality
import org.semanticweb.owlapi.model.OWLObjectHasValue
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom

class StrixObjectProperty(val objectProperty: OWLObjectPropertyExpression) {

	def some(classExpression: OWLClassExpression): OWLObjectSomeValuesFrom = {
			OWLManager.getOWLDataFactory().getOWLObjectSomeValuesFrom(objectProperty, classExpression);
	}

	def only(classExpression: OWLClassExpression): OWLObjectAllValuesFrom = {
			OWLManager.getOWLDataFactory().getOWLObjectAllValuesFrom(objectProperty, classExpression);
	}

	def exactly(cardinality: Int, classExpression: OWLClassExpression): OWLObjectExactCardinality = {
			OWLManager.getOWLDataFactory().getOWLObjectExactCardinality(cardinality, objectProperty, classExpression);
	}

	def value(individual: OWLIndividual): OWLObjectHasValue = {
			OWLManager.getOWLDataFactory().getOWLObjectHasValue(objectProperty, individual);
	}

	def o(property: OWLObjectPropertyExpression): StrixPropertyChain = {
			new StrixPropertyChain(objectProperty, property);
	}

	def SubPropertyChain(chain: StrixPropertyChain): OWLSubPropertyChainOfAxiom = {
			OWLManager.getOWLDataFactory().getOWLSubPropertyChainOfAxiom(chain.properties, objectProperty);
	}

}