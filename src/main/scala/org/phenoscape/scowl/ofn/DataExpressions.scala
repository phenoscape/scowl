package org.phenoscape.scowl.ofn

import scala.collection.JavaConversions._
import org.phenoscape.scowl.factory
import org.phenoscape.scowl.Literalable
import org.semanticweb.owlapi.model.OWLDataOneOf
import org.semanticweb.owlapi.model.OWLDatatype
import org.semanticweb.owlapi.model.OWLLiteral
import org.semanticweb.owlapi.model.OWLDataRange
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom
import org.semanticweb.owlapi.model.OWLFacetRestriction
import org.semanticweb.owlapi.model.OWLDatatypeRestriction
import org.semanticweb.owlapi.vocab.OWLFacet
import org.semanticweb.owlapi.model.OWLDataIntersectionOf
import org.semanticweb.owlapi.model.OWLDataComplementOf
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom
import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom

trait DataExpressions {

  object ^^ {

    def unapply(literal: OWLLiteral): Option[(String, OWLDatatype)] = Option(literal.getLiteral, literal.getDatatype)

  }

  object @@ {

    def unapply(literal: OWLLiteral): Option[(String, String)] = Option(literal.getLiteral, literal.getLang)

  }

  object DataOneOf {

    def apply(literals: OWLLiteral*): OWLDataOneOf =
      apply(literals.toSet)

    def apply(literals: Set[_ <: OWLLiteral]): OWLDataOneOf =
      factory.getOWLDataOneOf(literals)

    def unapply(expression: OWLDataOneOf): Option[Set[_ <: OWLLiteral]] =
      Option(expression.getValues.toSet)

  }

  object DatatypeRestriction {

    def apply(datatype: OWLDatatype, restrictions: Set[OWLFacetRestriction]): OWLDatatypeRestriction =
      factory.getOWLDatatypeRestriction(datatype, restrictions)

    def apply(datatype: OWLDatatype, restrictions: OWLFacetRestriction*): OWLDatatypeRestriction =
      apply(datatype, restrictions.toSet)

    def unapply(datatypeRestriction: OWLDatatypeRestriction): Option[(OWLDatatype, Set[OWLFacetRestriction])] =
      Option(datatypeRestriction.getDatatype, datatypeRestriction.getFacetRestrictions.toSet)

  }

  object XSDMinInclusive extends FacetRestriction(OWLFacet.MIN_EXCLUSIVE)

  object XSDMaxInclusive extends FacetRestriction(OWLFacet.MAX_EXCLUSIVE)

  object DatatypeDefinition {

    def apply(annotations: Set[OWLAnnotation], datatype: OWLDatatype, datarange: OWLDataRange): OWLDatatypeDefinitionAxiom =
      factory.getOWLDatatypeDefinitionAxiom(datatype, datarange, annotations)

    def apply(datatype: OWLDatatype, datarange: OWLDataRange): OWLDatatypeDefinitionAxiom =
      apply(Set.empty, datatype, datarange)

    def unapply(axiom: OWLDatatypeDefinitionAxiom): Option[(Set[OWLAnnotation], OWLDatatype, OWLDataRange)] =
      Option(axiom.getAnnotations.toSet, axiom.getDatatype, axiom.getDataRange)

  }

  object DataIntersectionOf {

    def apply(dataRanges: Set[OWLDataRange]): OWLDataIntersectionOf = factory.getOWLDataIntersectionOf(dataRanges)

    def apply(dataRange1: OWLDataRange, dataRange2: OWLDataRange, more: OWLDataRange*): OWLDataIntersectionOf =
      apply(more.toSet + dataRange1 + dataRange2)

    def unapply(intersection: OWLDataIntersectionOf): Option[Set[OWLDataRange]] = Option(intersection.getOperands.toSet)

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

  def apply[T: Literalable](value: T): OWLFacetRestriction = {
    val literalable = implicitly[Literalable[T]]
    factory.getOWLFacetRestriction(facet, literalable.toLiteral(value))
  }

  def unapply(facetRestriction: OWLFacetRestriction): Option[OWLLiteral] = Option(facetRestriction.getFacetValue)

}