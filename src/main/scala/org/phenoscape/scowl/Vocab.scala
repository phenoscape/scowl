package org.phenoscape.scowl

import org.semanticweb.owlapi.apibinding.OWLManager

trait Vocab {

  val XSDInteger = factory.getIntegerOWLDatatype

  val XSDFloat = factory.getFloatOWLDatatype

  val XSDDouble = factory.getDoubleOWLDatatype

  val XSDBoolean = factory.getBooleanOWLDatatype

  val OWLThing = factory.getOWLThing

  val OWLNothing = factory.getOWLNothing

  val RDFSLabel = factory.getRDFSLabel

}