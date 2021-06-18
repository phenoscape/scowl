package org.phenoscape.scowl.ofn

import org.phenoscape.scowl.converters.Literalable
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model._

import scala.jdk.CollectionConverters._

trait IndividualAxioms {
  
  private val factory = OWLManager.getOWLDataFactory

  object ObjectPropertyAssertion extends ObjectPropertyAssertionAxiom[OWLObjectPropertyAssertionAxiom] {

    def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): OWLObjectPropertyAssertionAxiom =
      factory.getOWLObjectPropertyAssertionAxiom(property, subject, target, annotations.asJava)

  }

  object NegativeObjectPropertyAssertion extends ObjectPropertyAssertionAxiom[OWLNegativeObjectPropertyAssertionAxiom] {

    def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): OWLNegativeObjectPropertyAssertionAxiom =
      factory.getOWLNegativeObjectPropertyAssertionAxiom(property, subject, target, annotations.asJava)

  }

  object DataPropertyAssertion extends DataPropertyAssertionAxiom[OWLDataPropertyAssertionAxiom] {

    def apply[V: Literalable](annotations: Set[OWLAnnotation], property: OWLDataPropertyExpression, subject: OWLIndividual, value: V): OWLDataPropertyAssertionAxiom = {
      val literalable = implicitly[Literalable[V]]
      factory.getOWLDataPropertyAssertionAxiom(property, subject, literalable.toLiteral(value), annotations.asJava)
    }

  }

  object NegativeDataPropertyAssertion extends DataPropertyAssertionAxiom[OWLNegativeDataPropertyAssertionAxiom] {

    def apply[V: Literalable](annotations: Set[OWLAnnotation], property: OWLDataPropertyExpression, subject: OWLIndividual, value: V): OWLNegativeDataPropertyAssertionAxiom = {
      val literalable = implicitly[Literalable[V]]
      factory.getOWLNegativeDataPropertyAssertionAxiom(property, subject, literalable.toLiteral(value), annotations.asJava)
    }

  }

  object ClassAssertion {

    def apply(annotations: Set[OWLAnnotation], classExpression: OWLClassExpression, individual: OWLIndividual): OWLClassAssertionAxiom =
      factory.getOWLClassAssertionAxiom(classExpression, individual, annotations.asJava)

    def apply(classExpression: OWLClassExpression, individual: OWLIndividual): OWLClassAssertionAxiom =
      ClassAssertion(Set.empty, classExpression, individual)

    def apply(annotations: OWLAnnotation*)(classExpression: OWLClassExpression, individual: OWLIndividual): OWLClassAssertionAxiom =
      ClassAssertion(annotations.toSet, classExpression, individual)

    def unapply(axiom: OWLClassAssertionAxiom): Option[(Set[OWLAnnotation], OWLClassExpression, OWLIndividual)] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getClassExpression, axiom.getIndividual)

  }

  object SameIndividual extends NaryIndividualsAxiom[OWLSameIndividualAxiom] {

    def apply(annotations: Set[OWLAnnotation], individuals: Set[_ <: OWLIndividual]): OWLSameIndividualAxiom =
      factory.getOWLSameIndividualAxiom(individuals.toSet.asJava, annotations.toSet.asJava)

  }

  object DifferentIndividuals extends NaryIndividualsAxiom[OWLDifferentIndividualsAxiom] {

    def apply(annotations: Set[OWLAnnotation], individuals: Set[_ <: OWLIndividual]): OWLDifferentIndividualsAxiom =
      factory.getOWLDifferentIndividualsAxiom(individuals.toSet.asJava, annotations.toSet.asJava)

  }

}

trait ObjectPropertyAssertionAxiom[T <: OWLPropertyAssertionAxiom[OWLObjectPropertyExpression, OWLIndividual]] {

  def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): T

  def apply(annotations: OWLAnnotation*)(property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): T =
    apply(annotations.toSet, property, subject, target)

  def apply(property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): T =
    apply(Set.empty, property, subject, target)

  def unapply(axiom: T): Option[(Set[OWLAnnotation], OWLObjectPropertyExpression, OWLIndividual, OWLIndividual)] =
    Option(axiom.getAnnotations.asScala.toSet, axiom.getProperty, axiom.getSubject, axiom.getObject)

}

trait DataPropertyAssertionAxiom[T <: OWLPropertyAssertionAxiom[OWLDataPropertyExpression, OWLLiteral]] {

  def apply[V: Literalable](annotations: Set[OWLAnnotation], property: OWLDataPropertyExpression, subject: OWLIndividual, value: V): T

  def apply[V: Literalable](annotations: OWLAnnotation*)(property: OWLDataPropertyExpression, subject: OWLIndividual, value: V): T =
    apply(annotations.toSet, property, subject, value)

  def apply[V: Literalable](property: OWLDataPropertyExpression, subject: OWLIndividual, value: V): T =
    apply(Set.empty, property, subject, value)

  def unapply(axiom: T): Option[(Set[OWLAnnotation], OWLDataPropertyExpression, OWLIndividual, OWLLiteral)] =
    Option(axiom.getAnnotations.asScala.toSet, axiom.getProperty, axiom.getSubject, axiom.getObject)

}

trait NaryIndividualsAxiom[T <: OWLNaryIndividualAxiom] {

  def apply(annotations: Set[OWLAnnotation], individuals: Set[_ <: OWLIndividual]): T

  def apply(individuals: OWLIndividual*): T =
    apply(Set.empty[OWLAnnotation], individuals.toSet)

  def apply(individuals: Set[_ <: OWLIndividual]): T =
    apply(Set.empty[OWLAnnotation], individuals)

  def apply(annotations: OWLAnnotation*)(individuals: OWLIndividual*): T =
    apply(annotations.toSet, individuals.toSet)

  def unapply(axiom: T): Option[(Set[OWLAnnotation], Set[OWLIndividual])] =
    Option(axiom.getAnnotations.asScala.toSet, axiom.getIndividuals.asScala.toSet)

}