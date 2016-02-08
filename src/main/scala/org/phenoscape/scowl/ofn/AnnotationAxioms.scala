package org.phenoscape.scowl.ofn

import scala.collection.JavaConversions._

import org.phenoscape.scowl.factory
import org.phenoscape.scowl.Literalable
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom
import org.semanticweb.owlapi.model.OWLAnnotationProperty

import org.semanticweb.owlapi.model.OWLAnnotationSubject
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLDeclarationAxiom
import org.semanticweb.owlapi.model.OWLEntity

trait AnnotationAxioms {

  object Declaration {

    def apply(annotations: Set[OWLAnnotation], entity: OWLEntity): OWLDeclarationAxiom =
      factory.getOWLDeclarationAxiom(entity, annotations)

    def apply(annotations: OWLAnnotation*)(entity: OWLEntity): OWLDeclarationAxiom =
      Declaration(annotations.toSet, entity)

    def apply(entity: OWLEntity): OWLDeclarationAxiom =
      Declaration(Set.empty, entity)

    def unapply(axiom: OWLDeclarationAxiom): Option[(Set[OWLAnnotation], OWLEntity)] =
      Option((axiom.getAnnotations.toSet, axiom.getEntity))

  }

  object Annotation {

    def apply(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAnnotation =
      factory.getOWLAnnotation(property, value)

    def apply[T: Literalable](property: OWLAnnotationProperty, value: T): OWLAnnotation = {
      val literalable = implicitly[Literalable[T]]
      factory.getOWLAnnotation(property, literalable.toLiteral(value))
    }

    def unapply(annotation: OWLAnnotation): Option[(OWLAnnotationProperty, OWLAnnotationValue)] =
      Option((annotation.getProperty, annotation.getValue))

  }

  object AnnotationAssertion {

    def apply(annotations: Set[OWLAnnotation], property: OWLAnnotationProperty, subject: OWLAnnotationSubject, value: OWLAnnotationValue): OWLAnnotationAssertionAxiom =
      factory.getOWLAnnotationAssertionAxiom(property, subject, value, annotations)

    def apply(property: OWLAnnotationProperty, subject: OWLAnnotationSubject, value: OWLAnnotationValue): OWLAnnotationAssertionAxiom =
      AnnotationAssertion(Set.empty, property, subject, value)

    def apply(annotations: OWLAnnotation*)(property: OWLAnnotationProperty, subject: OWLAnnotationSubject, value: OWLAnnotationValue): OWLAnnotationAssertionAxiom = AnnotationAssertion(annotations.toSet, property, subject, value)

    def unapply(axiom: OWLAnnotationAssertionAxiom): Option[(Set[OWLAnnotation], OWLAnnotationProperty, OWLAnnotationSubject, OWLAnnotationValue)] =
      Option(axiom.getAnnotations.toSet, axiom.getProperty, axiom.getSubject, axiom.getValue)

  }

}