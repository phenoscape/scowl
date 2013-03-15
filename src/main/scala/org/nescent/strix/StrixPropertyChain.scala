package org.nescent.strix

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression

class StrixPropertyChain(val properties: OWLObjectPropertyExpression*) {

	def o(property: OWLObjectPropertyExpression): StrixPropertyChain = {
			new StrixPropertyChain((properties :+ property):_*);
	}

}