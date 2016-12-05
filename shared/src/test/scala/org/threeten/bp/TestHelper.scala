package org.threeten.bp

import org.scalactic.{Prettifier, source}
import org.scalatest.{Assertion, AssertionsMacro, Assertions}
import org.scalatest.FunSuite

trait TestHelper { this: FunSuite =>
  def assertEquals[A, B](o1: A, o2: B)(implicit prettifier: Prettifier, pos: source.Position): Assertion =
    assert(o1 == o2)

}
