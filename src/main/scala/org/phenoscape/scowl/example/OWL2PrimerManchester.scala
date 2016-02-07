package org.phenoscape.scowl.example

import org.phenoscape.scowl._
import scala.language.postfixOps

object OWL2PrimerManchester {

  val ns = "http://www.w3.org/TR/owl2-primer"

  /**
   * 4.1 Classes and Instances
   */
  val Mary = Individual(s"$ns#Mary")
  val Person = Class(s"$ns#Person")

  Mary Type Person

  val Woman = Class(s"$ns#Woman")

  Mary Type Woman

  /**
   * 4.2 Class Hierarchies
   */
  Woman SubClassOf Person

  val Mother = Class(s"$ns#Mother")

  Mother SubClassOf Woman

  val Human = Class(s"$ns#Human")

  Person EquivalentTo Human

  /**
   * 4.3 Class Disjointness
   */
  val Man = Class(s"$ns#Man")

  Woman DisjointWith Man

  /**
   * 4.4 Object Properties
   */
  val hasWife = ObjectProperty(s"$ns#hasWife")
  val John = Individual(s"$ns#John")

  John Fact (hasWife, Mary)

  val Bill = Individual(s"$ns#Bill")

  Bill Fact (not(hasWife, Mary))

  /**
   * 4.5 Property Hierarchies
   */
  val hasSpouse = ObjectProperty(s"$ns#hasSpouse")

  hasWife SubPropertyOf hasSpouse

  /**
   * 4.6 Domain and Range Restrictions
   */
  hasWife Domain Man

  hasWife Range Woman

  /**
   * 4.7 Equality and Inequality of Individuals
   */
  John DifferentFrom Bill

  val James = Individual(s"$ns#James")
  val Jim = Individual(s"$ns#Jim")

  James SameAs Jim

  /**
   * 4.8 Datatypes
   */
  val hasAge = DataProperty(s"$ns#hasAge")

  John Fact (hasAge, 51)

  John Fact (not(hasAge, "53" ^^ XSDInteger))

  hasAge Domain Person

  hasAge Range XSDNonNegativeInteger

  /**
   * 5.1 Complex Classes
   */
  val Parent = Class(s"$ns#Parent")

  Mother EquivalentTo (Woman and Parent)

  val Father = Class(s"$ns#Father")

  Parent EquivalentTo (Mother or Father)

  val ChildlessPerson = Class(s"$ns#ChildlessPerson")

  ChildlessPerson EquivalentTo (Person and not(Parent))

  val Grandfather = Class(s"$ns#Grandfather")

  Grandfather SubClassOf (Man and Parent)

  val Jack = Individual(s"$ns#Jack")

  Jack Type (Person and not(Parent))

  /**
   * 5.2 Property Restrictions
   */
  val hasChild = ObjectProperty(s"$ns#hasChild")

  Parent EquivalentTo (hasChild some Person)

  val HappyPerson = Class(s"$ns#HappyPerson")

  HappyPerson EquivalentTo (hasChild only HappyPerson)

  HappyPerson EquivalentTo ((hasChild only HappyPerson) and (hasChild some HappyPerson))

  val hasParent = ObjectProperty(s"$ns#hasParent")
  val JohnsChildren = Class(s"$ns#JohnsChildren")

  JohnsChildren EquivalentTo (hasParent value John)

  val loves = ObjectProperty(s"$ns#loves")
  val NarcissisticPerson = Class(s"$ns#NarcissisticPerson")

  NarcissisticPerson EquivalentTo (loves Self)

  /**
   * 5.3 Property Cardinality Restrictions
   */
  John Type (hasChild max (4, Parent))

  John Type (hasChild min (2, Parent))

  John Type (hasChild exactly (3, Parent))

  John Type (hasChild exactly 5)

  /**
   * 5.4 Enumeration of Individuals
   */
  val MyBirthdayGuests = Class(s"$ns#MyBirthdayGuests")

  MyBirthdayGuests EquivalentTo (Bill ~ John ~ Mary)

  /**
   * 6.1 Property Characteristics
   */
  hasParent InverseOf hasChild

  val Orphan = Class(s"$ns#Orphan")
  val Dead = Class(s"$ns#Dead")

  Orphan EquivalentTo (inverse(hasChild) only Dead)

  hasSpouse Characteristic Symmetric

  hasChild Characteristic Asymmetric

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