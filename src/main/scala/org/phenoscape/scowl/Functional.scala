package org.phenoscape.scowl

import scala.collection.JavaConversions._
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDeclarationAxiom
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom
import org.semanticweb.owlapi.model.OWLEntity
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLNaryClassAxiom
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom
import org.semanticweb.owlapi.model.OWLObjectComplementOf
import org.semanticweb.owlapi.model.OWLObjectExactCardinality
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality
import org.semanticweb.owlapi.model.OWLObjectMinCardinality
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom
import org.semanticweb.owlapi.model.OWLObjectUnionOf
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLAnonymousIndividual
import org.semanticweb.owlapi.model.NodeID

object Functional {

  val factory = OWLManager.getOWLDataFactory
  val OWLThing = factory.getOWLThing

  object ObjectIntersectionOf {

    def apply(operands: OWLClassExpression*): OWLObjectIntersectionOf = apply(operands.toSet)

    def apply(operands: Set[_ <: OWLClassExpression]): OWLObjectIntersectionOf = factory.getOWLObjectIntersectionOf(operands)

    def unapply(expression: OWLObjectIntersectionOf): Option[Set[_ <: OWLClassExpression]] = Option(expression.getOperands.toSet)

    //def unapplySeq(operands: Seq[OWLClassExpression]): Option[Seq[OWLClassExpression]] = Option(operands.sortWith((a, b) => a.compareTo(b) < 1))

  }

  object ObjectUnionOf {

    def apply(operands: OWLClassExpression*): OWLObjectUnionOf = apply(operands.toSet)

    def apply(operands: Set[_ <: OWLClassExpression]): OWLObjectUnionOf = factory.getOWLObjectUnionOf(operands)

    def unapply(expression: OWLObjectUnionOf): Option[Set[_ <: OWLClassExpression]] = Option(expression.getOperands.toSet)

  }

  object ObjectComplementOf {

    def apply(operand: OWLClassExpression): OWLObjectComplementOf = factory.getOWLObjectComplementOf(operand)

    def unapply(complement: OWLObjectComplementOf): Option[OWLClassExpression] = Option(complement.getOperand)

  }

  object ObjectSomeValuesFrom {

    def apply(property: OWLObjectPropertyExpression, filler: OWLClassExpression): OWLObjectSomeValuesFrom = factory.getOWLObjectSomeValuesFrom(property, filler)

    def unapply(expression: OWLObjectSomeValuesFrom): Option[(OWLObjectPropertyExpression, OWLClassExpression)] = Option((expression.getProperty, expression.getFiller))

  }

  object ObjectAllValuesFrom {

    def apply(property: OWLObjectPropertyExpression, filler: OWLClassExpression): OWLObjectAllValuesFrom = factory.getOWLObjectAllValuesFrom(property, filler)

    def unapply(expression: OWLObjectAllValuesFrom): Option[(OWLObjectPropertyExpression, OWLClassExpression)] = Option((expression.getProperty, expression.getFiller))

  }

  object ObjectExactCardinality {

    def apply(cardinality: Int, property: OWLObjectPropertyExpression, filler: OWLClassExpression = OWLThing): OWLObjectExactCardinality = factory.getOWLObjectExactCardinality(cardinality, property, filler)

    def unapply(expression: OWLObjectExactCardinality): Option[(Int, OWLObjectPropertyExpression, OWLClassExpression)] = Option((expression.getCardinality, expression.getProperty, expression.getFiller))

  }

  object ObjectMinCardinality {

    def apply(cardinality: Int, property: OWLObjectPropertyExpression, filler: OWLClassExpression = OWLThing): OWLObjectMinCardinality = factory.getOWLObjectMinCardinality(cardinality, property, filler)

    def unapply(expression: OWLObjectMinCardinality): Option[(Int, OWLObjectPropertyExpression, OWLClassExpression)] = Option((expression.getCardinality, expression.getProperty, expression.getFiller))

  }

