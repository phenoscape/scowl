package org.phenoscape.scowl

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.OWLObjectProperty

object Functional extends App {

  val factory = OWLManager.getOWLDataFactory
  
  case class Property(iri: String)

  sealed trait ClassExpression

  case class Class(iri: String) extends ClassExpression

  case class ObjectIntersectionOf(expressions: ClassExpression*) extends ClassExpression

  object ObjectIntersectionOfExtractor extends ClassExpression {

    def unapplySeq(expression: ObjectIntersectionOf): Option[Seq[ClassExpression]] = {
      Option(expression.expressions)
    }

  }

  case class ObjectUnionOf(expression: ClassExpression*) extends ClassExpression

  case class ObjectSomeValuesFrom(property: Property, filler: ClassExpression) extends ClassExpression

  val a = Class("http://example.org/a")
  val b = Class("http://example.org/b")

  val AandB: ClassExpression = ObjectIntersectionOf(a, b)
  val someClass: ClassExpression = a
  val findMe: ClassExpression = ObjectIntersectionOf(ObjectSomeValuesFrom(Property("http://example.org/part_of"), a), b)

  findMe match {
    //case ObjectIntersectionOf(first, rest @ _*) => println(first, rest)
    case ObjectUnionOf(items @ _*) => {}
    case ObjectSomeValuesFrom(property, filler) => {}
    case ObjectIntersectionOf(ObjectSomeValuesFrom(property, filler), other) => println(property)
  }
  
  class Axiom
 
  
  class ClassAndAxioms {
    
    def SubClassOf(term: Class): Set[Axiom] = ???
    
    def apply(a: ClassAndAxioms => AnyRef) = a(this)
    
  }
  
  class Ontology {
    
    def Classs(text: String): ClassAndAxioms = {
      ???
    }
    
  }
  
  object Ont extends Ontology {
    
    val res = Classs ("head") { t =>
      t SubClassOf a
    }
  }


}