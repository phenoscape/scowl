package org.phenoscape.scowl.example

import scalaz._
import Scalaz._
import org.semanticweb.owlapi.model.OWLOntology
import org.phenoscape.scowl._
import org.phenoscape.scowl.^^
import scala.collection.JavaConversions._
import org.semanticweb.owlapi.model.AxiomType
import org.semanticweb.owlapi.model.parameters.Imports
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.phenoscape.scowltest.UnitSpec
import org.scalatest._

class DataChecks extends UnitSpec {

  val manager = OWLManager.createOWLOntologyManager()
  val ont = manager.loadOntology(IRI.create("http://purl.obolibrary.org/obo/uberon/ext.owl"))

  "rdfs:label values" should "be used only once per ontology" in {
    ont.getAxioms(Imports.EXCLUDED)
      .collect { case axiom @ AnnotationAssertion(_, RDFSLabel, _, value ^^ _) => Map(value -> Set(axiom)) }
      .reduce(_ |+| _)
      .filter { case (label, annotations) => annotations.size > 1 }
      .isEmpty should be(true)
  }

  "Annotation values" should "not contain double spaces" in {
    ont.getAxioms(Imports.EXCLUDED)
      .collect { case axiom @ AnnotationAssertion(_, _, _, value ^^ _) if value.contains("  ") => axiom }
      .isEmpty should be(true)

  }

  "Trim" should "not have an effect on annotation values" in {
    ont.getAxioms(Imports.EXCLUDED)
      .collect { case axiom @ AnnotationAssertion(_, _, _, value ^^ _) if value.trim != value => axiom }
      .isEmpty should be(true)
  }

}