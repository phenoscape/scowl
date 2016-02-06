package org.phenoscape.scowl.omn

import org.phenoscape.scowl.factory
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLPropertyExpression
import org.semanticweb.owlapi.model.OWLPropertyRange
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom

sealed trait PropertyCharacteristic[R <: OWLPropertyRange, P <: OWLPropertyExpression[R, P], T <: OWLObjectPropertyCharacteristicAxiom] {

  def axiom(property: P): T

}

trait PropertyCharacteristics {

  object Symmetric extends PropertyCharacteristic[OWLClassExpression, OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLSymmetricObjectPropertyAxiom = factory.getOWLSymmetricObjectPropertyAxiom(property)

  }

  object Asymmetric extends PropertyCharacteristic[OWLClassExpression, OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLAsymmetricObjectPropertyAxiom = factory.getOWLAsymmetricObjectPropertyAxiom(property)

  }

  object Reflexive extends PropertyCharacteristic[OWLClassExpression, OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLReflexiveObjectPropertyAxiom = factory.getOWLReflexiveObjectPropertyAxiom(property)

  }

  object Irreflexive extends PropertyCharacteristic[OWLClassExpression, OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLIrreflexiveObjectPropertyAxiom = factory.getOWLIrreflexiveObjectPropertyAxiom(property)

  }

  object Functional extends PropertyCharacteristic[OWLClassExpression, OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLFunctionalObjectPropertyAxiom = factory.getOWLFunctionalObjectPropertyAxiom(property)

  }

  object InverseFunctional extends PropertyCharacteristic[OWLClassExpression, OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLInverseFunctionalObjectPropertyAxiom = factory.getOWLInverseFunctionalObjectPropertyAxiom(property)

  }

  object Transitive extends PropertyCharacteristic[OWLClassExpression, OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom] {

    def axiom(property: OWLObjectPropertyExpression): OWLTransitiveObjectPropertyAxiom = factory.getOWLTransitiveObjectPropertyAxiom(property)

  }

}