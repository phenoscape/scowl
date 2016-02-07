package org.phenoscape.scowl.ofn

import scala.collection.JavaConversions._

import org.phenoscape.scowl.factory
import org.semanticweb.owlapi.model.OWLDataOneOf
import org.semanticweb.owlapi.model.OWLDatatype

import org.semanticweb.owlapi.model.OWLLiteral

trait DataExpressions {

  object ^^ {

    def unapply(literal: OWLLiteral): Option[(String, OWLDatatype)] = Option(literal.getLiteral, literal.getDatatype)

  }

  object @@ {

    def unapply(literal: OWLLiteral): Option[(String, String)] = Option(literal.getLiteral, literal.getLang)

  }

  object DataOneOf {

    def apply(literals: OWLLiteral*): OWLDataOneOf =
      apply(literals.toSet)

    def apply(literals: Set[_ <: OWLLiteral]): OWLDataOneOf =
      factory.getOWLDataOneOf(literals)

    def unapply(expression: OWLDataOneOf): Option[Set[_ <: OWLLiteral]] =
      Option(expression.getValues.toSet)

  }

}