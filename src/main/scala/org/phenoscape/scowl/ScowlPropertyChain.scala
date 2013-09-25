package org.phenoscape.scowl

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression

class ScowlPropertyChain(val properties: OWLObjectPropertyExpression*) {

  def o(property: OWLObjectPropertyExpression): ScowlPropertyChain = {
    new ScowlPropertyChain((properties :+ property): _*);
  }

}