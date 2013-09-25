package org.phenoscape.scowl

import scala.collection.JavaConversions._
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLObjectComplementOf
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLAnonymousIndividual
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLNamedObject
import org.semanticweb.owlapi.model.OWLAnnotationSubject
import org.semanticweb.owlapi.model.OWLOntology

object OWL {

  val factory = OWLManager.getOWLDataFactory();

  def Ontology(iri: String, axioms: Set[OWLAxiom]): OWLOntology = {
    return OWLManager.createOWLOntologyManager().createOntology(axioms, IRI.create(iri));
  }

  def Class(iri: IRI): OWLClass = {
    return factory.getOWLClass(iri);
  }

  def Class(iri: String): OWLClass = {
    return Class(IRI.create(iri));
  }

  def Individual(iri: IRI): OWLNamedIndividual = {
    return factory.getOWLNamedIndividual(iri);
  }

  def Individual(iri: String): OWLNamedIndividual = {
    return Individual(IRI.create(iri));
  }

  def Individual(): OWLAnonymousIndividual = {
    return factory.getOWLAnonymousIndividual();
  }

  def ObjectProperty(iri: IRI): OWLObjectProperty = {
    return OWLManager.getOWLDataFactory().getOWLObjectProperty(iri);
  }

  def ObjectProperty(iri: String): OWLObjectProperty = {
    return ObjectProperty(IRI.create(iri));
  }

  implicit def OWLObjectPropertyToProperty(value: OWLObjectProperty) = { new ScowlObjectProperty(value); }

  implicit def OWLClassExpressionToClassExpression(value: OWLClassExpression) = { new ScowlClassExpression(value); }

  implicit def OWLIndividualToIndividual(value: OWLIndividual) = { new ScowlIndividual(value); }

  implicit def OWLAxiomToScowlAxiom(value: OWLAxiom) = { new ScowlAxiom(value); }

  implicit def OWLAnnotationSubjectToScowlAnnotationSubject(value: OWLAnnotationSubject) = { new ScowlAnnotationSubject(value); }

  implicit def OWLNamedObjectToScowlNamedObject(value: OWLNamedObject) = { new ScowlNamedObject(value); }

  def not(classExpression: OWLClassExpression): OWLObjectComplementOf = {
    OWLManager.getOWLDataFactory().getOWLObjectComplementOf(classExpression);
  }

}