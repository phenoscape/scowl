package org.phenoscape.scowl.example

import org.phenoscape.scowl._

object OWL2PrimerFunctional {

  val ns = "http://www.w3.org/TR/owl2-primer"

  /**
   * 4.1 Classes and Instances
   */
  val Mary = Individual(s"$ns#Mary")
  val Person = Class(s"$ns#Person")

  ClassAssertion(Person, Mary)

  val Woman = Class(s"$ns#Woman")

  ClassAssertion(Woman, Mary)

  /**
   * 4.2 Class Hierarchies
   */
  SubClassOf(Woman, Person)

  val Mother = Class(s"$ns#Mother")

  SubClassOf(Mother, Woman)

  val Human = Class(s"$ns#Human")

  EquivalentClasses(Person, Human)

  /**
   * 4.3 Class Disjointness
   */
  val Man = Class(s"$ns#Man")

  DisjointClasses(Woman, Man)

  /**
   * 4.4 Object Properties
   */
  val hasWife = ObjectProperty(s"$ns#hasWife")
  val John = Individual(s"$ns#John")

  ObjectPropertyAssertion(hasWife, John, Mary)

  val Bill = Individual(s"$ns#Bill")

  NegativeObjectPropertyAssertion(hasWife, Bill, Mary)

  /**
   * 4.5 Property Hierarchies
   */
  val hasSpouse = ObjectProperty(s"$ns#hasSpouse")

  SubObjectPropertyOf(hasWife, hasSpouse)

  /**
   * 4.6 Domain and Range Restrictions
   */
  ObjectPropertyDomain(hasWife, Man)

  ObjectPropertyRange(hasWife, Woman)

  /**
   * 4.7 Equality and Inequality of Individuals
   */
  DifferentIndividuals(John, Bill)

  val James = Individual(s"$ns#James")
  val Jim = Individual(s"$ns#Jim")

  SameIndividual(James, Jim)

  /**
   * 4.8 Datatypes
   */
  val hasAge = DataProperty(s"$ns#hasAge")

  DataPropertyAssertion(hasAge, John, 51)

  NegativeDataPropertyAssertion(hasAge, John, "53" ^^ XSDInteger)

  DataPropertyDomain(hasAge, Person)

  DataPropertyRange(hasAge, XSDNonNegativeInteger)

  /**
   * 5.1 Complex Classes
   */
  val Parent = Class(s"$ns#Parent")

  EquivalentClasses(
    Mother,
    ObjectIntersectionOf(Woman, Parent))

  val Father = Class(s"$ns#Father")

  Parent EquivalentTo (Mother or Father)

  EquivalentClasses(
    Parent,
    ObjectUnionOf(Mother, Father))

  val ChildlessPerson = Class(s"$ns#ChildlessPerson")

  EquivalentClasses(
    ChildlessPerson,
    ObjectIntersectionOf(
      Person,
      ObjectComplementOf(Parent)))

  val Grandfather = Class(s"$ns#Grandfather")

  SubClassOf(
    Grandfather,
    ObjectIntersectionOf(Man, Parent))

  val Jack = Individual(s"$ns#Jack")

  ClassAssertion(
    ObjectIntersectionOf(
      Person,
      ObjectComplementOf(Parent)),
    Jack)

  /**
   * 5.2 Property Restrictions
   */
  val hasChild = ObjectProperty(s"$ns#hasChild")

  EquivalentClasses(
    Parent,
    ObjectSomeValuesFrom(hasChild, Person))

  val HappyPerson = Class(s"$ns#HappyPerson")

  EquivalentClasses(
    HappyPerson,
    ObjectAllValuesFrom(hasChild, HappyPerson))

  EquivalentClasses(
    HappyPerson,
    ObjectIntersectionOf(
      ObjectAllValuesFrom(hasChild, HappyPerson),
      ObjectSomeValuesFrom(hasChild, Person)))

  val hasParent = ObjectProperty(s"$ns#hasParent")
  val JohnsChildren = Class(s"$ns#JohnsChildren")

  EquivalentClasses(
    JohnsChildren,
    ObjectHasValue(hasParent, John))

  val loves = ObjectProperty(s"$ns#loves")
  val NarcissisticPerson = Class(s"$ns#NarcissisticPerson")

  EquivalentClasses(
    NarcissisticPerson,
    ObjectHasSelf(loves))

  /**
   * 5.3 Property Cardinality Restrictions
   */

  ClassAssertion(
    ObjectMaxCardinality(4, hasChild, Parent),
    John)

  ClassAssertion(
    ObjectMinCardinality(2, hasChild, Parent),
    John)

  ClassAssertion(
    ObjectExactCardinality(3, hasChild, Parent),
    John)

  ClassAssertion(
    ObjectExactCardinality(5, hasChild),
    John)

  /**
   * 5.4 Enumeration of Individuals
   */
  val MyBirthdayGuests = Class(s"$ns#MyBirthdayGuests")

  EquivalentClasses(
    MyBirthdayGuests,
    ObjectOneOf(Bill, John, Mary))

  /**
   * 6.1 Property Characteristics
   */
  InverseObjectProperties(hasParent, hasChild)

  val Orphan = Class(s"$ns#Orphan")
  val Dead = Class(s"$ns#Dead")

  EquivalentClasses(
    Orphan,
    ObjectAllValuesFrom(
      ObjectInverseOf(hasChild),
      Dead))

  SymmetricObjectProperty(hasSpouse)

  AsymmetricObjectProperty(hasChild)

  hasParent DisjointWith hasSpouse

  val hasRelative = ObjectProperty(s"$ns#hasRelative")

  hasRelative Characteristic Reflexive

  val parentOf = ObjectProperty(s"$ns#parentOf")

  parentOf Characteristic Irreflexive

  val hasHusband = ObjectProperty(s"$ns#hasHusband")

  hasHusband Characteristic Functional

  hasHusband Characteristic InverseFunctional

  val hasAncestor = ObjectProperty(s"$ns#hasAncestor")

  hasAncestor Characteristic Transitive

  /**
   * 6.2 Property Chains
   */
  val hasGrandparent = ObjectProperty(s"$ns#hasGrandparent")

  hasGrandparent SubPropertyChain (hasParent o hasParent)

  /**
   * 6.3 Keys
   */

  val hasSSN = DataProperty(s"$ns#hasSSN")

  Person HasKey hasSSN

  /**
   * 7 Advanced Use of Datatypes
   */
  val PersonAge = Datatype(s"$ns#PersonAge")

  PersonAge EquivalentTo XSDInteger(>=(0), <=(150))

  val MajorAge = Datatype(s"$ns#MajorAge")
  val MinorAge = Datatype(s"$ns#MinorAge")

  MajorAge EquivalentTo (PersonAge and not(MinorAge))

  val ToddlerAge = Datatype(s"$ns#ToddlerAge")

  ToddlerAge EquivalentTo (("1" ^^ XSDInteger) ~ ("2" ^^ XSDInteger))

  hasAge Characteristic Functional

  val Teenager = Class(s"$ns#Teenager")

  Teenager SubClassOf (hasAge some XSDInteger(>(12), <=(19)))

  /**
   * 8.1 Annotating Axioms and Entities
   */

  Person Annotation (RDFSComment, "Represents the set of all people.")

  (Man SubClassOf Person) Annotation (RDFSComment, "States that every man is a person.")

}