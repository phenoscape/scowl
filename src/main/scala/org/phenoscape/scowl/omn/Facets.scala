package org.phenoscape.scowl.omn

import org.phenoscape.scowl.converters.Literalable
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.vocab.OWLFacet

trait Facets {
  
  private val factory = OWLManager.getOWLDataFactory

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