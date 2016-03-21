package org.phenoscape

import scala.collection.JavaConversions._

import org.phenoscape.scowl.converters.AnnotationValuer
import org.phenoscape.scowl.converters.Literalable
import org.phenoscape.scowl.omn.PropertyCharacteristic
import org.phenoscape.scowl.omn.ScowlNegativeDataPropertyValue
import org.phenoscape.scowl.omn.ScowlNegativeObjectPropertyValue
import org.phenoscape.scowl.omn.ScowlPropertyChain
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationSubject
import org.semanticweb.owlapi.model.OWLAnnotationValue
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom
import org.semanticweb.owlapi.model.OWLDataExactCardinality
import org.semanticweb.owlapi.model.OWLDataHasValue
import org.semanticweb.owlapi.model.OWLDataIntersectionOf
import org.semanticweb.owlapi.model.OWLDataMaxCardinality
import org.semanticweb.owlapi.model.OWLDataMinCardinality
import org.semanticweb.owlapi.model.OWLDataOneOf
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLDataPropertyCharacteristicAxiom
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom
import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom
import org.semanticweb.owlapi.model.OWLDataRange
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom
import org.semanticweb.owlapi.model.OWLDataUnionOf
import org.semanticweb.owlapi.model.OWLDatatype
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom
import org.semanticweb.owlapi.model.OWLDatatypeRestriction
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
import org.semanticweb.owlapi.model.OWLFacetRestriction
import org.semanticweb.owlapi.model.OWLHasKeyAxiom
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom
import org.semanticweb.owlapi.model.OWLLiteral
import org.semanticweb.owlapi.model.OWLNamedObject
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom
import org.semanticweb.owlapi.model.OWLObjectExactCardinality
import org.semanticweb.owlapi.model.OWLObjectHasSelf
import org.semanticweb.owlapi.model.OWLObjectHasValue
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality
import org.semanticweb.owlapi.model.OWLObjectMinCardinality
import org.semanticweb.owlapi.model.OWLObjectOneOf
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom
import org.semanticweb.owlapi.model.OWLObjectUnionOf
import org.semanticweb.owlapi.model.OWLPropertyExpression
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom

