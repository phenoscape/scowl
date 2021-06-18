package org.phenoscape.scowl.ofn

import org.phenoscape.scowl.Vocab
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model._

import scala.jdk.CollectionConverters._

trait ObjectExpressions extends Vocab {
  
  private val factory = OWLManager.getOWLDataFactory

  object ObjectIntersectionOf {

    def apply(operands: OWLClassExpression*): OWLObjectIntersectionOf =
      apply(operands.toSet)

    def apply(operands: Set[_ <: OWLClassExpression]): OWLObjectIntersectionOf =
      factory.getOWLObjectIntersectionOf(operands.asJava)

    def unapply(expression: OWLObjectIntersectionOf): Option[Set[_ <: OWLClassExpression]] =
      Option(expression.getOperands.asScala.toSet)

  }

  object ObjectUnionOf {

    def apply(operands: OWLClassExpression*): OWLObjectUnionOf =
      apply(operands.toSet)

    def apply(operands: Set[_ <: OWLClassExpression]): OWLObjectUnionOf =
      factory.getOWLObjectUnionOf(operands.asJava)

    def unapply(expression: OWLObjectUnionOf): Option[Set[_ <: OWLClassExpression]] =
      Option(expression.getOperands.asScala.toSet)

  }

  object ObjectOneOf {

    def apply(individuals: OWLIndividual*): OWLObjectOneOf =
      apply(individuals.toSet)

    def apply(individuals: Set[_ <: OWLIndividual]): OWLObjectOneOf =
      factory.getOWLObjectOneOf(individuals.asJava)

    def unapply(expression: OWLObjectOneOf): Option[Set[_ <: OWLIndividual]] =
      Option(expression.getIndividuals.asScala.toSet)

  }

  object ObjectHasValue {

    def apply(property: OWLObjectPropertyExpression, value: OWLIndividual): OWLObjectHasValue =
      factory.getOWLObjectHasValue(property, value)

    def unapply(expression: OWLObjectHasValue): Option[(OWLObjectPropertyExpression, OWLIndividual)] =
      Option((expression.getProperty, expression.getFiller))

  }

  object ObjectHasSelf {

    def apply(property: OWLObjectPropertyExpression): OWLObjectHasSelf = factory.getOWLObjectHasSelf(property)

    def unapply(expression: OWLObjectHasSelf): Option[OWLObjectPropertyExpression] = Option(expression.getProperty)

  }

  object ObjectComplementOf {

    def apply(operand: OWLClassExpression): OWLObjectComplementOf =
      factory.getOWLObjectComplementOf(operand)

    def unapply(complement: OWLObjectComplementOf): Option[OWLClassExpression] =
      Option(complement.getOperand)

  }

  object ObjectSomeValuesFrom {

    def apply(property: OWLObjectPropertyExpression, filler: OWLClassExpression): OWLObjectSomeValuesFrom =
      factory.getOWLObjectSomeValuesFrom(property, filler)

    def unapply(expression: OWLObjectSomeValuesFrom): Option[(OWLObjectPropertyExpression, OWLClassExpression)] =
      Option((expression.getProperty, expression.getFiller))

  }

  object ObjectAllValuesFrom {

    def apply(property: OWLObjectPropertyExpression, filler: OWLClassExpression): OWLObjectAllValuesFrom =
      factory.getOWLObjectAllValuesFrom(property, filler)

    def unapply(expression: OWLObjectAllValuesFrom): Option[(OWLObjectPropertyExpression, OWLClassExpression)] =
      Option((expression.getProperty, expression.getFiller))

  }

  object ObjectExactCardinality {

    def apply(cardinality: Int, property: OWLObjectPropertyExpression, filler: OWLClassExpression = OWLThing): OWLObjectExactCardinality =
      factory.getOWLObjectExactCardinality(cardinality, property, filler)

    def unapply(expression: OWLObjectExactCardinality): Option[(Int, OWLObjectPropertyExpression, OWLClassExpression)] =
      Option((expression.getCardinality, expression.getProperty, expression.getFiller))

  }

  object ObjectMinCardinality {

    def apply(cardinality: Int, property: OWLObjectPropertyExpression, filler: OWLClassExpression = OWLThing): OWLObjectMinCardinality =
      factory.getOWLObjectMinCardinality(cardinality, property, filler)

    def unapply(expression: OWLObjectMinCardinality): Option[(Int, OWLObjectPropertyExpression, OWLClassExpression)] =
      Option((expression.getCardinality, expression.getProperty, expression.getFiller))

  }

  object ObjectMaxCardinality {

    def apply(cardinality: Int, property: OWLObjectPropertyExpression, filler: OWLClassExpression = OWLThing): OWLObjectMaxCardinality =
      factory.getOWLObjectMaxCardinality(cardinality, property, filler)

    def unapply(expression: OWLObjectMaxCardinality): Option[(Int, OWLObjectPropertyExpression, OWLClassExpression)] =
      Option((expression.getCardinality, expression.getProperty, expression.getFiller))

  }

  object ObjectInverseOf {

    def apply(property: OWLObjectPropertyExpression): OWLObjectInverseOf = factory.getOWLObjectInverseOf(property)

    def unapply(inverse: OWLObjectInverseOf): Option[OWLObjectPropertyExpression] = Option(inverse.getInverse)

  }

}