package org.phenoscape.scowl.ofn

import org.phenoscape.scowl.converters.Literalable
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model._
import org.semanticweb.owlapi.vocab.OWLFacet

import scala.jdk.CollectionConverters._

trait DataExpressions {

  private val factory = OWLManager.getOWLDataFactory

  object ^^ {

    def unapply(literal: OWLLiteral): Option[(String, OWLDatatype)] = Option(literal.getLiteral, literal.getDatatype)

  }

  object @@ {

    def unapply(literal: OWLLiteral): Option[(String, Option[String])] = Option(
      literal.getLiteral,
      if (literal.hasLang) Some(literal.getLang) else None)

  }

  object DataOneOf {

    def apply(literals: OWLLiteral*): OWLDataOneOf =
      apply(literals.toSet)

    def apply(literals: Set[_ <: OWLLiteral]): OWLDataOneOf =
      factory.getOWLDataOneOf(literals.asJava)

    def unapply(expression: OWLDataOneOf): Option[Set[_ <: OWLLiteral]] =
      Option(expression.getValues.asScala.toSet)

  }

  object DatatypeRestriction {

    def apply(datatype: OWLDatatype, restrictions: Set[OWLFacetRestriction]): OWLDatatypeRestriction =
      factory.getOWLDatatypeRestriction(datatype, restrictions.asJava)

    def apply(datatype: OWLDatatype, restrictions: OWLFacetRestriction*): OWLDatatypeRestriction =
      apply(datatype, restrictions.toSet)

    def unapply(datatypeRestriction: OWLDatatypeRestriction): Option[(OWLDatatype, Set[OWLFacetRestriction])] =
      Option(datatypeRestriction.getDatatype, datatypeRestriction.getFacetRestrictions.asScala.toSet)

  }

  object XSDMinInclusive extends FacetRestriction(OWLFacet.MIN_EXCLUSIVE)

  object XSDMaxInclusive extends FacetRestriction(OWLFacet.MAX_EXCLUSIVE)

  object DatatypeDefinition {

    def apply(annotations: Set[OWLAnnotation], datatype: OWLDatatype, datarange: OWLDataRange): OWLDatatypeDefinitionAxiom =
      factory.getOWLDatatypeDefinitionAxiom(datatype, datarange, annotations.asJava)

    def apply(datatype: OWLDatatype, datarange: OWLDataRange): OWLDatatypeDefinitionAxiom =
      apply(Set.empty, datatype, datarange)

    def unapply(axiom: OWLDatatypeDefinitionAxiom): Option[(Set[OWLAnnotation], OWLDatatype, OWLDataRange)] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getDatatype, axiom.getDataRange)

  }

  object DataIntersectionOf {

    def apply(dataRanges: Set[OWLDataRange]): OWLDataIntersectionOf = factory.getOWLDataIntersectionOf(dataRanges.asJava)

    def apply(dataRange1: OWLDataRange, dataRange2: OWLDataRange, more: OWLDataRange*): OWLDataIntersectionOf =
      apply(more.toSet + dataRange1 + dataRange2)

    def unapply(intersection: OWLDataIntersectionOf): Option[Set[OWLDataRange]] = Option(intersection.getOperands.asScala.toSet)

  }

  object DataComplementOf {

    def apply(range: OWLDataRange): OWLDataComplementOf = factory.getOWLDataComplementOf(range)

    def unapply(complement: OWLDataComplementOf): Option[OWLDataRange] = Option(complement.getDataRange)

  }

  object DataSomeValuesFrom {

    def apply(property: OWLDataPropertyExpression, range: OWLDataRange): OWLDataSomeValuesFrom =
      factory.getOWLDataSomeValuesFrom(property, range)

    def unapply(expression: OWLDataSomeValuesFrom): Option[(OWLDataPropertyExpression, OWLDataRange)] =
      Option(expression.getProperty, expression.getFiller)

  }

  object DataAllValuesFrom {

    def apply(property: OWLDataPropertyExpression, range: OWLDataRange): OWLDataAllValuesFrom =
      factory.getOWLDataAllValuesFrom(property, range)

    def unapply(expression: OWLDataAllValuesFrom): Option[(OWLDataPropertyExpression, OWLDataRange)] =
      Option(expression.getProperty, expression.getFiller)

  }

}

class FacetRestriction(facet: OWLFacet) {

  private val factory = OWLManager.getOWLDataFactory

  def apply[T: Literalable](value: T): OWLFacetRestriction = {
    val literalable = implicitly[Literalable[T]]
    factory.getOWLFacetRestriction(facet, literalable.toLiteral(value))
  }

  def unapply(facetRestriction: OWLFacetRestriction): Option[OWLLiteral] = Option(facetRestriction.getFacetValue)

}