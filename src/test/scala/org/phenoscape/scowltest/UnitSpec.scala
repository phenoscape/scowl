package org.phenoscape.scowltest

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

abstract class UnitSpec extends AnyFlatSpec with Matchers with OptionValues with Inside with Inspectors
  