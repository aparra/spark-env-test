package com.github.aparra.sparkenvtest

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{DiagrammedAssertions, MustMatchers, PropSpec}

abstract class BaseSpec
  extends PropSpec
  with GeneratorDrivenPropertyChecks
  with MustMatchers
  with TypeCheckedTripleEquals
  with DiagrammedAssertions