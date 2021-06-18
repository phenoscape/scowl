package org.phenoscape.scowl.ofn

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model._

import scala.jdk.CollectionConverters._

trait Entities {
  
  private val factory = OWLManager.getOWLDataFactory

  def Ontology(iri: String, axioms: Set[OWLAxiom]): OWLOntology = OWLManager.createOWLOntologyManager().createOntology(axioms.asJava, IRI.create(iri))

  object Class extends NamedObjectConstructor[OWLClass](factory.getOWLClass)

  def Individual(iri: IRI): OWLNamedIndividual = factory.getOWLNamedIndividual(iri)

  def Individual(iri: String): OWLNamedIndividual = Individual(IRI.create(iri))

  def Individual(): OWLAnonymousIndividual = factory.getOWLAnonymousIndividual()

  object NamedIndividual extends NamedObjectConstructor[OWLNamedIndividual](factory.getOWLNamedIndividual)

  object AnonymousIndividual {

    def apply(id: String): OWLAnonymousIndividual = factory.getOWLAnonymousIndividual(id)

    def apply(): OWLAnonymousIndividual = factory.getOWLAnonymousIndividual

    def unapply(individual: OWLAnonymousIndividual): Option[String] = Option(individual.getID.getID)

  }

  object ObjectProperty extends NamedObjectConstructor[OWLObjectProperty](factory.getOWLObjectProperty)

  object AnnotationProperty extends NamedObjectConstructor[OWLAnnotationProperty](factory.getOWLAnnotationProperty)

  object DataProperty extends NamedObjectConstructor[OWLDataProperty](factory.getOWLDataProperty)

  object Datatype extends NamedObjectConstructor[OWLDatatype](factory.getOWLDatatype)

}

class NamedObjectConstructor[T <: OWLNamedObject](constructor: IRI => T) {

  def apply(iri: IRI): T = constructor(iri)

  def apply(iri: String): T = apply(IRI.create(iri))

  def unapply(entity: T): Option[IRI] = Option(entity.getIRI)

}