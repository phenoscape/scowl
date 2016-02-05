package org.phenoscape.scowl.ofn

import org.semanticweb.owlapi.model.OWLLiteral
import org.semanticweb.owlapi.model.OWLDatatype

trait DataExpressions {

  object ^^ {

    def unapply(literal: OWLLiteral): Option[(String, OWLDatatype)] = Option(literal.getLiteral, literal.getDatatype)

  }

  object @@ {

    def unapply(literal: OWLLiteral): Option[(String, String)] = Option(literal.getLiteral, literal.getLang)

  }

}