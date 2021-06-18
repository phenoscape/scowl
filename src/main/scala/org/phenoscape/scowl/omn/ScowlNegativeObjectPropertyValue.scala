package org.phenoscape.scowl.omn

import org.semanticweb.owlapi.model.{OWLIndividual, OWLObjectPropertyExpression}

case class ScowlNegativeObjectPropertyValue(property: OWLObjectPropertyExpression, value: OWLIndividual) 