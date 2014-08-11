# Scowl

[![Build Status](https://secure.travis-ci.org/phenoscape/scowl.png)](http://travis-ci.org/phenoscape/scowl)

Scowl is a Scala library allowing a declarative approach to composing OWL expressions and axioms using the [OWL API](http://owlapi.sourceforge.net).

## Usage

Build the jar using `mvn package`, and add to your Scala project. Import `org.phenoscape.scowl.OWL._`, and Scowl implicit conversions will add pseudo Manchester syntax methods to native OWL API objects.

## Examples
```scala
val manager = OWLManager.createOWLOntologyManager()
val ontology = manager.createOntology()
val partOf = ObjectProperty("http://example.org/part_of")
val hasPart = ObjectProperty("http://example.org/has_part")
val developsFrom = ObjectProperty("http://example.org/develops_from")
val eye = Class("http://example.org/eye")
val head = Class("http://example.org/head")
val tail = Class("http://example.org/tail")
manager.addAxiom(ontology, eye SubClassOf (partOf some head))
manager.addAxiom(ontology, eye SubClassOf (not (partOf some tail)))
ontology.getClassesInSignature(true).map (c => 
  (not (hasPart some c)) SubClassOf (not (hasPart some (developsFrom some c)))
  ) foreach (manager.addAxiom(ontology, _))
```

## License

Scowl is open source under the [MIT License](http://opensource.org/licenses/MIT).  See [LICENSE](LICENSE) for more information.
