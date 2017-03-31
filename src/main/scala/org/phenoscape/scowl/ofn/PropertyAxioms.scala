package org.phenoscape.scowl.ofn

import scala.collection.JavaConverters._

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom
import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom
import org.semanticweb.owlapi.model.OWLDataRange
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom
import org.semanticweb.owlapi.model.OWLPropertyExpression
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLUnaryPropertyAxiom

trait PropertyAxioms {

  private val factory = OWLManager.getOWLDataFactory

  object SubObjectPropertyOf {

    def apply(annotations: Set[OWLAnnotation], subProperty: OWLObjectPropertyExpression, superProperty: OWLObjectPropertyExpression): OWLSubObjectPropertyOfAxiom =
      factory.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, annotations.asJava)

    def apply(subProperty: OWLObjectPropertyExpression, superProperty: OWLObjectPropertyExpression): OWLSubObjectPropertyOfAxiom =
      apply(Set.empty, subProperty, superProperty)

    def unapply(axiom: OWLSubObjectPropertyOfAxiom): Option[(Set[OWLAnnotation], OWLObjectPropertyExpression, OWLObjectPropertyExpression)] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getSubProperty, axiom.getSuperProperty)

  }

  object SubObjectPropertyChainOf {

    def apply(annotations: Set[OWLAnnotation], subPropertyChain: List[OWLObjectPropertyExpression], superProperty: OWLObjectPropertyExpression): OWLSubPropertyChainOfAxiom =
      factory.getOWLSubPropertyChainOfAxiom(subPropertyChain.asJava, superProperty, annotations.asJava)

    def apply(subPropertyChain: List[OWLObjectPropertyExpression], superProperty: OWLObjectPropertyExpression): OWLSubPropertyChainOfAxiom =
      apply(Set.empty, subPropertyChain, superProperty)

    def unapply(axiom: OWLSubPropertyChainOfAxiom): Option[(Set[OWLAnnotation], List[OWLObjectPropertyExpression], OWLObjectPropertyExpression)] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getPropertyChain.asScala.toList, axiom.getSuperProperty)

  }

  object SubDataPropertyOf {

    def apply(annotations: Set[OWLAnnotation], subProperty: OWLDataPropertyExpression, superProperty: OWLDataPropertyExpression): OWLSubDataPropertyOfAxiom =
      factory.getOWLSubDataPropertyOfAxiom(subProperty, superProperty, annotations.asJava)

    def apply(subProperty: OWLDataPropertyExpression, superProperty: OWLDataPropertyExpression): OWLSubDataPropertyOfAxiom =
      apply(Set.empty, subProperty, superProperty)

    def unapply(axiom: OWLSubDataPropertyOfAxiom): Option[(Set[OWLAnnotation], OWLDataPropertyExpression, OWLDataPropertyExpression)] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getSubProperty, axiom.getSuperProperty)

  }

  object EquivalentObjectProperties {

    def apply(annotations: Set[OWLAnnotation], propertyExpressions: Set[OWLObjectPropertyExpression]): OWLEquivalentObjectPropertiesAxiom =
      factory.getOWLEquivalentObjectPropertiesAxiom(propertyExpressions.asJava, annotations.asJava)

    def apply(properties: OWLObjectPropertyExpression*): OWLEquivalentObjectPropertiesAxiom =
      apply(Set.empty[OWLAnnotation], properties.toSet)

    def apply(properties: Set[OWLObjectPropertyExpression]): OWLEquivalentObjectPropertiesAxiom =
      apply(Set.empty[OWLAnnotation], properties)

    def apply(annotations: OWLAnnotation*)(properties: OWLObjectPropertyExpression*): OWLEquivalentObjectPropertiesAxiom =
      apply(annotations.toSet, properties.toSet)

    def unapply(axiom: OWLEquivalentObjectPropertiesAxiom): Option[(Set[OWLAnnotation], Set[OWLObjectPropertyExpression])] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getProperties.asScala.toSet)

  }

  object EquivalentDataProperties {

    def apply(annotations: Set[OWLAnnotation], propertyExpressions: Set[OWLDataPropertyExpression]): OWLEquivalentDataPropertiesAxiom =
      factory.getOWLEquivalentDataPropertiesAxiom(propertyExpressions.asJava, annotations.asJava)

    def apply(properties: OWLDataPropertyExpression*): OWLEquivalentDataPropertiesAxiom =
      apply(Set.empty[OWLAnnotation], properties.toSet)

    def apply(properties: Set[OWLDataPropertyExpression]): OWLEquivalentDataPropertiesAxiom =
      apply(Set.empty[OWLAnnotation], properties)

    def apply(annotations: OWLAnnotation*)(properties: OWLDataPropertyExpression*): OWLEquivalentDataPropertiesAxiom =
      apply(annotations.toSet, properties.toSet)

    def unapply(axiom: OWLEquivalentDataPropertiesAxiom): Option[(Set[OWLAnnotation], Set[OWLDataPropertyExpression])] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getProperties.asScala.toSet)

  }

  object ObjectPropertyDomain {

    def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, domain: OWLClassExpression): OWLObjectPropertyDomainAxiom =
      factory.getOWLObjectPropertyDomainAxiom(property, domain, annotations.asJava)

    def apply(property: OWLObjectPropertyExpression, domain: OWLClassExpression): OWLObjectPropertyDomainAxiom =
      apply(Set.empty, property, domain)

    def unapply(axiom: OWLObjectPropertyDomainAxiom): Option[(Set[OWLAnnotation], OWLObjectPropertyExpression, OWLClassExpression)] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getProperty, axiom.getDomain)

  }

  object ObjectPropertyRange {

    def apply(annotations: Set[OWLAnnotation], property: OWLObjectPropertyExpression, domain: OWLClassExpression): OWLObjectPropertyRangeAxiom =
      factory.getOWLObjectPropertyRangeAxiom(property, domain, annotations.asJava)

    def apply(property: OWLObjectPropertyExpression, domain: OWLClassExpression): OWLObjectPropertyRangeAxiom =
      apply(Set.empty, property, domain)

    def unapply(axiom: OWLObjectPropertyRangeAxiom): Option[(Set[OWLAnnotation], OWLObjectPropertyExpression, OWLClassExpression)] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getProperty, axiom.getRange)

  }

  object DataPropertyDomain {

    def apply(annotations: Set[OWLAnnotation], property: OWLDataPropertyExpression, domain: OWLClassExpression): OWLDataPropertyDomainAxiom =
      factory.getOWLDataPropertyDomainAxiom(property, domain, annotations.asJava)

    def apply(property: OWLDataPropertyExpression, domain: OWLClassExpression): OWLDataPropertyDomainAxiom =
      apply(Set.empty, property, domain)

    def unapply(axiom: OWLDataPropertyDomainAxiom): Option[(Set[OWLAnnotation], OWLDataPropertyExpression, OWLClassExpression)] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getProperty, axiom.getDomain)

  }

  object DataPropertyRange {

    def apply(annotations: Set[OWLAnnotation], property: OWLDataPropertyExpression, range: OWLDataRange): OWLDataPropertyRangeAxiom =
      factory.getOWLDataPropertyRangeAxiom(property, range, annotations.asJava)

    def apply(property: OWLDataPropertyExpression, range: OWLDataRange): OWLDataPropertyRangeAxiom =
      apply(Set.empty, property, range)

    def unapply(axiom: OWLDataPropertyRangeAxiom): Option[(Set[OWLAnnotation], OWLDataPropertyExpression, OWLDataRange)] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getProperty, axiom.getRange)

  }

  object InverseObjectProperties {

    def apply(annotations: Set[OWLAnnotation], property1: OWLObjectPropertyExpression, property2: OWLObjectPropertyExpression): OWLInverseObjectPropertiesAxiom =
      factory.getOWLInverseObjectPropertiesAxiom(property1, property2, annotations.asJava)

    def apply(property1: OWLObjectPropertyExpression, property2: OWLObjectPropertyExpression): OWLInverseObjectPropertiesAxiom =
      apply(Set.empty, property1, property2)

    def unapply(axiom: OWLInverseObjectPropertiesAxiom): Option[(Set[OWLAnnotation], OWLObjectPropertyExpression, OWLObjectPropertyExpression)] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getFirstProperty, axiom.getSecondProperty)

  }

  object DisjointObjectProperties {

    def apply(annotations: Set[OWLAnnotation], properties: Set[OWLObjectPropertyExpression]): OWLDisjointObjectPropertiesAxiom =
      factory.getOWLDisjointObjectPropertiesAxiom(properties.asJava, annotations.asJava)

    def apply(properties: OWLObjectPropertyExpression*): OWLDisjointObjectPropertiesAxiom =
      apply(Set.empty[OWLAnnotation], properties.toSet)

    def apply(properties: Set[OWLObjectPropertyExpression]): OWLDisjointObjectPropertiesAxiom =
      apply(Set.empty[OWLAnnotation], properties)

    def apply(annotations: OWLAnnotation*)(properties: OWLObjectPropertyExpression*): OWLDisjointObjectPropertiesAxiom =
      apply(annotations.toSet, properties.toSet)

    def unapply(axiom: OWLDisjointObjectPropertiesAxiom): Option[(Set[OWLAnnotation], Set[OWLObjectPropertyExpression])] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getProperties.asScala.toSet)

  }

  object DisjointDataProperties {

    def apply(annotations: Set[OWLAnnotation], properties: Set[OWLDataPropertyExpression]): OWLDisjointDataPropertiesAxiom =
      factory.getOWLDisjointDataPropertiesAxiom(properties.asJava, annotations.asJava)

    def apply(properties: OWLDataPropertyExpression*): OWLDisjointDataPropertiesAxiom =
      apply(Set.empty[OWLAnnotation], properties.toSet)

    def apply(properties: Set[OWLDataPropertyExpression]): OWLDisjointDataPropertiesAxiom =
      apply(Set.empty[OWLAnnotation], properties)

    def apply(annotations: OWLAnnotation*)(properties: OWLDataPropertyExpression*): OWLDisjointDataPropertiesAxiom =
      apply(annotations.toSet, properties.toSet)

    def unapply(axiom: OWLDisjointDataPropertiesAxiom): Option[(Set[OWLAnnotation], Set[OWLDataPropertyExpression])] =
      Option(axiom.getAnnotations.asScala.toSet, axiom.getProperties.asScala.toSet)

  }

  object SymmetricObjectProperty extends UnaryObjectPropertyAxiom[OWLSymmetricObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = (pe: OWLObjectPropertyExpression, annotations: Set[OWLAnnotation]) =>
      factory.getOWLSymmetricObjectPropertyAxiom(pe, annotations.asJava)

  }

  object AsymmetricObjectProperty extends UnaryObjectPropertyAxiom[OWLAsymmetricObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = (pe: OWLObjectPropertyExpression, annotations: Set[OWLAnnotation]) =>
      factory.getOWLAsymmetricObjectPropertyAxiom(pe, annotations.asJava)

  }

  object ReflexiveObjectProperty extends UnaryObjectPropertyAxiom[OWLReflexiveObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = (pe: OWLObjectPropertyExpression, annotations: Set[OWLAnnotation]) =>
      factory.getOWLReflexiveObjectPropertyAxiom(pe, annotations.asJava)

  }

  object IrreflexiveObjectProperty extends UnaryObjectPropertyAxiom[OWLIrreflexiveObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = (pe: OWLObjectPropertyExpression, annotations: Set[OWLAnnotation]) =>
      factory.getOWLIrreflexiveObjectPropertyAxiom(pe, annotations.asJava)

  }

  object FunctionalObjectProperty extends UnaryObjectPropertyAxiom[OWLFunctionalObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = (pe: OWLObjectPropertyExpression, annotations: Set[OWLAnnotation]) =>
      factory.getOWLFunctionalObjectPropertyAxiom(pe, annotations.asJava)

  }

  object FunctionalDataProperty extends UnaryObjectPropertyAxiom[OWLFunctionalDataPropertyAxiom, OWLDataPropertyExpression] {

    val constructor = (pe: OWLDataPropertyExpression, annotations: Set[OWLAnnotation]) =>
      factory.getOWLFunctionalDataPropertyAxiom(pe, annotations.asJava)

  }

  object InverseFunctionalObjectProperty extends UnaryObjectPropertyAxiom[OWLInverseFunctionalObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = (pe: OWLObjectPropertyExpression, annotations: Set[OWLAnnotation]) =>
      factory.getOWLInverseFunctionalObjectPropertyAxiom(pe, annotations.asJava)

  }

  object TransitiveObjectProperty extends UnaryObjectPropertyAxiom[OWLTransitiveObjectPropertyAxiom, OWLObjectPropertyExpression] {

    val constructor = (pe: OWLObjectPropertyExpression, annotations: Set[OWLAnnotation]) =>
      factory.getOWLTransitiveObjectPropertyAxiom(pe, annotations.asJava)

  }

}

trait UnaryObjectPropertyAxiom[T <: OWLUnaryPropertyAxiom[P], P <: OWLPropertyExpression] {

  def constructor: (P, Set[OWLAnnotation]) => T

  def apply(annotations: Set[OWLAnnotation], property: P): T = constructor(property, annotations)

  def apply(property: P): T = apply(Set.empty, property)

  def unapply(axiom: T): Option[(Set[OWLAnnotation], P)] =
    Option(axiom.getAnnotations.asScala.toSet, axiom.getProperty)
}

