package org.phenoscape.scowl.ofn

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.OWLNamedObject
import org.semanticweb.owlapi.model.OWLAnonymousIndividual
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLDataProperty
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLAxiom
import scala.collection.JavaConverters._
import org.semanticweb.owlapi.model.OWLDatatype

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