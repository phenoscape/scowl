package org.phenoscape.scowl

import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom
import org.semanticweb.owlapi.model.OWLObjectUnionOf
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom
import org.semanticweb.owlapi.model.IRI
import org.phenoscape.scowl.OWL._

object Macros {

  val factory = OWLManager.getOWLDataFactory
  val part_of = ObjectProperty("http://purl.obolibrary.org/obo/part_of") //FIXME

  implicit class MacroClassExpression(val self: OWLClassExpression) extends AnyVal {

    def SpatiallyDisjointFrom(other: OWLClassExpression): OWLDisjointClassesAxiom = (part_of some self) DisjointFrom (part_of some other)

  }

}