package org.phenoscape.scowl

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
import org.semanticweb.owlapi.model.OWLObjectUnionOf

class ScowlClassExpression(val classExpression: OWLClassExpression) {

  def and(other: OWLClassExpression): OWLObjectIntersectionOf = OWLManager.getOWLDataFactory().getOWLObjectIntersectionOf(classExpression, other)

  def or(other: OWLClassExpression): OWLObjectUnionOf = OWLManager.getOWLDataFactory().getOWLObjectUnionOf(classExpression, other)

  def SubClassOf(other: OWLClassExpression): OWLSubClassOfAxiom = OWLManager.getOWLDataFactory().getOWLSubClassOfAxiom(classExpression, other)

  def EquivalentTo(other: OWLClassExpression): OWLEquivalentClassesAxiom = OWLManager.getOWLDataFactory().getOWLEquivalentClassesAxiom(classExpression, other)

}