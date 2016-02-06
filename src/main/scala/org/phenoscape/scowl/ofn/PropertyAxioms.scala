package org.phenoscape.scowl.ofn

import scala.collection.JavaConversions._
import org.phenoscape.scowl.factory
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom
import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom
import org.semanticweb.owlapi.model.OWLDataRange
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom

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

}