package org.phenoscape.scowl.ofn

import scala.collection.JavaConversions._

import org.phenoscape.scowl.factory
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom

import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom

trait IndividualAxioms {

  object ObjectPropertyAssertion extends PropertyAssertionAxiom[OWLObjectPropertyAssertionAxiom] {

    def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): OWLObjectPropertyAssertionAxiom =
      factory.getOWLObjectPropertyAssertionAxiom(property, subject, target, annotations)

  }

  object NegativeObjectPropertyAssertion extends PropertyAssertionAxiom[OWLNegativeObjectPropertyAssertionAxiom] {

    def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): OWLNegativeObjectPropertyAssertionAxiom =
      factory.getOWLNegativeObjectPropertyAssertionAxiom(property, subject, target, annotations)

  }

  object ClassAssertion {

    def apply(annotations: Set[OWLAnnotation], classExpression: OWLClassExpression, individual: OWLIndividual): OWLClassAssertionAxiom =
      factory.getOWLClassAssertionAxiom(classExpression, individual, annotations)

    def apply(classExpression: OWLClassExpression, individual: OWLIndividual): OWLClassAssertionAxiom =
      ClassAssertion(Set.empty, classExpression, individual)

    def apply(annotations: OWLAnnotation*)(classExpression: OWLClassExpression, individual: OWLIndividual): OWLClassAssertionAxiom =
      ClassAssertion(annotations.toSet, classExpression, individual)

    def unapply(axiom: OWLClassAssertionAxiom): Option[(Set[OWLAnnotation], OWLClassExpression, OWLIndividual)] =
      Option((axiom.getAnnotations.toSet, axiom.getClassExpression, axiom.getIndividual))

  }

}

trait PropertyAssertionAxiom[T <: OWLPropertyAssertionAxiom[OWLObjectPropertyExpression, OWLIndividual]] {

  def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): T

  def apply(annotations: OWLAnnotation*)(property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): T =
    apply(annotations.toSet, property, subject, target)

  def apply(property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): T =
    apply(Set.empty, property, subject, target)

  def unapply(axiom: T): Option[(Set[OWLAnnotation], OWLObjectPropertyExpression, OWLIndividual, OWLIndividual)] =
    Option((axiom.getAnnotations.toSet, axiom.getProperty, axiom.getSubject, axiom.getObject))

}