package object scowl extends Vocab
    with ofn.Entities
    with ofn.AnnotationAxioms
    with ofn.ClassAxioms
    with ofn.IndividualAxioms
    with ofn.PropertyAxioms
    with ofn.ObjectExpressions
    with ofn.DataExpressions
    with omn.ClassExpressions
    with omn.PropertyCharacteristics
    with omn.Facets
    with converters.AnnotationSubjects
    with converters.Values {

  private val factory = OWLManager.getOWLDataFactory

  // Implicit value classes cannot be placed inside a trait and so must be defined here

  implicit class ScowlClassExpression(val self: OWLClassExpression) extends AnyVal {

    def and(other: OWLClassExpression): OWLObjectIntersectionOf = factory.getOWLObjectIntersectionOf(self.asConjunctSet + other)

    def or(other: OWLClassExpression): OWLObjectUnionOf = factory.getOWLObjectUnionOf(self.asDisjunctSet + other)

    def SubClassOf(other: OWLClassExpression): OWLSubClassOfAxiom = factory.getOWLSubClassOfAxiom(self, other)

    def EquivalentTo(other: OWLClassExpression): OWLEquivalentClassesAxiom = factory.getOWLEquivalentClassesAxiom(self, other)

    def DisjointWith(other: OWLClassExpression): OWLDisjointClassesAxiom = factory.getOWLDisjointClassesAxiom(self, other)

    def HasKey(property: OWLPropertyExpression[_, _], more: OWLPropertyExpression[_, _]*): OWLHasKeyAxiom = factory.getOWLHasKeyAxiom(self, more.toSet + property)

  }

  implicit class ScowlObjectOneOf(val self: OWLObjectOneOf) extends AnyVal {

    def ~(ind: OWLIndividual) = factory.getOWLObjectOneOf(self.getIndividuals + ind)

  }

  implicit class ScowlDataRange(val self: OWLDataRange) extends AnyVal {

    def and(other: OWLDataRange): OWLDataIntersectionOf = (self, other) match {
      case (s: OWLDataIntersectionOf, o: OWLDataIntersectionOf) => factory.getOWLDataIntersectionOf(s.getOperands ++ o.getOperands)
      case (s: OWLDataIntersectionOf, _) => factory.getOWLDataIntersectionOf(s.getOperands + other)
      case (_, o: OWLDataIntersectionOf) => factory.getOWLDataIntersectionOf(o.getOperands + self)
      case _ => factory.getOWLDataIntersectionOf(Set(self, other))
    }

    def or(other: OWLDataRange): OWLDataUnionOf = (self, other) match {
      case (s: OWLDataUnionOf, o: OWLDataIntersectionOf) => factory.getOWLDataUnionOf(s.getOperands ++ o.getOperands)
      case (s: OWLDataUnionOf, _) => factory.getOWLDataUnionOf(s.getOperands + other)
      case (_, o: OWLDataUnionOf) => factory.getOWLDataUnionOf(o.getOperands + self)
      case _ => factory.getOWLDataUnionOf(Set(self, other))
    }

  }

  implicit class ScowlDataType(val self: OWLDatatype) extends AnyVal {

    def apply(facet: OWLFacetRestriction, more: OWLFacetRestriction*): OWLDatatypeRestriction = factory.getOWLDatatypeRestriction(self, more.toSet + facet)

    def EquivalentTo(range: OWLDataRange): OWLDatatypeDefinitionAxiom = factory.getOWLDatatypeDefinitionAxiom(self, range)

  }

  implicit class ScowlIndividual(val self: OWLIndividual) extends AnyVal {

    def Fact(property: OWLObjectPropertyExpression, value: OWLIndividual): OWLObjectPropertyAssertionAxiom = factory.getOWLObjectPropertyAssertionAxiom(property, self, value)

    //TODO clean up Facts to handle multiple kinds of facts
    def Facts(facts: (OWLObjectPropertyExpression, OWLIndividual)*): Set[OWLObjectPropertyAssertionAxiom] =
      (facts map { case (property, value) => factory.getOWLObjectPropertyAssertionAxiom(property, self, value) }).toSet

    def Fact[T: Literalable](property: OWLDataPropertyExpression, value: T): OWLDataPropertyAssertionAxiom = {
      val literalable = implicitly[Literalable[T]]
      factory.getOWLDataPropertyAssertionAxiom(property, self, literalable.toLiteral(value))
    }

    def Fact(negative: ScowlNegativeObjectPropertyValue): OWLNegativeObjectPropertyAssertionAxiom = factory.getOWLNegativeObjectPropertyAssertionAxiom(negative.property, self, negative.value)

    def Fact[T: Literalable](negative: ScowlNegativeDataPropertyValue[T]): OWLNegativeDataPropertyAssertionAxiom = factory.getOWLNegativeDataPropertyAssertionAxiom(negative.property, self, negative.literal)

    def Type(owlClass: OWLClassExpression): OWLClassAssertionAxiom = factory.getOWLClassAssertionAxiom(owlClass, self)

    def Types(owlClass: OWLClassExpression, more: OWLClassExpression*): Set[OWLClassAssertionAxiom] =
      (more.toSet + owlClass).map(factory.getOWLClassAssertionAxiom(_, self))

    def ~(other: OWLIndividual): OWLObjectOneOf = factory.getOWLObjectOneOf(self, other)

    def SameAs(other: OWLIndividual, more: OWLIndividual*): OWLSameIndividualAxiom = factory.getOWLSameIndividualAxiom(more.toSet + other + self)

    def DifferentFrom(other: OWLIndividual, more: OWLIndividual*): OWLDifferentIndividualsAxiom = factory.getOWLDifferentIndividualsAxiom(more.toSet + other + self)

  }

  implicit class ScowlObjectProperty(val self: OWLObjectPropertyExpression) extends AnyVal {

    def some(classExpression: OWLClassExpression): OWLObjectSomeValuesFrom = factory.getOWLObjectSomeValuesFrom(self, classExpression)

    def only(classExpression: OWLClassExpression): OWLObjectAllValuesFrom = factory.getOWLObjectAllValuesFrom(self, classExpression)

    def exactly(cardinality: Int, classExpression: OWLClassExpression): OWLObjectExactCardinality = factory.getOWLObjectExactCardinality(cardinality, self, classExpression)

    def exactly(cardinality: Int): OWLObjectExactCardinality = factory.getOWLObjectExactCardinality(cardinality, self)

    def min(cardinality: Int, classExpression: OWLClassExpression): OWLObjectMinCardinality = factory.getOWLObjectMinCardinality(cardinality, self, classExpression)

    def min(cardinality: Int): OWLObjectMinCardinality = factory.getOWLObjectMinCardinality(cardinality, self)

    def max(cardinality: Int, classExpression: OWLClassExpression): OWLObjectMaxCardinality = factory.getOWLObjectMaxCardinality(cardinality, self, classExpression)

    def max(cardinality: Int): OWLObjectMaxCardinality = factory.getOWLObjectMaxCardinality(cardinality, self)

    def value(individual: OWLIndividual): OWLObjectHasValue = factory.getOWLObjectHasValue(self, individual)

    def SubPropertyOf(other: OWLObjectPropertyExpression): OWLSubObjectPropertyOfAxiom = factory.getOWLSubObjectPropertyOfAxiom(self, other)

    def o(property: OWLObjectPropertyExpression): ScowlPropertyChain = new ScowlPropertyChain(self, property)

    def SubPropertyChain(chain: ScowlPropertyChain): OWLSubPropertyChainOfAxiom = factory.getOWLSubPropertyChainOfAxiom(chain.properties, self)

    def Self: OWLObjectHasSelf = factory.getOWLObjectHasSelf(self)

    def Domain(domain: OWLClassExpression): OWLObjectPropertyDomainAxiom = factory.getOWLObjectPropertyDomainAxiom(self, domain)

    def Range(range: OWLClassExpression): OWLObjectPropertyRangeAxiom = factory.getOWLObjectPropertyRangeAxiom(self, range)

    def InverseOf(other: OWLObjectPropertyExpression): OWLInverseObjectPropertiesAxiom = factory.getOWLInverseObjectPropertiesAxiom(self, other)

    def DisjointWith(other: OWLObjectPropertyExpression): OWLDisjointObjectPropertiesAxiom = factory.getOWLDisjointObjectPropertiesAxiom(self, other)

    def Characteristic[T <: OWLObjectPropertyCharacteristicAxiom](characteristic: PropertyCharacteristic[T, _]): T = characteristic.axiom(self)

  }

  implicit class ScowlDataProperty(val self: OWLDataPropertyExpression) extends AnyVal {

    def some(range: OWLDataRange): OWLDataSomeValuesFrom = factory.getOWLDataSomeValuesFrom(self, range)

    def only(range: OWLDataRange): OWLDataAllValuesFrom = factory.getOWLDataAllValuesFrom(self, range)

    def exactly(cardinality: Int, range: OWLDataRange): OWLDataExactCardinality = factory.getOWLDataExactCardinality(cardinality, self, range)

    def exactly(cardinality: Int): OWLDataExactCardinality = factory.getOWLDataExactCardinality(cardinality, self)

    def min(cardinality: Int, range: OWLDataRange): OWLDataMinCardinality = factory.getOWLDataMinCardinality(cardinality, self, range)

    def min(cardinality: Int): OWLDataMinCardinality = factory.getOWLDataMinCardinality(cardinality, self)

    def max(cardinality: Int, range: OWLDataRange): OWLDataMaxCardinality = factory.getOWLDataMaxCardinality(cardinality, self, range)

    def max(cardinality: Int): OWLDataMaxCardinality = factory.getOWLDataMaxCardinality(cardinality, self)

    def value[T: Literalable](value: T): OWLDataHasValue = {
      val literalable = implicitly[Literalable[T]]
      factory.getOWLDataHasValue(self, literalable.toLiteral(value))
    }

    def Domain(domain: OWLClassExpression): OWLDataPropertyDomainAxiom = factory.getOWLDataPropertyDomainAxiom(self, domain)

    def Range(range: OWLDataRange): OWLDataPropertyRangeAxiom = factory.getOWLDataPropertyRangeAxiom(self, range)

    def Characteristic[T <: OWLDataPropertyCharacteristicAxiom](characteristic: PropertyCharacteristic[_, T]): T = characteristic.axiom(self)

  }

  implicit class ScowlAnnotationSubject(val self: OWLAnnotationSubject) extends AnyVal {

    def Annotation[T: AnnotationValuer](property: OWLAnnotationProperty, value: T): OWLAnnotationAssertionAxiom = {
      val valuer = implicitly[AnnotationValuer[T]]
      factory.getOWLAnnotationAssertionAxiom(property, self, valuer.toAnnotationValue(value))
    }

  }

  implicit class ScowlAxiom(val self: OWLAxiom) extends AnyVal {

    def Annotation[T: AnnotationValuer](property: OWLAnnotationProperty, value: T): OWLAxiom = {
      val valuer = implicitly[AnnotationValuer[T]]
      self.getAnnotatedAxiom(Set(factory.getOWLAnnotation(property, valuer.toAnnotationValue(value))))
    }

    def Annotations[T: AnnotationValuer](annotations: (OWLAnnotationProperty, T)*): OWLAxiom = {
      val valuer = implicitly[AnnotationValuer[T]]
      self.getAnnotatedAxiom(annotations.map { case (property, value) => factory.getOWLAnnotation(property, valuer.toAnnotationValue(value)) }.toSet[OWLAnnotation])
    }

  }

  implicit class ScowlNamedObject(val self: OWLNamedObject) extends AnyVal {

    def Annotation[T: AnnotationValuer](property: OWLAnnotationProperty, value: T): OWLAnnotationAssertionAxiom = {
      val valuer = implicitly[AnnotationValuer[T]]
      Annotation(property, valuer.toAnnotationValue(value))
    }

    def Annotation(property: OWLAnnotationProperty, value: OWLAnnotationValue): OWLAnnotationAssertionAxiom = factory.getOWLAnnotationAssertionAxiom(property, self.getIRI, value)

  }

  implicit class ScowlLiteralString(val self: String) extends AnyVal {

    def ^^(datatype: OWLDatatype): OWLLiteral = factory.getOWLLiteral(self, datatype)

    def ^^(datatypeIRI: IRI): OWLLiteral = factory.getOWLLiteral(self, factory.getOWLDatatype(datatypeIRI))

    def ^^(datatypeIRI: String): OWLLiteral = factory.getOWLLiteral(self, factory.getOWLDatatype(IRI.create(datatypeIRI)))

    /**
     * Create plain literal with language tag
     * '@' by itself is not a valid identifier in Scala
     */
    def @@(lang: String): OWLLiteral = factory.getOWLLiteral(self, lang)

  }

  implicit class ScowlLiteral(val self: OWLLiteral) extends AnyVal {

    def ~[T: Literalable](value: T): OWLDataOneOf = {
      val literalable = implicitly[Literalable[T]]
      factory.getOWLDataOneOf(self, literalable.toLiteral(value))
    }

  }

  implicit class ScowlDataOneOf(val self: OWLDataOneOf) extends AnyVal {

    def ~[T: Literalable](value: T): OWLDataOneOf = {
      val literalable = implicitly[Literalable[T]]
      factory.getOWLDataOneOf(self.getValues + literalable.toLiteral(value))
    }

  }

}