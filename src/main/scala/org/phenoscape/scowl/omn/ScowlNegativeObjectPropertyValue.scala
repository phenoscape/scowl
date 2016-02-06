package org.phenoscape.scowl.omn

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLIndividual

case class ScowlNegativeObjectPropertyValue(property: OWLObjectPropertyExpression, value: OWLIndividual) 