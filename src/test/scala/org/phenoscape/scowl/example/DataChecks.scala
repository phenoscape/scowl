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
  // All tests are ignored so that the scowl build passes (this is an example ontology test).

  val manager = OWLManager.createOWLOntologyManager()
  val ont = manager.loadOntology(IRI.create("http://purl.obolibrary.org/obo/uberon/ext.owl"))
  val HasExactSynonym = AnnotationProperty("http://www.geneontology.org/formats/oboInOwl#hasExactSynonym")
  val HasNarrowSynonym = AnnotationProperty("http://www.geneontology.org/formats/oboInOwl#hasNarrowSynonym")
  val HasBroadSynonym = AnnotationProperty("http://www.geneontology.org/formats/oboInOwl#hasBroadSynonym")
  val HasRelatedSynonym = AnnotationProperty("http://www.geneontology.org/formats/oboInOwl#hasRelatedSynonym")
  val synonymProperties = Set(HasExactSynonym, HasNarrowSynonym, HasBroadSynonym, HasRelatedSynonym)

  "rdfs:label values" should "be used only once per ontology" ignore {
    ont.getAxioms(Imports.EXCLUDED)
      .collect { case axiom @ AnnotationAssertion(_, RDFSLabel, _, value ^^ _) => Map(value -> Set(axiom)) }
      .reduce(_ |+| _)
      .filter { case (label, annotations) => annotations.size > 1 } shouldBe empty
  }

  "hasExactSynonym values" should "be used only once per ontology" ignore {
    ont.getAxioms(Imports.EXCLUDED)
      .collect { case axiom @ AnnotationAssertion(_, HasExactSynonym, _, value ^^ _) => Map(value -> Set(axiom)) }
      .reduce(_ |+| _)
      .filter { case (label, annotations) => annotations.size > 1 } shouldBe empty
  }

  // This test is very slow; SPARQL would probably be better (perhaps for many other such tests as well).
  "No class" should "have a synonym that matches the label" ignore { //FIXME ignored 
    val annotations = ont.getAxioms(AxiomType.ANNOTATION_ASSERTION, Imports.EXCLUDED).toSet
    (for {
      AnnotationAssertion(_, RDFSLabel, termIRI, label ^^ _) <- annotations
      AnnotationAssertion(_, synonymProperty, `termIRI`, `label` ^^ _) <- annotations
      if synonymProperties(synonymProperty)
    } yield termIRI -> label) shouldBe empty
  }

  "Annotation values" should "not contain double spaces" ignore {
    ont.getAxioms(Imports.EXCLUDED)
      .collect { case axiom @ AnnotationAssertion(_, _, _, value ^^ _) if value.contains("  ") => axiom } shouldBe empty

  }

  "Trim" should "not have an effect on annotation values" ignore {
    ont.getAxioms(Imports.EXCLUDED)
      .collect { case axiom @ AnnotationAssertion(_, _, _, value ^^ _) if value.trim != value => axiom } shouldBe empty
  }

}