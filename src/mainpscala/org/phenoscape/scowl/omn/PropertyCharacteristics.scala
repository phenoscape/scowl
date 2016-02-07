package org.phenoscape.scowl.omn

import org.phenoscape.scowl.factory
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLPropertyAxiom
import org.semanticweb.owlapi.model.OWLPropertyExpression
import org.semanticweb.owlapi.model.OWLPropertyRange
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLProperty
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom
import org.semanticweb.owlapi.model.OWLDataPropertyCharacteristicAxiom
import org.semanticweb.owlapi.model.OWLDataPropertyExpression

//sealed trait PropertyCharacteristic[R <: OWLPropertyRange, P <: OWLPropertyExpression[R, P], T <: OWLObjectPropertyCharacteristicAxiom, U <: OWLDataPropertyCharacteristicAxiom] {
//
//  def axiom(property: P): T
//
//}

sealed trait PropertyCharacteristic[T <: OWLObjectPropertyCharacteristicAxiom, U <: OWLDataPropertyCharacteristicAxiom] {

  def axiom(property: OWLObjectPropertyExpression): T

  def axiom(property: OWLDataPropertyExpression): U

}

sealed trait ObjectPropertyOnlyCharacteristic[T <: OWLObjectPropertyCharacteristicAxiom] extends PropertyCharacteristic[T, Nothing] {

  def axiom(property: OWLObjectPropertyExpression): T

  def axiom(property: OWLDataPropertyExpression): Nothing = sys.error("Characteristic not valid for data properties.")

}

trait PropertyCharacteristics {

  object Symmetric extends ObjectPropertyOnlyCharacteristic[OWLSymmetricObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLSymmetricObjectPropertyAxiom = factory.getOWLSymmetricObjectPropertyAxiom(property)

  }

  object Asymmetric extends ObjectPropertyOnlyCharacteristic[OWLAsymmetricObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLAsymmetricObjectPropertyAxiom = factory.getOWLAsymmetricObjectPropertyAxiom(property)

  }

  object Reflexive extends ObjectPropertyOnlyCharacteristic[OWLReflexiveObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLReflexiveObjectPropertyAxiom = factory.getOWLReflexiveObjectPropertyAxiom(property)

  }

  object Irreflexive extends ObjectPropertyOnlyCharacteristic[OWLIrreflexiveObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLIrreflexiveObjectPropertyAxiom = factory.getOWLIrreflexiveObjectPropertyAxiom(property)

  }

  object Functional extends PropertyCharacteristic[OWLFunctionalObjectPropertyAxiom, OWLFunctionalDataPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLFunctionalObjectPropertyAxiom = factory.getOWLFunctionalObjectPropertyAxiom(property)

    def axiom(property: OWLDataPropertyExpression): OWLFunctionalDataPropertyAxiom = factory.getOWLFunctionalDataPropertyAxiom(property)

  }

  object InverseFunctional extends ObjectPropertyOnlyCharacteristic[OWLInverseFunctionalObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLInverseFunctionalObjectPropertyAxiom = factory.getOWLInverseFunctionalObjectPropertyAxiom(property)

  }

  object Transitive extends ObjectPropertyOnlyCharacteristic[OWLTransitiveObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLTransitiveObjectPropertyAxiom = factory.getOWLTransitiveObjectPropertyAxiom(property)

  }

}