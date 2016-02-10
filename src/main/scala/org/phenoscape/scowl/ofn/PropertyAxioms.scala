package org.phenoscape.scowl.ofn

import scala.collection.JavaConversions._
import org.phenoscape.scowl.factory
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom
import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom
import org.semanticweb.owlapi.model.OWLDataRange
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom
import org.semanticweb.owlapi.model.OWLPropertyExpression
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLUnaryPropertyAxiom
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom

trait PropertyAxioms {

  object SubObjectPropertyOf {

    def apply(annotations: Set[OWLAnnotation], subProperty: OWLObjectPropertyExpression, superProperty: OWLObjectPropertyExpression): OWLSubObjectPropertyOfAxiom =
      factory.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty)

    def apply(subProperty: OWLObjectPropertyExpression, superProperty: OWLObjectPropertyExpression): OWLSubObjectPropertyOfAxiom =
      apply(Set.empty, subProperty, superProperty)

    def unapply(axiom: OWLSubObjectPropertyOfAxiom): Option[(Set[OWLAnnotation], OWLObjectPropertyExpression, OWLObjectPropertyExpression)] =
      Option(axiom.getAnnotations.toSet, axiom.getSubProperty, axiom.getSuperProperty)

  }

  object ObjectPropertyDomain {

    def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, domain: OWLClassExpression): OWLObjectPropertyDomainAxiom =
      factory.getOWLObjectPropertyDomainAxiom(property, domain)

    def apply(property: OWLObjectPropertyExpression, domain: OWLClassExpression): OWLObjectPropertyDomainAxiom =
      apply(Set.empty, property, domain)

    def unapply(axiom: OWLObjectPropertyDomainAxiom): Option[(Set[OWLAnnotation], OWLObjectPropertyExpression, OWLClassExpression)] =
      Option(axiom.getAnnotations.toSet, axiom.getProperty, axiom.getDomain)

  }

  object ObjectPropertyRange {

    def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, domain: OWLClassExpression): OWLObjectPropertyRangeAxiom =
      factory.getOWLObjectPropertyRangeAxiom(property, domain)

    def apply(property: OWLObjectPropertyExpression, domain: OWLClassExpression): OWLObjectPropertyRangeAxiom =
      apply(Set.empty, property, domain)

    def unapply(axiom: OWLObjectPropertyRangeAxiom): Option[(Set[OWLAnnotation], OWLObjectPropertyExpression, OWLClassExpression)] =
      Option(axiom.getAnnotations.toSet, axiom.getProperty, axiom.getRange)

  }

  object DataPropertyDomain {

    def apply(annotations: Set[OWLAnnotation], property: OWLDataPropertyExpression, domain: OWLClassExpression): OWLDataPropertyDomainAxiom =
      factory.getOWLDataPropertyDomainAxiom(property, domain)

    def apply(property: OWLDataPropertyExpression, domain: OWLClassExpression): OWLDataPropertyDomainAxiom =
      apply(Set.empty, property, domain)

    def unapply(axiom: OWLDataPropertyDomainAxiom): Option[(Set[OWLAnnotation], OWLDataPropertyExpression, OWLClassExpression)] =
      Option(axiom.getAnnotations.toSet, axiom.getProperty, axiom.getDomain)

  }

  object DataPropertyRange {

    def apply(annotations: Set[OWLAnnotation], property: OWLDataPropertyExpression, range: OWLDataRange): OWLDataPropertyRangeAxiom =
      factory.getOWLDataPropertyRangeAxiom(property, range)

    def apply(property: OWLDataPropertyExpression, range: OWLDataRange): OWLDataPropertyRangeAxiom =
      apply(Set.empty, property, range)

    def unapply(axiom: OWLDataPropertyRangeAxiom): Option[(Set[OWLAnnotation], OWLDataPropertyExpression, OWLDataRange)] =
      Option(axiom.getAnnotations.toSet, axiom.getProperty, axiom.getRange)

  }

  object InverseObjectProperties {

    def apply(annotations: Set[OWLAnnotation], property1: OWLObjectPropertyExpression, property2: OWLObjectPropertyExpression): OWLInverseObjectPropertiesAxiom =
      factory.getOWLInverseObjectPropertiesAxiom(property1, property2, annotations)

    def apply(property1: OWLObjectPropertyExpression, property2: OWLObjectPropertyExpression): OWLInverseObjectPropertiesAxiom =
      apply(Set.empty, property1, property2)

    def unapply(axiom: OWLInverseObjectPropertiesAxiom): Option[(Set[OWLAnnotation], OWLObjectPropertyExpression, OWLObjectPropertyExpression)] =
      Option(axiom.getAnnotations.toSet, axiom.getFirstProperty, axiom.getSecondProperty)

  }

  object DisjointObjectProperties {

    def apply(annotations: Set[OWLAnnotation], properties: Set[OWLObjectPropertyExpression]): OWLDisjointObjectPropertiesAxiom =
      factory.getOWLDisjointObjectPropertiesAxiom(properties, annotations)

    def apply(properties: OWLObjectPropertyExpression*): OWLDisjointObjectPropertiesAxiom =
      apply(Set.empty[OWLAnnotation], properties.toSet)

    def apply(properties: Set[OWLObjectPropertyExpression]): OWLDisjointObjectPropertiesAxiom =
      apply(Set.empty[OWLAnnotation], properties)

    def apply(annotations: OWLAnnotation*)(properties: OWLObjectPropertyExpression*): OWLDisjointObjectPropertiesAxiom =
      apply(annotations.toSet, properties.toSet)

    def unapply(axiom: OWLDisjointObjectPropertiesAxiom): Option[(Set[OWLAnnotation], Set[OWLObjectPropertyExpression])] =
      Option(axiom.getAnnotations.toSet, axiom.getProperties.toSet)

  }

  object SymmetricObjectProperty extends UnaryObjectPropertyAxiom[OWLSymmetricObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = factory.getOWLSymmetricObjectPropertyAxiom(_: OWLObjectPropertyExpression, _: Set[OWLAnnotation])

  }

  object AsymmetricObjectProperty extends UnaryObjectPropertyAxiom[OWLAsymmetricObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = factory.getOWLAsymmetricObjectPropertyAxiom(_: OWLObjectPropertyExpression, _: Set[OWLAnnotation])

  }

  object ReflexiveObjectProperty extends UnaryObjectPropertyAxiom[OWLReflexiveObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = factory.getOWLReflexiveObjectPropertyAxiom(_: OWLObjectPropertyExpression, _: Set[OWLAnnotation])

  }

  object IrreflexiveObjectProperty extends UnaryObjectPropertyAxiom[OWLIrreflexiveObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = factory.getOWLIrreflexiveObjectPropertyAxiom(_: OWLObjectPropertyExpression, _: Set[OWLAnnotation])

  }

  object FunctionalObjectProperty extends UnaryObjectPropertyAxiom[OWLFunctionalObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = factory.getOWLFunctionalObjectPropertyAxiom(_: OWLObjectPropertyExpression, _: Set[OWLAnnotation])

  }

  object FunctionalDataProperty extends UnaryObjectPropertyAxiom[OWLFunctionalDataPropertyAxiom, OWLDataPropertyExpression] {

    val constructor = factory.getOWLFunctionalDataPropertyAxiom(_: OWLDataPropertyExpression, _: Set[OWLAnnotation])

  }

  object InverseFunctionalObjectProperty extends UnaryObjectPropertyAxiom[OWLInverseFunctionalObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = factory.getOWLInverseFunctionalObjectPropertyAxiom(_: OWLObjectPropertyExpression, _: Set[OWLAnnotation])

  }

  object TransitiveObjectProperty extends UnaryObjectPropertyAxiom[OWLTransitiveObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = factory.getOWLTransitiveObjectPropertyAxiom(_: OWLObjectPropertyExpression, _: Set[OWLAnnotation])

  }

}

trait UnaryObjectPropertyAxiom[T <: OWLUnaryPropertyAxiom[P], P <: OWLPropertyExpression[_, _]] {

  def constructor: (P, Set[OWLAnnotation]) => T

  def apply(annotations: Set[OWLAnnotation], property: P): T = constructor(property, annotations)

  def apply(property: P): T = apply(Set.empty, property)

  def unapply(axiom: T): Option[(Set[OWLAnnotation], P)] =
    Option(axiom.getAnnotations.toSet, axiom.getProperty)
}