  object ObjectMaxCardinality {

    def apply(cardinality: Int, property: OWLObjectPropertyExpression, filler: OWLClassExpression = OWLThing): OWLObjectMaxCardinality = factory.getOWLObjectMaxCardinality(cardinality, property, filler)

    def unapply(expression: OWLObjectMaxCardinality): Option[(Int, OWLObjectPropertyExpression, OWLClassExpression)] = Option((expression.getCardinality, expression.getProperty, expression.getFiller))

  }

  object NamedIndividual {

    def apply(iri: IRI): OWLNamedIndividual = factory.getOWLNamedIndividual(iri)

    def unapply(individual: OWLNamedIndividual): Option[IRI] = Option(individual.getIRI)

  }

  object AnonymousIndividual {

    def apply(id: String): OWLAnonymousIndividual = factory.getOWLAnonymousIndividual(id)

    def apply(): OWLAnonymousIndividual = factory.getOWLAnonymousIndividual

    def unapply(individual: OWLAnonymousIndividual): Option[String] = Option(individual.getID.getID)

  }

  object Declaration {

    def apply(annotations: Set[OWLAnnotation], entity: OWLEntity): OWLDeclarationAxiom = factory.getOWLDeclarationAxiom(entity, annotations)

    def apply(annotations: OWLAnnotation*)(entity: OWLEntity): OWLDeclarationAxiom = Declaration(annotations.toSet, entity)

    def apply(entity: OWLEntity): OWLDeclarationAxiom = Declaration(Set(), entity)

    def unapply(axiom: OWLDeclarationAxiom): Option[(Set[OWLAnnotation], OWLEntity)] = Option((axiom.getAnnotations.toSet, axiom.getEntity))

  }

  object SubClassOf {

    def apply(annotations: Set[OWLAnnotation], subClass: OWLClassExpression, superClass: OWLClassExpression): OWLSubClassOfAxiom = factory.getOWLSubClassOfAxiom(subClass, superClass, annotations)

    def apply(annotations: OWLAnnotation*)(subClass: OWLClassExpression, superClass: OWLClassExpression): OWLSubClassOfAxiom = SubClassOf(annotations.toSet, subClass, superClass)

    def apply(subClass: OWLClassExpression, superClass: OWLClassExpression): OWLSubClassOfAxiom = SubClassOf(Set(), subClass, superClass)

    def unapply(axiom: OWLSubClassOfAxiom): Option[(Set[OWLAnnotation], OWLClassExpression, OWLClassExpression)] = Option((axiom.getAnnotations.toSet, axiom.getSubClass, axiom.getSuperClass))

  }

  trait NaryClassAxiom[T <: OWLNaryClassAxiom] {

    def apply(annotations: Set[OWLAnnotation], classExpressions: Set[_ <: OWLClassExpression]): T

    def apply(classExpressions: Set[_ <: OWLClassExpression]): T = apply(Set[OWLAnnotation](), classExpressions)

    def apply(annotations: OWLAnnotation*)(classExpressions: OWLClassExpression*): T = apply(annotations.toSet, classExpressions.toSet)

    def apply(classExpressions: OWLClassExpression*): T = apply(Set[OWLAnnotation](), classExpressions.toSet)

    def unapply(axiom: T): Option[(Set[OWLAnnotation], Set[_ <: OWLClassExpression])] = Option((axiom.getAnnotations.toSet, axiom.getClassExpressions.toSet))

  }

  object EquivalentClasses extends NaryClassAxiom[OWLEquivalentClassesAxiom] {

    def apply(annotations: Set[OWLAnnotation], classExpressions: Set[_ <: OWLClassExpression]): OWLEquivalentClassesAxiom = factory.getOWLEquivalentClassesAxiom(classExpressions, annotations)

  }

  object DisjointClasses extends NaryClassAxiom[OWLDisjointClassesAxiom] {

