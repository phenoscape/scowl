package org.phenoscape.scowl.omn

import org.phenoscape.scowl.converters.{SWRLDArgish, SWRLIArgish}
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.{IRI, SWRLBuiltInAtom, SWRLDifferentIndividualsAtom, SWRLSameIndividualAtom}
import org.semanticweb.owlapi.vocab.{SWRLBuiltInsVocabulary, SWRLVocabulary}

import scala.jdk.CollectionConverters._

trait SWRLAtoms {

  private val factory = OWLManager.getOWLDataFactory

  def sameAs[S: SWRLIArgish, O: SWRLIArgish](subj: S, obj: O): SWRLSameIndividualAtom = {
    val argishS = implicitly[SWRLIArgish[S]]
    val argishO = implicitly[SWRLIArgish[O]]
    factory.getSWRLSameIndividualAtom(argishS.toArgument(subj), argishO.toArgument(obj))
  }

  def differentFrom[S: SWRLIArgish, O: SWRLIArgish](subj: S, obj: O): SWRLDifferentIndividualsAtom = {
    val argishS = implicitly[SWRLIArgish[S]]
    val argishO = implicitly[SWRLIArgish[O]]
    factory.getSWRLDifferentIndividualsAtom(argishS.toArgument(subj), argishO.toArgument(obj))
  }

  val swrlbAbs = SWRLBuiltIn(SWRLBuiltInsVocabulary.ABS.getIRI)

}

case class SWRLBuiltIn(iri: IRI) {

  private val factory = OWLManager.getOWLDataFactory

  def apply[T: SWRLDArgish](args: T*): SWRLBuiltInAtom = {
    val argish = implicitly[SWRLDArgish[T]]
    factory.getSWRLBuiltInAtom(iri, args.map(argish.toArgument).asJava)
  }

}