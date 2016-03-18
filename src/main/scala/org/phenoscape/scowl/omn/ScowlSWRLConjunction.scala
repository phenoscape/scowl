package org.phenoscape.scowl.omn

import org.semanticweb.owlapi.model.SWRLAtom
import org.semanticweb.owlapi.model.SWRLRule
import org.semanticweb.owlapi.apibinding.OWLManager
import scala.collection.JavaConversions._

case class ScowlSWRLConjunction(atoms: Set[SWRLAtom]) {

  val factory = OWLManager.getOWLDataFactory

  def ^(other: SWRLAtom): ScowlSWRLConjunction = ScowlSWRLConjunction(atoms + other)

  def -->(head: SWRLAtom): SWRLRule = factory.getSWRLRule(atoms, Set(head))

  def -->(head: ScowlSWRLConjunction): SWRLRule = factory.getSWRLRule(atoms, head.atoms)
  
}