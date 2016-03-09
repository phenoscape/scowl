package org.phenoscape.scowl.converters

import org.semanticweb.owlapi.model.OWLAnnotationSubject
import org.semanticweb.owlapi.model.OWLNamedObject

trait AnnotationSubjects {

  implicit object NamedObjectAnnotatable extends Annotatable[OWLNamedObject] {

    def toAnnotationSubject(value: OWLNamedObject): OWLAnnotationSubject = value.getIRI

  }

  implicit object AnnotationSubjectAnnotatable extends Annotatable[OWLAnnotationSubject] {

    def toAnnotationSubject(value: OWLAnnotationSubject): OWLAnnotationSubject = value

  }

}

trait Annotatable[-T] {

  def toAnnotationSubject(value: T): OWLAnnotationSubject

}