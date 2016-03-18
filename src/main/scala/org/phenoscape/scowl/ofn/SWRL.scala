package org.phenoscape.scowl.ofn

import scala.collection.JavaConversions._
import org.phenoscape.scowl.converters.SWRLArgs
import org.phenoscape.scowl.converters.SWRLDArgish
import org.phenoscape.scowl.converters.SWRLIArgish
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataRange
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.SWRLAtom
import org.semanticweb.owlapi.model.SWRLClassAtom
import org.semanticweb.owlapi.model.SWRLDArgument
import org.semanticweb.owlapi.model.SWRLDataRangeAtom
import org.semanticweb.owlapi.model.SWRLIArgument
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom
import org.semanticweb.owlapi.model.SWRLRule
import org.semanticweb.owlapi.model.SWRLVariable
import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom
import org.phenoscape.scowl.converters.SWRLDArgish
import org.semanticweb.owlapi.model.SWRLBuiltInAtom
import org.phenoscape.scowl.converters.SWRLIArgish
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom

trait SWRL {

  private val factory = OWLManager.getOWLDataFactory

  object DLSafeRule {

    def apply(annotations: Set[OWLAnnotation], body: Set[_ <: SWRLAtom], head: Set[_ <: SWRLAtom]): SWRLRule =
      factory.getSWRLRule(body, head, annotations)

    def apply(body: Set[_ <: SWRLAtom], head: Set[_ <: SWRLAtom]): SWRLRule =
      DLSafeRule(Set.empty, body, head)

    def apply(annotations: OWLAnnotation*)(body: Set[_ <: SWRLAtom], head: Set[_ <: SWRLAtom]): SWRLRule =
      DLSafeRule(annotations.toSet, body, head)

    def unapply(rule: SWRLRule): Option[(Set[OWLAnnotation], Set[SWRLAtom], Set[SWRLAtom])] =
      Option(rule.getAnnotations.toSet, rule.getBody.toSet, rule.getHead.toSet)

  }

  object ClassAtom {

    def apply[T: SWRLIArgish](classExpression: OWLClassExpression, arg: T): SWRLClassAtom = {
      val argish = implicitly[SWRLIArgish[T]]
      factory.getSWRLClassAtom(classExpression, argish.toArgument(arg))
    }

    def unapply(atom: SWRLClassAtom): Option[(OWLClassExpression, SWRLIArgument)] =
      Option(atom.getPredicate, atom.getArgument)

  }

  object DataRangeAtom {

    def apply[T: SWRLDArgish](dataRange: OWLDataRange, arg: T): SWRLDataRangeAtom = {
      val argish = implicitly[SWRLDArgish[T]]
      factory.getSWRLDataRangeAtom(dataRange, argish.toArgument(arg))
    }

    def unapply(atom: SWRLDataRangeAtom): Option[(OWLDataRange, SWRLDArgument)] =
      Option(atom.getPredicate, atom.getArgument)

  }

  object ObjectPropertyAtom {

    def apply[S: SWRLIArgish, O: SWRLIArgish](property: OWLObjectPropertyExpression, subj: S, obj: O): SWRLObjectPropertyAtom = {
      val sArgish = implicitly[SWRLIArgish[S]]
      val oArgish = implicitly[SWRLIArgish[O]]
      factory.getSWRLObjectPropertyAtom(property, sArgish.toArgument(subj), oArgish.toArgument(obj))
    }

    def unapply(atom: SWRLObjectPropertyAtom): Option[(OWLObjectPropertyExpression, SWRLIArgument, SWRLIArgument)] =
      Option(atom.getPredicate, atom.getFirstArgument, atom.getSecondArgument)

  }

  object DataPropertyAtom {

    def apply[S: SWRLIArgish, V: SWRLDArgish](property: OWLDataPropertyExpression, subj: S, value: V): SWRLDataPropertyAtom = {
      val sArgish = implicitly[SWRLIArgish[S]]
      val vArgish = implicitly[SWRLDArgish[V]]
      factory.getSWRLDataPropertyAtom(property, sArgish.toArgument(subj), vArgish.toArgument(value))
    }

    def unapply(atom: SWRLDataPropertyAtom): Option[(OWLDataPropertyExpression, SWRLIArgument, SWRLDArgument)] =
      Option(atom.getPredicate, atom.getFirstArgument, atom.getSecondArgument)

  }

  object BuiltInAtom {

    def apply[T: SWRLDArgish](iri: IRI, args: T*): SWRLBuiltInAtom = {
      val argish = implicitly[SWRLDArgish[T]]
      factory.getSWRLBuiltInAtom(iri, args.map(argish.toArgument))
    }

    def unapply(atom: SWRLBuiltInAtom): Option[(IRI, Seq[SWRLDArgument])] = {
      Option(atom.getPredicate, atom.getArguments)
    }

  }

  object SameIndividualAtom {

    def apply[S: SWRLIArgish, O: SWRLIArgish](subj: S, obj: O): SWRLSameIndividualAtom = {
      val sArgish = implicitly[SWRLIArgish[S]]
      val oArgish = implicitly[SWRLIArgish[O]]
      factory.getSWRLSameIndividualAtom(sArgish.toArgument(subj), oArgish.toArgument(obj))
    }

    def unapply(atom: SWRLSameIndividualAtom): Option[(SWRLIArgument, SWRLIArgument)] = {
      Option(atom.getFirstArgument, atom.getSecondArgument)
    }

  }

  object DifferentIndividualsAtom {

    def apply[S: SWRLIArgish, O: SWRLIArgish](subj: S, obj: O): SWRLDifferentIndividualsAtom = {
      val sArgish = implicitly[SWRLIArgish[S]]
      val oArgish = implicitly[SWRLIArgish[O]]
      factory.getSWRLDifferentIndividualsAtom(sArgish.toArgument(subj), oArgish.toArgument(obj))
    }

    def unapply(atom: SWRLDifferentIndividualsAtom): Option[(SWRLIArgument, SWRLIArgument)] = {
      Option(atom.getFirstArgument, atom.getSecondArgument)
    }

  }

  object Variable {

    private object argish extends SWRLArgs

    def apply(iri: IRI): SWRLVariable = factory.getSWRLVariable(iri)

    def apply(symbol: Symbol): SWRLVariable =
      argish.SymbolArgish.toArgument(symbol)

    def unapply(variable: SWRLVariable): Option[IRI] =
      Option(variable.getIRI)

  }

}