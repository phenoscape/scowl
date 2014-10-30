package org.phenoscape.scowl

import scala.collection.JavaConversions._
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationSubject
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLAnonymousIndividual
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLNamedObject
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom
import org.semanticweb.owlapi.model.OWLObjectComplementOf
import org.semanticweb.owlapi.model.OWLObjectExactCardinality
import org.semanticweb.owlapi.model.OWLObjectHasValue
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality
import org.semanticweb.owlapi.model.OWLObjectMinCardinality
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom
import org.semanticweb.owlapi.model.OWLObjectUnionOf
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom
import org.semanticweb.owlapi.model.OWLDataProperty
import org.semanticweb.owlapi.model.OWLEntity
import org.semanticweb.owlapi.model.OWLDeclarationAxiom
import org.semanticweb.owlapi.model.OWLObjectOneOf
import org.semanticweb.owlapi.model.OWLObjectHasSelf
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom

object OWL {

  val factory = OWLManager.getOWLDataFactory

  def Ontology(iri: String, axioms: Set[OWLAxiom]): OWLOntology = OWLManager.createOWLOntologyManager().createOntology(axioms, IRI.create(iri))

  def Declare(entity: OWLEntity): OWLDeclarationAxiom = factory.getOWLDeclarationAxiom(entity)
  
  object Class {

    def apply(iri: IRI): OWLClass = factory.getOWLClass(iri)

    def apply(iri: String): OWLClass = apply(IRI.create(iri))

    def unapply(aClass: OWLClass): Option[IRI] = Option(aClass.getIRI)

  }

  def Individual(iri: IRI): OWLNamedIndividual = factory.getOWLNamedIndividual(iri)

  def Individual(iri: String): OWLNamedIndividual = Individual(IRI.create(iri))

  def Individual(): OWLAnonymousIndividual = factory.getOWLAnonymousIndividual()

  def ObjectProperty(iri: IRI): OWLObjectProperty = factory.getOWLObjectProperty(iri)

  def ObjectProperty(iri: String): OWLObjectProperty = ObjectProperty(IRI.create(iri))

  def AnnotationProperty(iri: IRI): OWLAnnotationProperty = factory.getOWLAnnotationProperty(iri)

  def AnnotationProperty(iri: String): OWLAnnotationProperty = AnnotationProperty(IRI.create(iri))

  def DataProperty(iri: IRI): OWLDataProperty = factory.getOWLDataProperty(iri)

  def DataProperty(iri: String): OWLDataProperty = DataProperty(IRI.create(iri))

  def not(classExpression: OWLClassExpression): OWLObjectComplementOf = OWLManager.getOWLDataFactory.getOWLObjectComplementOf(classExpression)

  def oneOf(individuals: OWLNamedIndividual*): OWLObjectOneOf = factory.getOWLObjectOneOf(individuals.toSet)

  implicit class ScowlClassExpression(val self: OWLClassExpression) extends AnyVal {

    def and(other: OWLClassExpression): OWLObjectIntersectionOf = factory.getOWLObjectIntersectionOf(self.asConjunctSet + other)

    def or(other: OWLClassExpression): OWLObjectUnionOf = factory.getOWLObjectUnionOf(self.asDisjunctSet + other)

    def SubClassOf(other: OWLClassExpression): OWLSubClassOfAxiom = factory.getOWLSubClassOfAxiom(self, other)

    def EquivalentTo(other: OWLClassExpression): OWLEquivalentClassesAxiom = factory.getOWLEquivalentClassesAxiom(self, other)

    def DisjointFrom(other: OWLClassExpression): OWLDisjointClassesAxiom = factory.getOWLDisjointClassesAxiom(self, other)

  }

  implicit class ScowlIndividual(val self: OWLIndividual) extends AnyVal {

    def Fact(property: OWLObjectProperty, value: OWLIndividual): OWLObjectPropertyAssertionAxiom = factory.getOWLObjectPropertyAssertionAxiom(property, self, value)

    def Facts(facts: (OWLObjectProperty, OWLIndividual)*): Set[OWLObjectPropertyAssertionAxiom] =
      (facts map { case (property, value) => factory.getOWLObjectPropertyAssertionAxiom(property, self, value) }).toSet

    def Type(owlClass: OWLClassExpression): OWLClassAssertionAxiom = factory.getOWLClassAssertionAxiom(owlClass, self)

  }

  implicit class ScowlObjectProperty(val objectProperty: OWLObjectPropertyExpression) extends AnyVal {

    def some(classExpression: OWLClassExpression): OWLObjectSomeValuesFrom = factory.getOWLObjectSomeValuesFrom(objectProperty, classExpression)

    def only(classExpression: OWLClassExpression): OWLObjectAllValuesFrom = factory.getOWLObjectAllValuesFrom(objectProperty, classExpression)

    def exactly(cardinality: Int, classExpression: OWLClassExpression): OWLObjectExactCardinality = factory.getOWLObjectExactCardinality(cardinality, objectProperty, classExpression)

    def exactly(cardinality: Int): OWLObjectExactCardinality = factory.getOWLObjectExactCardinality(cardinality, objectProperty)

    def min(cardinality: Int, classExpression: OWLClassExpression): OWLObjectMinCardinality = factory.getOWLObjectMinCardinality(cardinality, objectProperty, classExpression)

    def min(cardinality: Int): OWLObjectMinCardinality = factory.getOWLObjectMinCardinality(cardinality, objectProperty)

    def max(cardinality: Int, classExpression: OWLClassExpression): OWLObjectMaxCardinality = factory.getOWLObjectMaxCardinality(cardinality, objectProperty, classExpression)

    def max(cardinality: Int): OWLObjectMaxCardinality = factory.getOWLObjectMaxCardinality(cardinality, objectProperty)

    def value(individual: OWLIndividual): OWLObjectHasValue = factory.getOWLObjectHasValue(objectProperty, individual)

    def o(property: OWLObjectPropertyExpression): ScowlPropertyChain = new ScowlPropertyChain(objectProperty, property)

    def SubPropertyChain(chain: ScowlPropertyChain): OWLSubPropertyChainOfAxiom = factory.getOWLSubPropertyChainOfAxiom(chain.properties, objectProperty)

    def Self: OWLObjectHasSelf = factory.getOWLObjectHasSelf(objectProperty)

  }

  implicit class ScowlAnnotationSubject(val self: OWLAnnotationSubject) extends AnyVal {

    def Annotation(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAnnotationAssertionAxiom = {
      factory.getOWLAnnotationAssertionAxiom(property, self, value)
    }

  }

  implicit class ScowlAxiom(val self: OWLAxiom) extends AnyVal {

    def Annotation(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAxiom = {
      self.getAnnotatedAxiom(Set(factory.getOWLAnnotation(property, value)))
    }

    def Annotation(property: OWLAnnotationProperty, value: OWLNamedObject): OWLAxiom = {
      self.getAnnotatedAxiom(Set(factory.getOWLAnnotation(property, value.getIRI)))
    }

  }

  implicit class ScowlNamedObject(val self: OWLNamedObject) extends AnyVal {

    def Annotation(property: OWLAnnotationProperty, value: String): OWLAnnotationAssertionAxiom = Annotation(property, factory.getOWLLiteral(value))

    def Annotation(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAnnotationAssertionAxiom = factory.getOWLAnnotationAssertionAxiom(property, self.getIRI, value)

  }

}