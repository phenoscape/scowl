package org.phenoscape

import org.phenoscape.scowl.converters.{AnnotationValuer, Literalable, SWRLDArgish, SWRLIArgish}
import org.phenoscape.scowl.omn._
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model._

import scala.jdk.CollectionConverters._

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
    with omn.SWRLAtoms
    with converters.AnnotationSubjects
    with converters.Values
    with converters.SWRLArgs {

  private val factory = OWLManager.getOWLDataFactory

  // Implicit value classes cannot be placed inside a trait and so must be defined here

  implicit class ScowlClassExpression(val self: OWLClassExpression) extends AnyVal {

    def and(other: OWLClassExpression): OWLObjectIntersectionOf = factory.getOWLObjectIntersectionOf((self.asConjunctSet.asScala.to(Set) + other).asJava)

    def or(other: OWLClassExpression): OWLObjectUnionOf = factory.getOWLObjectUnionOf((self.asDisjunctSet.asScala.to(Set) + other).asJava)

    def SubClassOf(other: OWLClassExpression): OWLSubClassOfAxiom = factory.getOWLSubClassOfAxiom(self, other)

    def EquivalentTo(other: OWLClassExpression): OWLEquivalentClassesAxiom = factory.getOWLEquivalentClassesAxiom(self, other)

    def DisjointWith(other: OWLClassExpression): OWLDisjointClassesAxiom = factory.getOWLDisjointClassesAxiom(self, other)

    def HasKey(property: OWLPropertyExpression, more: OWLPropertyExpression*): OWLHasKeyAxiom = factory.getOWLHasKeyAxiom(self, (more.toSet + property).asJava)

    def apply[T: SWRLIArgish](arg: T): SWRLClassAtom = {
      val argish = implicitly[SWRLIArgish[T]]
      factory.getSWRLClassAtom(self, argish.toArgument(arg))
    }

  }

  implicit class ScowlObjectOneOf(val self: OWLObjectOneOf) extends AnyVal {

    def ~(ind: OWLIndividual) = factory.getOWLObjectOneOf((self.getIndividuals.asScala.to(Set) + ind).asJava)

  }

  implicit class ScowlDataRange(val self: OWLDataRange) extends AnyVal {

    def and(other: OWLDataRange): OWLDataIntersectionOf = (self, other) match {
      case (s: OWLDataIntersectionOf, o: OWLDataIntersectionOf) => factory.getOWLDataIntersectionOf((s.getOperands.asScala ++ o.getOperands.asScala).asJava)
      case (s: OWLDataIntersectionOf, _) => factory.getOWLDataIntersectionOf((s.getOperands.asScala.to(Set) + other).asJava)
      case (_, o: OWLDataIntersectionOf) => factory.getOWLDataIntersectionOf((o.getOperands.asScala.to(Set) + self).asJava)
      case _ => factory.getOWLDataIntersectionOf(Set(self, other).asJava)
    }

    def or(other: OWLDataRange): OWLDataUnionOf = (self, other) match {
      case (s: OWLDataUnionOf, o: OWLDataIntersectionOf) => factory.getOWLDataUnionOf((s.getOperands.asScala ++ o.getOperands.asScala).asJava)
      case (s: OWLDataUnionOf, _) => factory.getOWLDataUnionOf((s.getOperands.asScala.to(Set) + other).asJava)
      case (_, o: OWLDataUnionOf) => factory.getOWLDataUnionOf((o.getOperands.asScala.to(Set) + self).asJava)
      case _ => factory.getOWLDataUnionOf(Set(self, other).asJava)
    }

    def apply[T: SWRLDArgish](arg: T): SWRLDataRangeAtom = {
      val argish = implicitly[SWRLDArgish[T]]
      factory.getSWRLDataRangeAtom(self, argish.toArgument(arg))
    }

  }

  implicit class ScowlDataType(val self: OWLDatatype) extends AnyVal {

    def apply(facet: OWLFacetRestriction, more: OWLFacetRestriction*): OWLDatatypeRestriction = factory.getOWLDatatypeRestriction(self, (more.toSet + facet).asJava)

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

    def SameAs(other: OWLIndividual, more: OWLIndividual*): OWLSameIndividualAxiom = factory.getOWLSameIndividualAxiom((more.toSet + other + self).asJava)

    def DifferentFrom(other: OWLIndividual, more: OWLIndividual*): OWLDifferentIndividualsAxiom = factory.getOWLDifferentIndividualsAxiom((more.toSet + other + self).asJava)

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

    def EquivalentTo(other: OWLObjectPropertyExpression): OWLEquivalentObjectPropertiesAxiom = factory.getOWLEquivalentObjectPropertiesAxiom(self, other)

    def SubPropertyOf(other: OWLObjectPropertyExpression): OWLSubObjectPropertyOfAxiom = factory.getOWLSubObjectPropertyOfAxiom(self, other)

    def o(property: OWLObjectPropertyExpression): ScowlPropertyChain = new ScowlPropertyChain(self, property)

    def SubPropertyChain(chain: ScowlPropertyChain): OWLSubPropertyChainOfAxiom = factory.getOWLSubPropertyChainOfAxiom(chain.properties.asJava, self)

    def Self: OWLObjectHasSelf = factory.getOWLObjectHasSelf(self)

    def Domain(domain: OWLClassExpression): OWLObjectPropertyDomainAxiom = factory.getOWLObjectPropertyDomainAxiom(self, domain)

    def Range(range: OWLClassExpression): OWLObjectPropertyRangeAxiom = factory.getOWLObjectPropertyRangeAxiom(self, range)

    def InverseOf(other: OWLObjectPropertyExpression): OWLInverseObjectPropertiesAxiom = factory.getOWLInverseObjectPropertiesAxiom(self, other)

    def DisjointWith(other: OWLObjectPropertyExpression): OWLDisjointObjectPropertiesAxiom = factory.getOWLDisjointObjectPropertiesAxiom(self, other)

    def Characteristic[T <: OWLObjectPropertyCharacteristicAxiom](characteristic: PropertyCharacteristic[T, _]): T = characteristic.axiom(self)

    def apply[S: SWRLIArgish, O: SWRLIArgish](subj: S, obj: O): SWRLObjectPropertyAtom = {
      val argishS = implicitly[SWRLIArgish[S]]
      val argishO = implicitly[SWRLIArgish[O]]
      factory.getSWRLObjectPropertyAtom(self, argishS.toArgument(subj), argishO.toArgument(obj))
    }

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

    def EquivalentTo(other: OWLDataPropertyExpression): OWLEquivalentDataPropertiesAxiom = factory.getOWLEquivalentDataPropertiesAxiom(self, other)

    def SubPropertyOf(other: OWLDataPropertyExpression): OWLSubDataPropertyOfAxiom = factory.getOWLSubDataPropertyOfAxiom(self, other)

    def Domain(domain: OWLClassExpression): OWLDataPropertyDomainAxiom = factory.getOWLDataPropertyDomainAxiom(self, domain)

    def Range(range: OWLDataRange): OWLDataPropertyRangeAxiom = factory.getOWLDataPropertyRangeAxiom(self, range)

    def DisjointWith(other: OWLDataPropertyExpression): OWLDisjointDataPropertiesAxiom = factory.getOWLDisjointDataPropertiesAxiom(self, other)

    def Characteristic[T <: OWLDataPropertyCharacteristicAxiom](characteristic: PropertyCharacteristic[_, T]): T = characteristic.axiom(self)

    def apply[S: SWRLIArgish, V: SWRLDArgish](subj: S, value: V): SWRLDataPropertyAtom = {
      val argishS = implicitly[SWRLIArgish[S]]
      val argishV = implicitly[SWRLDArgish[V]]
      factory.getSWRLDataPropertyAtom(self, argishS.toArgument(subj), argishV.toArgument(value))
    }

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
      self.getAnnotatedAxiom(Set(factory.getOWLAnnotation(property, valuer.toAnnotationValue(value))).asJava)
    }

    def Annotations[T: AnnotationValuer](annotations: (OWLAnnotationProperty, T)*): OWLAxiom = {
      val valuer = implicitly[AnnotationValuer[T]]
      self.getAnnotatedAxiom((annotations.map { case (property, value) => factory.getOWLAnnotation(property, valuer.toAnnotationValue(value)) }.toSet[OWLAnnotation]).asJava)
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
      factory.getOWLDataOneOf((self.getValues.asScala.to(Set) + literalable.toLiteral(value)).asJava)
    }

  }

  implicit class ScowlSWRLAtom(val self: SWRLAtom) extends AnyVal {

    def ^(other: SWRLAtom): ScowlSWRLConjunction = ScowlSWRLConjunction(Set(self, other))

    def -->(head: ScowlSWRLConjunction): SWRLRule = factory.getSWRLRule(Set(self).asJava, head.atoms.asJava)

    def -->(head: SWRLAtom): SWRLRule = factory.getSWRLRule(Set(self).asJava, Set(head).asJava)

  }

  implicit class SWRLVariableInterpolator(val sc: StringContext) extends AnyVal {

    def swrl(args: Any*): SWRLVariable = {
      val strings = sc.parts.iterator
      val expressions = args.iterator
      val buf = new StringBuilder(strings.next())
      while(strings.hasNext) {
        buf.append(expressions.next())
        buf.append(strings.next())
      }
      factory.getSWRLVariable(IRI.create(s"$iriPrefix$buf"))
    }

  }

}