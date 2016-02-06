package org.phenoscape.scowl.example

import org.phenoscape.scowl._

object OWL2Primer {

  val ns = "http://www.w3.org/TR/owl2-primer"

  /**
   * 4.1 Classes and Instances
   */
  val Mary = Individual(s"$ns#Mary")
  val Person = Class(s"$ns#Person")

  Mary Type Person
  ClassAssertion(Person, Mary)

  val Woman = Class(s"$ns#Woman")

  Mary Type Woman
  ClassAssertion(Woman, Mary)

  /**
   * 4.2 Class Hierarchies
   */
  Woman SubClassOf Person
  SubClassOf(Woman, Person)

  val Mother = Class(s"$ns#Mother")

  Mother SubClassOf Woman
  SubClassOf(Mother, Woman)

  val Human = Class(s"$ns#Human")

  Person EquivalentTo Human
  EquivalentClasses(Person, Human)

  /**
   * 4.3 Class Disjointness
   */
  val Man = Class(s"$ns#Man")

  Woman DisjointWith Man
  DisjointClasses(Woman, Man)

  /**
   * 4.4 Object Properties
   */
  val hasWife = ObjectProperty(s"$ns#hasWife")
  val John = Individual(s"$ns#John")

  ObjectPropertyAssertion(hasWife, John, Mary)
  John Fact (hasWife, Mary)

  val Bill = Individual(s"$ns#Bill")

  Bill Fact (not(hasWife, Mary))
  NegativeObjectPropertyAssertion(hasWife, Bill, Mary)

  /**
   * 4.5 Property Hierarchies
   */
  val hasSpouse = ObjectProperty(s"$ns#hasSpouse")

  hasWife SubPropertyOf hasSpouse
  SubObjectPropertyOf(hasWife, hasSpouse)

  /**
   * 4.6 Domain and Range Restrictions
   */
  hasWife Domain Man
  ObjectPropertyDomain(hasWife, Man)

  hasWife Range Woman
  ObjectPropertyRange(hasWife, Woman)

  /**
   * 4.7 Equality and Inequality of Individuals
   */
  John DifferentFrom Bill
  DifferentIndividuals(John, Bill)

  val James = Individual(s"$ns#James")
  val Jim = Individual(s"$ns#Jim")

  James SameAs Jim
  SameIndividual(James, Jim)

  /**
   * 4.8 Datatypes
   */
  val hasAge = DataProperty(s"$ns#hasAge")

  John Fact (hasAge, 51)
  DataPropertyAssertion(hasAge, John, "51" ^^ XSDInteger)

  John Fact (not(hasAge, "53" ^^ XSDInteger))
  NegativeDataPropertyAssertion(hasAge, John, "53" ^^ XSDInteger)

  hasAge Domain Person
  DataPropertyDomain(hasAge, Person)

  hasAge Range XSDNonNegativeInteger
  DataPropertyRange(hasAge, XSDNonNegativeInteger)

  /**
   * 5.1 Complex Classes
   */
  val Parent = Class(s"$ns#Parent")

  Mother EquivalentTo (Woman and Parent)

  EquivalentClasses(
    Mother,
    ObjectIntersectionOf(Woman, Parent))

  val Father = Class(s"$ns#Father")

  Parent EquivalentTo (Mother or Father)

  EquivalentClasses(
    Parent,
    ObjectUnionOf(Mother, Father))

  val ChildlessPerson = Class(s"$ns#ChildlessPerson")

  ChildlessPerson EquivalentTo (Person and not(Parent))

  EquivalentClasses(
    ChildlessPerson,
    ObjectIntersectionOf(
      Person,
      ObjectComplementOf(Parent)))

  val Grandfather = Class(s"$ns#Grandfather")

  Grandfather SubClassOf (Man and Parent)

  SubClassOf(
    Grandfather,
    ObjectIntersectionOf(Man, Parent))

  val Jack = Individual(s"$ns#Jack")

  Jack Type (Person and not(Parent))

  ClassAssertion(
    ObjectIntersectionOf(
      Person,
      ObjectComplementOf(Parent)),
    Jack)

  /**
   * 5.2 Property Restrictions
   */
  val hasChild = ObjectProperty(s"$ns#hasChild")

  Parent EquivalentTo (hasChild some Person)

  EquivalentClasses(
    Parent,
    ObjectSomeValuesFrom(hasChild, Person))

}