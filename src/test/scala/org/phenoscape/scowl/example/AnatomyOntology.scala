package org.phenoscape.scowl.example

import org.phenoscape.scowl._
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import java.io.File

object AnatomyOntology extends App {

  val factory = OWLManager.getOWLDataFactory
  val ns = "http://example.org/anatomy.owl#"
  val head = Class(ns + "001")
  val body = Class(ns + "002")
  val hand = Class(ns + "003")
  val arm = Class(ns + "004")
  val anatomical_structure = Class(ns + "005")
  val part_of = ObjectProperty(ns + "006")
  val label = factory.getRDFSLabel

  val ontology = Ontology("http://example.org/anatomy.owl", Set(
    head Annotation (label, "head"),
    head SubClassOf anatomical_structure,
    head SubClassOf (part_of some body),
    head SubClassOf (not(part_of some arm)),

    body Annotation (label, "body"),
    body SubClassOf anatomical_structure,

    arm Annotation (label, "arm"),
    arm SubClassOf anatomical_structure,
    arm SubClassOf (part_of some body),

    hand Annotation (label, "hand"),
    hand SubClassOf anatomical_structure,
    hand SubClassOf (part_of some arm)))

  ontology.getOWLOntologyManager.saveOntology(ontology, IRI.create(new File(args(0))))

}