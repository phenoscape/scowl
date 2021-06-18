package org.phenoscape.scowl.omn

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.{SWRLAtom, SWRLRule}

import scala.jdk.CollectionConverters._

case class ScowlSWRLConjunction(atoms: Set[SWRLAtom]) {

  val factory = OWLManager.getOWLDataFactory

  def ^(other: SWRLAtom): ScowlSWRLConjunction = ScowlSWRLConjunction(atoms + other)

  def -->(head: SWRLAtom): SWRLRule = factory.getSWRLRule(atoms.asJava, Set(head).asJava)

  def -->(head: ScowlSWRLConjunction): SWRLRule = factory.getSWRLRule(atoms.asJava, head.atoms.asJava)

}