package org.phenoscape.scowl.example

import org.phenoscape.scowl.Functional._
import org.phenoscape.scowl.OWL._
import org.semanticweb.owlapi.model.OWLClassExpression

object FunctionalSyntax extends App {

  /**
   * Example of using pattern matching with functional syntax extractors
   */
  def nnf(given: OWLClassExpression): OWLClassExpression = given match {
    case Class(_) => given
    case ObjectComplementOf(Class(_)) => given
    case ObjectComplementOf(ObjectComplementOf(expression)) => nnf(expression)
    case ObjectUnionOf(operands) => ObjectUnionOf(operands.map(nnf))
    case ObjectIntersectionOf(operands) => ObjectIntersectionOf(operands.map(nnf))
    case ObjectComplementOf(ObjectUnionOf(operands)) => ObjectIntersectionOf(operands.map(c => nnf(ObjectComplementOf(c))))
    case ObjectComplementOf(ObjectIntersectionOf(operands)) => ObjectUnionOf(operands.map(c => nnf(ObjectComplementOf(c))))
    case ObjectAllValuesFrom(property, filler) => ObjectAllValuesFrom(property, nnf(filler))
    case ObjectSomeValuesFrom(property, filler) => ObjectSomeValuesFrom(property, nnf(filler))
    case ObjectMinCardinality(num, property, filler) => ObjectMinCardinality(num, property, nnf(filler))
    case ObjectMaxCardinality(num, property, filler) => ObjectMaxCardinality(num, property, nnf(filler))
    case ObjectExactCardinality(num, property, filler) => ObjectExactCardinality(num, property, nnf(filler))
    case ObjectComplementOf(ObjectAllValuesFrom(property, filler)) => ObjectSomeValuesFrom(property, nnf(ObjectComplementOf(filler)))
    case ObjectComplementOf(ObjectSomeValuesFrom(property, filler)) => ObjectAllValuesFrom(property, nnf(ObjectComplementOf(filler)))
    case ObjectComplementOf(ObjectMinCardinality(num, property, filler)) => ObjectMaxCardinality(math.max(num - 1, 0), property, filler)
    case ObjectComplementOf(ObjectMaxCardinality(num, property, filler)) => ObjectMinCardinality(num + 1, property, filler)
    case ObjectComplementOf(ObjectExactCardinality(num, property, filler)) => ObjectUnionOf(ObjectMinCardinality(num + 1, property, filler), ObjectMaxCardinality(math.max(num - 1, 0), property, filler))
  }

  // has_part some (entity and (bearer_of some quality))

  val HasPart = ObjectProperty("")
  val PartOf = ObjectProperty("")
  val BearerOf = ObjectProperty("")
  val InheresIn = ObjectProperty("")

  //  def entityFocusToQualityFocus(given: OWLClassExpression): OWLClassExpression = given match {
  //    case ObjectSomeValuesFrom(HasPart, ObjectIntersectionOf(entity, ObjectSomeValuesFrom(BearerOf, quality))) =>
  //      ObjectIntersectionOf(quality, ObjectSomeValuesFrom(InheresIn, entity))
  //  }

//  def entityFocusToQualityFocus(given: OWLClassExpression): OWLClassExpression = given match {
//    case ObjectSomeValuesFrom(HasPart, ObjectIntersectionOf(operands)) =>
//      ObjectIntersectionOf(operands.map {
//        case ObjectSomeValuesFrom(BearerOf, quality) => ObjectSomeValuesFrom()
//      })
//  }

}