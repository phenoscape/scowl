package org.phenoscape.scowl.ofn

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model._

import scala.jdk.CollectionConverters._

trait ClassAxioms {

  private val factory = OWLManager.getOWLDataFactory

  object SubClassOf {

    def apply(annotations: Set[OWLAnnotation], subClass: OWLClassExpression, superClass: OWLClassExpression): OWLSubClassOfAxiom =
      factory.getOWLSubClassOfAxiom(subClass, superClass, annotations.asJava)

    def apply(annotations: OWLAnnotation*)(subClass: OWLClassExpression, superClass: OWLClassExpression): OWLSubClassOfAxiom =
      SubClassOf(annotations.toSet, subClass, superClass)

    def apply(subClass: OWLClassExpression, superClass: OWLClassExpression): OWLSubClassOfAxiom =
      SubClassOf(Set.empty, subClass, superClass)

    def unapply(axiom: OWLSubClassOfAxiom): Option[(Set[OWLAnnotation], OWLClassExpression, OWLClassExpression)] =
      Option((axiom.getAnnotations.asScala.toSet, axiom.getSubClass, axiom.getSuperClass))

  }

  object EquivalentClasses extends NaryClassAxiom[OWLEquivalentClassesAxiom] {

    def apply(annotations: Set[OWLAnnotation], classExpressions: Set[_ <: OWLClassExpression]): OWLEquivalentClassesAxiom =
      factory.getOWLEquivalentClassesAxiom(classExpressions.asJava, annotations.asJava)

  }

  object DisjointClasses extends NaryClassAxiom[OWLDisjointClassesAxiom] {

    def apply(annotations: Set[OWLAnnotation], classExpressions: Set[_ <: OWLClassExpression]): OWLDisjointClassesAxiom =
      factory.getOWLDisjointClassesAxiom(classExpressions.asJava, annotations.asJava)

  }

  object DisjointUnion {

    def apply(annotations: Set[OWLAnnotation], aClass: OWLClass, classExpressions: Set[_ <: OWLClassExpression]): OWLDisjointUnionAxiom =
      factory.getOWLDisjointUnionAxiom(aClass, classExpressions.asJava, annotations.asJava)

    def apply(aClass: OWLClass, classExpressions: Set[_ <: OWLClassExpression]): OWLDisjointUnionAxiom =
      DisjointUnion(Set.empty[OWLAnnotation], aClass, classExpressions)

    def apply(annotations: OWLAnnotation*)(aClass: OWLClass, classExpressions: OWLClassExpression*): OWLDisjointUnionAxiom =
      DisjointUnion(annotations.toSet, aClass, classExpressions.toSet)

    def apply(aClass: OWLClass, classExpressions: OWLClassExpression*): OWLDisjointUnionAxiom =
      DisjointUnion(Set.empty[OWLAnnotation], aClass, classExpressions.toSet)

    def unapply(axiom: OWLDisjointUnionAxiom): Option[(Set[OWLAnnotation], OWLClass, Set[_ <: OWLClassExpression])] =
      Option((axiom.getAnnotations.asScala.toSet, axiom.getOWLClass, axiom.getClassExpressions.asScala.toSet))

  }

  object HasKey {

    def apply(annotations: Set[OWLAnnotation], classExpression: OWLClassExpression, objectProperties: Set[OWLObjectPropertyExpression], dataProperties: Set[OWLDataPropertyExpression]): OWLHasKeyAxiom =
      factory.getOWLHasKeyAxiom(classExpression, (objectProperties ++ dataProperties).asJava, annotations.asJava)

    def apply(annotations: OWLAnnotation*)(classExpression: OWLClassExpression, properties: OWLPropertyExpression*): OWLHasKeyAxiom =
      factory.getOWLHasKeyAxiom(classExpression, properties.toSet.asJava, annotations.toSet.asJava)

    def apply(classExpression: OWLClassExpression, properties: OWLPropertyExpression*): OWLHasKeyAxiom =
      factory.getOWLHasKeyAxiom(classExpression, properties.toSet.asJava)

    def unapply(axiom: OWLHasKeyAxiom): Option[(Set[OWLAnnotation], OWLClassExpression, Set[OWLObjectPropertyExpression], Set[OWLDataPropertyExpression])] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getClassExpression, axiom.getObjectPropertyExpressions.asScala.toSet, axiom.getDataPropertyExpressions.asScala.toSet)

  }

}

trait NaryClassAxiom[T <: OWLNaryClassAxiom] {

  def apply(annotations: Set[OWLAnnotation], classExpressions: Set[_ <: OWLClassExpression]): T

  def apply(classExpressions: Set[_ <: OWLClassExpression]): T =
    apply(Set.empty[OWLAnnotation], classExpressions)

  def apply(annotations: OWLAnnotation*)(classExpressions: OWLClassExpression*): T =
    apply(annotations.toSet, classExpressions.toSet)

  def apply(classExpressions: OWLClassExpression*): T =
    apply(Set.empty[OWLAnnotation], classExpressions.toSet)

  def unapply(axiom: T): Option[(Set[OWLAnnotation], Set[_ <: OWLClassExpression])] =
    Option((axiom.getAnnotations.asScala.toSet, axiom.getClassExpressions.asScala.toSet))

}