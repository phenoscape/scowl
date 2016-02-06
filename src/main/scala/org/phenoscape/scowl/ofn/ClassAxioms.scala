package org.phenoscape.scowl.ofn

import scala.collection.JavaConversions._

import org.phenoscape.scowl.factory
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
import org.semanticweb.owlapi.model.OWLHasKeyAxiom
import org.semanticweb.owlapi.model.OWLNaryClassAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLPropertyExpression
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom

trait ClassAxioms {

  object SubClassOf {

    def apply(annotations: Set[OWLAnnotation], subClass: OWLClassExpression, superClass: OWLClassExpression): OWLSubClassOfAxiom =
      factory.getOWLSubClassOfAxiom(subClass, superClass, annotations)

    def apply(annotations: OWLAnnotation*)(subClass: OWLClassExpression, superClass: OWLClassExpression): OWLSubClassOfAxiom =
      SubClassOf(annotations.toSet, subClass, superClass)

    def apply(subClass: OWLClassExpression, superClass: OWLClassExpression): OWLSubClassOfAxiom =
      SubClassOf(Set.empty, subClass, superClass)

    def unapply(axiom: OWLSubClassOfAxiom): Option[(Set[OWLAnnotation], OWLClassExpression, OWLClassExpression)] =
      Option((axiom.getAnnotations.toSet, axiom.getSubClass, axiom.getSuperClass))

  }

  object EquivalentClasses extends NaryClassAxiom[OWLEquivalentClassesAxiom] {

    def apply(annotations: Set[OWLAnnotation], classExpressions: Set[_ <: OWLClassExpression]): OWLEquivalentClassesAxiom =
      factory.getOWLEquivalentClassesAxiom(classExpressions, annotations)

  }

  object DisjointClasses extends NaryClassAxiom[OWLDisjointClassesAxiom] {

    def apply(annotations: Set[OWLAnnotation], classExpressions: Set[_ <: OWLClassExpression]): OWLDisjointClassesAxiom =
      factory.getOWLDisjointClassesAxiom(classExpressions, annotations)

  }

  object DisjointUnion {

    def apply(annotations: Set[OWLAnnotation], aClass: OWLClass, classExpressions: Set[_ <: OWLClassExpression]): OWLDisjointUnionAxiom =
      factory.getOWLDisjointUnionAxiom(aClass, classExpressions, annotations)

    def apply(aClass: OWLClass, classExpressions: Set[_ <: OWLClassExpression]): OWLDisjointUnionAxiom =
      DisjointUnion(Set[OWLAnnotation](), aClass, classExpressions)

    def apply(annotations: OWLAnnotation*)(aClass: OWLClass, classExpressions: OWLClassExpression*): OWLDisjointUnionAxiom =
      DisjointUnion(annotations.toSet, aClass, classExpressions.toSet)

    def apply(aClass: OWLClass, classExpressions: OWLClassExpression*): OWLDisjointUnionAxiom =
      DisjointUnion(Set[OWLAnnotation](), aClass, classExpressions.toSet)

    def unapply(axiom: OWLDisjointUnionAxiom): Option[(Set[OWLAnnotation], OWLClass, Set[_ <: OWLClassExpression])] =
      Option((axiom.getAnnotations.toSet, axiom.getOWLClass, axiom.getClassExpressions.toSet))

  }

  object HasKey {

    def apply(annotations: Set[OWLAnnotation], classExpression: OWLClassExpression, objectProperties: Set[OWLObjectPropertyExpression], dataProperties: Set[OWLDataPropertyExpression]): OWLHasKeyAxiom =
      factory.getOWLHasKeyAxiom(classExpression, objectProperties ++ dataProperties, annotations)

    def apply(annotations: OWLAnnotation*)(classExpression: OWLClassExpression, properties: OWLPropertyExpression[_, _]*): OWLHasKeyAxiom =
      factory.getOWLHasKeyAxiom(classExpression, properties.toSet, annotations.toSet)

    def apply(classExpression: OWLClassExpression, properties: OWLPropertyExpression[_, _]*): OWLHasKeyAxiom =
      factory.getOWLHasKeyAxiom(classExpression, properties.toSet)

    def unapply(axiom: OWLHasKeyAxiom): Option[(Set[OWLAnnotation], OWLClassExpression, Set[OWLObjectPropertyExpression], Set[OWLDataPropertyExpression])] =
      Option(axiom.getAnnotations.toSet, axiom.getClassExpression, axiom.getObjectPropertyExpressions.toSet, axiom.getDataPropertyExpressions.toSet)

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
    Option((axiom.getAnnotations.toSet, axiom.getClassExpressions.toSet))

}