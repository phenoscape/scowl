package org.phenoscape.scowl

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.vocab.OWL2Datatype

trait Vocab {

  private val factory = OWLManager.getOWLDataFactory

  val XSDInteger = factory.getIntegerOWLDatatype

  val XSDFloat = factory.getFloatOWLDatatype

  val XSDDouble = factory.getDoubleOWLDatatype

  val XSDBoolean = factory.getBooleanOWLDatatype

  val XSDNonNegativeInteger = OWL2Datatype.XSD_NON_NEGATIVE_INTEGER.getDatatype(factory)

  val OWLThing = factory.getOWLThing

  val OWLNothing = factory.getOWLNothing

  val RDFSLabel = factory.getRDFSLabel()

  val RDFSComment = factory.getRDFSComment()

}