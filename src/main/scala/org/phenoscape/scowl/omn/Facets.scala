package org.phenoscape.scowl.omn

import org.semanticweb.owlapi.model.OWLLiteral
import org.semanticweb.owlapi.vocab.OWLFacet
import org.phenoscape.scowl.factory

trait Facets {

  def <(value: OWLLiteral) = factory.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, value)

  def <=(value: OWLLiteral) = factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, value)

  def >(value: OWLLiteral) = factory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, value)

  def >=(value: OWLLiteral) = factory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, value)

  def <(value: Int) = factory.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, value)

  def <=(value: Int) = factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, value)

  def >(value: Int) = factory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, value)

  def >=(value: Int) = factory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, value)

  def <(value: Float) = factory.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, value)

  def <=(value: Float) = factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, value)

  def >(value: Float) = factory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, value)

  def >=(value: Float) = factory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, value)

  def <(value: Double) = factory.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, value)

  def <=(value: Double) = factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, value)

  def >(value: Double) = factory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, value)

  def >=(value: Double) = factory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, value)

}