package org.phenoscape.scowl.ofn

import org.phenoscape.scowl.converters.{Annotatable, AnnotationValuer}
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model._

import scala.jdk.CollectionConverters._

trait AnnotationAxioms {

  private val factory = OWLManager.getOWLDataFactory

  object Declaration {

    def apply(annotations: Set[OWLAnnotation], entity: OWLEntity): OWLDeclarationAxiom =
      factory.getOWLDeclarationAxiom(entity, annotations.asJava)

    def apply(annotations: OWLAnnotation*)(entity: OWLEntity): OWLDeclarationAxiom =
      Declaration(annotations.toSet, entity)

    def apply(entity: OWLEntity): OWLDeclarationAxiom =
      Declaration(Set.empty, entity)

    def unapply(axiom: OWLDeclarationAxiom): Option[(Set[OWLAnnotation], OWLEntity)] =
      Option((axiom.getAnnotations.asScala.toSet, axiom.getEntity))

  }

  object Annotation {

    def apply[T: AnnotationValuer](annotations: Set[OWLAnnotation], property: OWLAnnotationProperty, value: T): OWLAnnotation = {
      val valuer = implicitly[AnnotationValuer[T]]
      factory.getOWLAnnotation(property, valuer.toAnnotationValue(value), annotations.asJava)
    }

    def apply[T: AnnotationValuer](annotations: OWLAnnotation*)(property: OWLAnnotationProperty, value: T): OWLAnnotation =
      Annotation(annotations.toSet, property, value)

    def apply[T: AnnotationValuer](property: OWLAnnotationProperty, value: T): OWLAnnotation = Annotation(Set.empty, property, value)

    def unapply(annotation: OWLAnnotation): Option[(Set[OWLAnnotation], OWLAnnotationProperty, OWLAnnotationValue)] =
      Option((annotation.getAnnotations.asScala.toSet, annotation.getProperty, annotation.getValue))

  }

  object AnnotationAssertion {

    def apply[T: Annotatable, V: AnnotationValuer](annotations: Set[OWLAnnotation], property: OWLAnnotationProperty, subject: T, value: V): OWLAnnotationAssertionAxiom = {
      val annotatable = implicitly[Annotatable[T]]
      val valuer = implicitly[AnnotationValuer[V]]
      factory.getOWLAnnotationAssertionAxiom(property, annotatable.toAnnotationSubject(subject), valuer.toAnnotationValue(value), annotations.asJava)
    }

    def apply[T: Annotatable, V: AnnotationValuer](property: OWLAnnotationProperty, subject: T, value: V): OWLAnnotationAssertionAxiom =
      AnnotationAssertion(Set.empty, property, subject, value)

    def apply[T: Annotatable, V: AnnotationValuer](annotations: OWLAnnotation*)(property: OWLAnnotationProperty, subject: T, value: V): OWLAnnotationAssertionAxiom = AnnotationAssertion(annotations.toSet, property, subject, value)

    def unapply(axiom: OWLAnnotationAssertionAxiom): Option[(Set[OWLAnnotation], OWLAnnotationProperty, OWLAnnotationSubject, OWLAnnotationValue)] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getProperty, axiom.getSubject, axiom.getValue)

  }

}