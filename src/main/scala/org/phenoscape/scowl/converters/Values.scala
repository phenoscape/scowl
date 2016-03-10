package org.phenoscape.scowl.converters

import org.semanticweb.owlapi.model.OWLLiteral
import org.semanticweb.owlapi.model.OWLNamedObject
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.apibinding.OWLManager

trait Values {

  private val factory = OWLManager.getOWLDataFactory

  implicit object IntLiterable extends Literalable[Int] {

    def toLiteral(value: Int): OWLLiteral = factory.getOWLLiteral(value)

  }

  implicit object FloatLiterable extends Literalable[Float] {

    def toLiteral(value: Float): OWLLiteral = factory.getOWLLiteral(value)

  }

  implicit object DoubleLiterable extends Literalable[Double] {

    def toLiteral(value: Double): OWLLiteral = factory.getOWLLiteral(value)

  }

  implicit object StringLiterable extends Literalable[String] {

    def toLiteral(value: String): OWLLiteral = factory.getOWLLiteral(value)

  }

  implicit object BooleanLiterable extends Literalable[Boolean] {

    def toLiteral(value: Boolean): OWLLiteral = factory.getOWLLiteral(value)

  }

  implicit object LiteralLiterable extends Literalable[OWLLiteral] {

    def toLiteral(value: OWLLiteral): OWLLiteral = value

  }

  implicit object AnnotationValueValuer extends AnnotationValuer[OWLAnnotationValue] {

    def toAnnotationValue(value: OWLAnnotationValue): OWLAnnotationValue = value

  }

  implicit object NamedObjectValuer extends AnnotationValuer[OWLNamedObject] {

    def toAnnotationValue(value: OWLNamedObject): OWLAnnotationValue = value.getIRI

  }

}

trait AnnotationValuer[-T] {

  def toAnnotationValue(value: T): OWLAnnotationValue

}

trait Literalable[-T] extends AnnotationValuer[T] {

  def toLiteral(value: T): OWLLiteral

  def toAnnotationValue(value: T): OWLAnnotationValue = toLiteral(value)

}