package org.phenoscape.scowl

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
import org.semanticweb.owlapi.model.OWLObjectUnionOf

class ScowlClassExpression(val classExpression: OWLClassExpression) {

  val factory = OWLManager.getOWLDataFactory

  def and(other: OWLClassExpression): OWLObjectIntersectionOf = factory.getOWLObjectIntersectionOf(classExpression, other)

  def or(other: OWLClassExpression): OWLObjectUnionOf = factory.getOWLObjectUnionOf(classExpression, other)

  def SubClassOf(other: OWLClassExpression): OWLSubClassOfAxiom = factory.getOWLSubClassOfAxiom(classExpression, other)

  def EquivalentTo(other: OWLClassExpression): OWLEquivalentClassesAxiom = factory.getOWLEquivalentClassesAxiom(classExpression, other)

}