package org.phenoscape.scowl.omn

import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.OWLLiteral

case class ScowlNegativeDataPropertyValue(property: OWLDataPropertyExpression, value: OWLLiteral)