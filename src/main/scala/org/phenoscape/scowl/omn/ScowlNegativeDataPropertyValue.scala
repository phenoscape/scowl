package org.phenoscape.scowl.omn

import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.OWLLiteral
import org.phenoscape.scowl.Literalable

case class ScowlNegativeDataPropertyValue[T: Literalable](property: OWLDataPropertyExpression, value: T) {

  lazy val literal: OWLLiteral = {
    val literalable = implicitly[Literalable[T]]
    literalable.toLiteral(value)
  }

}

