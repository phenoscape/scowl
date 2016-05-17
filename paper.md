---
title: 'Scowl: a Scala DSL for programming with the OWL API'
tags:
  - OWL
  - ontology
authors:
  - name: James P Balhoff
    orcid: 0000-0002-8688-6599
    affiliation: RTI International
date: 16 May 2016
bibliography: paper.bib
---

# Summary

Scowl is a domain-specific language (DSL) which provides a convenient Scala-based syntax for composing OWL (Web Ontology Language, [@OWL2009]) expressions and axioms. Effective use of complex, community developed ontologies within research applications, such as those from the OBO library [@Smith2007], often requires custom programmatic manipulation of ontologies and data, along with input to and output from automated reasoners. Allowing deeply nested OWL constructs to be coded in a readable, declarative style, Scowl facilitates use cases at the interface of application programming and OWL modeling: 1) tabular data ingest and transformation into OWL; 2) ontology manipulation via programmatic generation of axioms from existing content; and 3) reporting on ontology content using pattern matching on OWL objects.

Within Scala applications, Scowl adds extension methods to native Java OWL API objects [@OWLAPI], supporting two syntax styles: the pseudo-English Manchester syntax [@Horridge2009], as well as the OWL Functional syntax [@Motik2009]. Integration with the standard Java OWL API provides compatibility with multiple freely available OWL reasoners.

# References
