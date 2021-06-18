package org.phenoscape.scowl.omn

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model._

sealed trait PropertyCharacteristic[T <: OWLObjectPropertyCharacteristicAxiom, U <: OWLDataPropertyCharacteristicAxiom] {

  def axiom(property: OWLObjectPropertyExpression): T

  def axiom(property: OWLDataPropertyExpression): U

}

sealed trait ObjectPropertyOnlyCharacteristic[T <: OWLObjectPropertyCharacteristicAxiom] extends PropertyCharacteristic[T, Nothing] {

  def axiom(property: OWLObjectPropertyExpression): T

  def axiom(property: OWLDataPropertyExpression): Nothing = sys.error("Characteristic not valid for data properties.")

}

trait PropertyCharacteristics {

  private val factory = OWLManager.getOWLDataFactory

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