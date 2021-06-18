package org.phenoscape.scowl.ofn

import org.phenoscape.scowl.converters.{SWRLArgs, SWRLDArgish, SWRLIArgish}
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model._

import scala.jdk.CollectionConverters._

trait SWRL {

  private val factory = OWLManager.getOWLDataFactory

  object DLSafeRule {

    def apply(annotations: Set[OWLAnnotation], body: Set[_ <: SWRLAtom], head: Set[_ <: SWRLAtom]): SWRLRule =
      factory.getSWRLRule(body.asJava, head.asJava, annotations.asJava)

    def apply(body: Set[_ <: SWRLAtom], head: Set[_ <: SWRLAtom]): SWRLRule =
      DLSafeRule(Set.empty, body, head)

    def apply(annotations: OWLAnnotation*)(body: Set[_ <: SWRLAtom], head: Set[_ <: SWRLAtom]): SWRLRule =
      DLSafeRule(annotations.toSet, body, head)

    def unapply(rule: SWRLRule): Option[(Set[OWLAnnotation], Set[SWRLAtom], Set[SWRLAtom])] =
      Option(rule.getAnnotations.asScala.toSet, rule.getBody.asScala.toSet, rule.getHead.asScala.toSet)

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
      factory.getSWRLBuiltInAtom(iri, args.map(argish.toArgument).asJava)
    }

    def unapply(atom: SWRLBuiltInAtom): Option[(IRI, Seq[SWRLDArgument])] = {
      Option((atom.getPredicate, atom.getArguments.asScala.toSeq))
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