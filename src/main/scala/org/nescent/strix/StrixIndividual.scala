package org.nescent.strix

import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLAnonymousIndividual

class StrixIndividual(val self: OWLIndividual) {

  val factory = OWLManager.getOWLDataFactory();

  def Fact(property: OWLObjectProperty, value: OWLIndividual): OWLObjectPropertyAssertionAxiom = {
    factory.getOWLObjectPropertyAssertionAxiom(property, self, value);
  }

  def Facts(facts: Tuple2[OWLObjectProperty, OWLIndividual]*): Set[OWLObjectPropertyAssertionAxiom] = {
    facts.map(fact => factory.getOWLObjectPropertyAssertionAxiom(fact._1, self, fact._2)).toSet;
  }

  def Type(owlClass: OWLClassExpression): OWLClassAssertionAxiom = {
    factory.getOWLClassAssertionAxiom(owlClass, self);
  }

}