package org.phenoscape.scowl.ofn

import scala.collection.JavaConversions._
import org.phenoscape.scowl.converters.Annotatable
import org.phenoscape.scowl.converters.AnnotationValuer
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationSubject
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLDeclarationAxiom
import org.semanticweb.owlapi.model.OWLEntity
import org.semanticweb.owlapi.apibinding.OWLManager

trait AnnotationAxioms {

  private val factory = OWLManager.getOWLDataFactory

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

    def apply[T: AnnotationValuer](annotations: Set[OWLAnnotation], property: OWLAnnotationProperty, value: T): OWLAnnotation = {
      val valuer = implicitly[AnnotationValuer[T]]
      factory.getOWLAnnotation(property, valuer.toAnnotationValue(value), annotations)
    }

    def apply[T: AnnotationValuer](annotations: OWLAnnotation*)(property: OWLAnnotationProperty, value: T): OWLAnnotation =
      Annotation(annotations.toSet, property, value)

    def apply[T: AnnotationValuer](property: OWLAnnotationProperty, value: T): OWLAnnotation = Annotation(Set.empty, property, value)

    def unapply(annotation: OWLAnnotation): Option[(Set[OWLAnnotation], OWLAnnotationProperty, OWLAnnotationValue)] =
      Option((annotation.getAnnotations.toSet, annotation.getProperty, annotation.getValue))

  }

  object AnnotationAssertion {

    def apply[T: Annotatable, V: AnnotationValuer](annotations: Set[OWLAnnotation], property: OWLAnnotationProperty, subject: T, value: V): OWLAnnotationAssertionAxiom = {
      val annotatable = implicitly[Annotatable[T]]
      val valuer = implicitly[AnnotationValuer[V]]
      factory.getOWLAnnotationAssertionAxiom(property, annotatable.toAnnotationSubject(subject), valuer.toAnnotationValue(value), annotations)
    }

    def apply[T: Annotatable, V: AnnotationValuer](property: OWLAnnotationProperty, subject: T, value: V): OWLAnnotationAssertionAxiom =
      AnnotationAssertion(Set.empty, property, subject, value)

    def apply[T: Annotatable, V: AnnotationValuer](annotations: OWLAnnotation*)(property: OWLAnnotationProperty, subject: T, value: V): OWLAnnotationAssertionAxiom = AnnotationAssertion(annotations.toSet, property, subject, value)

    def unapply(axiom: OWLAnnotationAssertionAxiom): Option[(Set[OWLAnnotation], OWLAnnotationProperty, OWLAnnotationSubject, OWLAnnotationValue)] =
      Option(axiom.getAnnotations.toSet, axiom.getProperty, axiom.getSubject, axiom.getValue)

  }

}