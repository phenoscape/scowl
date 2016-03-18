package org.phenoscape.scowl.converters

import org.semanticweb.owlapi.model.SWRLIArgument
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.SWRLIndividualArgument
import org.semanticweb.owlapi.model.SWRLVariable
import org.semanticweb.owlapi.model.SWRLDArgument
import org.semanticweb.owlapi.model.OWLLiteral
import org.semanticweb.owlapi.model.SWRLLiteralArgument

trait SWRLArgs {

  private val factory = OWLManager.getOWLDataFactory

  implicit object SymbolArgish extends SWRLIArgish[Symbol] with SWRLDArgish[Symbol] {

    val iriPrefix = "urn:swrl#"

    def toArgument(arg: Symbol): SWRLVariable = factory.getSWRLVariable(IRI.create(s"$iriPrefix${arg.toString}"))

  }

  implicit object IndividualArgish extends SWRLIArgish[OWLIndividual] {

    def toArgument(arg: OWLIndividual): SWRLIndividualArgument = factory.getSWRLIndividualArgument(arg)

  }

  implicit object VariableArgish extends SWRLIArgish[SWRLVariable] with SWRLDArgish[SWRLVariable] {

    def toArgument(arg: SWRLVariable): SWRLVariable = arg

  }

}

trait SWRLIArgish[-T] {

  def toArgument(arg: T): SWRLIArgument

}

trait SWRLDArgish[-T] {

  def toArgument(arg: T): SWRLDArgument

}