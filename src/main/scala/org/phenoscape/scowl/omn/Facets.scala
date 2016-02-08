package org.phenoscape.scowl.omn

import org.semanticweb.owlapi.model.OWLLiteral
import org.semanticweb.owlapi.vocab.OWLFacet
import org.phenoscape.scowl.factory
import org.phenoscape.scowl.Literalable

trait Facets {

  def <[T: Literalable](value: T) = {
    val literalable = implicitly[Literalable[T]]
    factory.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, literalable.toLiteral(value))
  }

  def <=[T: Literalable](value: T) = {
    val literalable = implicitly[Literalable[T]]
    factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, literalable.toLiteral(value))
  }

  def >[T: Literalable](value: T) = {
    val literalable = implicitly[Literalable[T]]
    factory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, literalable.toLiteral(value))
  }

  def >=[T: Literalable](value: T) = {
    val literalable = implicitly[Literalable[T]]
    factory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, literalable.toLiteral(value))
  }

}