    def apply(annotations: Set[OWLAnnotation], classExpressions: Set[_ <: OWLClassExpression]): OWLDisjointClassesAxiom = factory.getOWLDisjointClassesAxiom(classExpressions, annotations)

  }

  object DisjointUnion {

    def apply(annotations: Set[OWLAnnotation], aClass: OWLClass, classExpressions: Set[_ <: OWLClassExpression]): OWLDisjointUnionAxiom = factory.getOWLDisjointUnionAxiom(aClass, classExpressions, annotations)

    def apply(aClass: OWLClass, classExpressions: Set[_ <: OWLClassExpression]): OWLDisjointUnionAxiom = DisjointUnion(Set[OWLAnnotation](), aClass, classExpressions)

    def apply(annotations: OWLAnnotation*)(aClass: OWLClass, classExpressions: OWLClassExpression*): OWLDisjointUnionAxiom = DisjointUnion(annotations.toSet, aClass, classExpressions.toSet)

    def apply(aClass: OWLClass, classExpressions: OWLClassExpression*): OWLDisjointUnionAxiom = DisjointUnion(Set[OWLAnnotation](), aClass, classExpressions.toSet)

    def unapply(axiom: OWLDisjointUnionAxiom): Option[(Set[OWLAnnotation], OWLClass, Set[_ <: OWLClassExpression])] = Option((axiom.getAnnotations.toSet, axiom.getOWLClass, axiom.getClassExpressions.toSet))

  }

  trait PropertyAssertionAxiom[T <: OWLPropertyAssertionAxiom[OWLObjectPropertyExpression, OWLIndividual]] {

    def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): T

    def apply(annotations: OWLAnnotation*)(property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): T = apply(annotations.toSet, property, subject, target)

    def apply(property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): T = apply(Set(), property, subject, target)

    def unapply(axiom: T): Option[(Set[OWLAnnotation], OWLObjectPropertyExpression, OWLIndividual, OWLIndividual)] = Option((axiom.getAnnotations.toSet, axiom.getProperty, axiom.getSubject, axiom.getObject))

  }

  object ObjectPropertyAssertion extends PropertyAssertionAxiom[OWLObjectPropertyAssertionAxiom] {

    def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): OWLObjectPropertyAssertionAxiom = factory.getOWLObjectPropertyAssertionAxiom(property, subject, target, annotations)

  }

  object NegativeObjectPropertyAssertion extends PropertyAssertionAxiom[OWLNegativeObjectPropertyAssertionAxiom] {

    def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, subject: OWLIndividual, target: OWLIndividual): OWLNegativeObjectPropertyAssertionAxiom = factory.getOWLNegativeObjectPropertyAssertionAxiom(property, subject, target, annotations)

  }

  object ClassAssertion {

    def apply(annotations: Set[OWLAnnotation], classExpression: OWLClassExpression, individual: OWLIndividual): OWLClassAssertionAxiom = factory.getOWLClassAssertionAxiom(classExpression, individual, annotations)

    def apply(classExpression: OWLClassExpression, individual: OWLIndividual): OWLClassAssertionAxiom = ClassAssertion(Set(), classExpression, individual)

    def apply(annotations: OWLAnnotation*)(classExpression: OWLClassExpression, individual: OWLIndividual): OWLClassAssertionAxiom = ClassAssertion(annotations.toSet, classExpression, individual)

    def unapply(axiom: OWLClassAssertionAxiom): Option[(Set[OWLAnnotation], OWLClassExpression, OWLIndividual)] = Option((axiom.getAnnotations.toSet, axiom.getClassExpression, axiom.getIndividual))

  }

  object Annotation {

    def apply(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAnnotation = factory.getOWLAnnotation(property, value)

    def unapply(annotation: OWLAnnotation): Option[(OWLAnnotationProperty, OWLAnnotationValue)] = Option((annotation.getProperty, annotation.getValue))

  }

}