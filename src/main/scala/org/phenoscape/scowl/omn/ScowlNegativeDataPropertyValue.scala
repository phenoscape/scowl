package org.phenoscape.scowl.omn

import org.phenoscape.scowl.converters.Literalable
import org.semanticweb.owlapi.model.{OWLDataPropertyExpression, OWLLiteral}

case class ScowlNegativeDataPropertyValue[T: Literalable](property: OWLDataPropertyExpression, value: T) {

  lazy val literal: OWLLiteral = {
    val literalable = implicitly[Literalable[T]]
    literalable.toLiteral(value)
  }

}

