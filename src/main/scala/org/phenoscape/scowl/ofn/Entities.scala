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
import org.phenoscape.scowl.factory
import scala.collection.JavaConversions._

trait Entities {

  def Ontology(iri: String, axioms: Set[OWLAxiom]): OWLOntology = OWLManager.createOWLOntologyManager().createOntology(axioms, IRI.create(iri))

  object Class extends NamedObjectConstructor[OWLClass] {

    def apply(iri: IRI): OWLClass = factory.getOWLClass(iri)

  }

  def Individual(iri: IRI): OWLNamedIndividual = factory.getOWLNamedIndividual(iri)

  def Individual(iri: String): OWLNamedIndividual = Individual(IRI.create(iri))

  def Individual(): OWLAnonymousIndividual = factory.getOWLAnonymousIndividual()

  object NamedIndividual extends NamedObjectConstructor[OWLNamedIndividual] {

    def apply(iri: IRI): OWLNamedIndividual = factory.getOWLNamedIndividual(iri)

  }

  object AnonymousIndividual {

    def apply(id: String): OWLAnonymousIndividual = factory.getOWLAnonymousIndividual(id)

    def apply(): OWLAnonymousIndividual = factory.getOWLAnonymousIndividual

    def unapply(individual: OWLAnonymousIndividual): Option[String] = Option(individual.getID.getID)

  }

  object ObjectProperty extends NamedObjectConstructor[OWLObjectProperty] {

    def apply(iri: IRI): OWLObjectProperty = factory.getOWLObjectProperty(iri)

  }

  object AnnotationProperty extends NamedObjectConstructor[OWLAnnotationProperty] {

    def apply(iri: IRI): OWLAnnotationProperty = factory.getOWLAnnotationProperty(iri)

  }

  object DataProperty extends NamedObjectConstructor[OWLDataProperty] {

    def apply(iri: IRI): OWLDataProperty = factory.getOWLDataProperty(iri)

  }

}

trait NamedObjectConstructor[T <: OWLNamedObject] {

  def apply(iri: IRI): T

  def apply(iri: String): T = apply(IRI.create(iri))

  def unapply(entity: T): Option[IRI] = Option(entity.getIRI)

}