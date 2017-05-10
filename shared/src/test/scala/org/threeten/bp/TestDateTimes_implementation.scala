/*
 * Copyright (c) 2007-present, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.threeten.bp

import org.scalatest.FunSuite

import java.util.Collections

import java.lang.{Long => JLong}

/** Test. */
class TestDateTimes_implementation extends FunSuite with AssertionsHelper {

  val safeAddIntProvider: List[(Int, Int, Int)] =
    List(
      (Integer.MIN_VALUE, 1, Integer.MIN_VALUE + 1),
      (-1, 1, 0),
      (0, 0, 0),
      (1, -1, 0),
      (Integer.MAX_VALUE, -1, Integer.MAX_VALUE - 1))

  test("test_safeAddInt") {
    safeAddIntProvider.foreach {
      case (a, b, expected) =>
        assertEquals(Math.addExact(a, b), expected)
    }
  }

  val safeAddIntProviderOverflow: List[(Int, Int)] =
    List(
      (Integer.MIN_VALUE, -1),
      (Integer.MIN_VALUE + 1, -2),
      (Integer.MAX_VALUE - 1, 2),
      (Integer.MAX_VALUE, 1))

  test("test_safeAddInt_overflow") {
    safeAddIntProviderOverflow.foreach {
      case (a, b) =>
        assertThrows[ArithmeticException] {
          Math.addExact(a, b)
        }
    }
  }

  val safeAddLongProvider: List[(Long, Long, Long)] =
    List(
      (Long.MinValue, 1, Long.MinValue + 1),
      (-1, 1, 0),
      (0, 0, 0),
      (1, -1, 0),
      (Long.MaxValue, -1, Long.MaxValue - 1))

  test("test_safeAddLong") {
    safeAddLongProvider.foreach {
      case (a, b, expected) =>
        assertEquals(Math.addExact(a, b), expected)
    }
  }

  val safeAddLongProviderOverflow: List[(Long, Long)] =
    List(
      (Long.MinValue, -1),
      (Long.MinValue + 1, -2),
      (Long.MaxValue - 1, 2),
      (Long.MaxValue, 1))

  test("test_safeAddLong_overflow") {
    safeAddLongProviderOverflow.foreach {
      case (a, b) =>
        assertThrows[ArithmeticException] {
          Math.addExact(a, b)
        }
    }
  }

  val safeSubtractIntProvider: List[(Int, Int, Int)] =
    List(
      (Integer.MIN_VALUE, -1, Integer.MIN_VALUE + 1),
      (-1, -1, 0),
      (0, 0, 0),
      (1, 1, 0),
      (Integer.MAX_VALUE, 1, Integer.MAX_VALUE - 1))

  test("test_safeSubtractInt") {
    safeSubtractIntProvider.foreach {
      case (a, b, expected) =>
        assertEquals(Math.subtractExact(a, b), expected)
    }
  }

  val safeSubtractIntProviderOverflow: List[(Int, Int)] =
    List(
      (Integer.MIN_VALUE, 1),
      (Integer.MIN_VALUE + 1, 2),
      (Integer.MAX_VALUE - 1, -2),
      (Integer.MAX_VALUE, -1))

  test("test_safeSubtractInt_overflow") {
    safeSubtractIntProviderOverflow.foreach {
      case (a, b) =>
        assertThrows[ArithmeticException] {
          Math.subtractExact(a, b)
        }
    }
  }

  val safeSubtractLongProvider: List[(Long, Long, Long)] =
    List(
      (Long.MinValue, -1, Long.MinValue + 1),
      (-1, -1, 0),
      (0, 0, 0),
      (1, 1, 0),
      (Long.MaxValue, 1, Long.MaxValue - 1))

  test("test_safeSubtractLong") {
    safeSubtractLongProvider.foreach {
      case (a, b, expected) =>
        assertEquals(Math.subtractExact(a, b), expected)
    }
  }

  val safeSubtractLongProviderOverflow: List[(Long, Long)] =
    List(
      (Long.MinValue, 1),
      (Long.MinValue + 1, 2),
      (Long.MaxValue - 1, -2),
      (Long.MaxValue, -1))

  test("test_safeSubtractLong_overflow") {
    safeSubtractLongProviderOverflow.foreach {
      case (a, b) =>
        assertThrows[ArithmeticException] {
          Math.subtractExact(a, b)
        }
    }
  }

  val safeMultiplyIntProvider: List[(Int, Int, Int)] =
    List(
      (Integer.MIN_VALUE, 1, Integer.MIN_VALUE),
      (Integer.MIN_VALUE / 2, 2, Integer.MIN_VALUE),
      (-1, -1, 1),
      (-1, 1, -1),
      (0, -1, 0),
      (0, 0, 0),
      (0, 1, 0),
      (1, -1, -1),
      (1, 1, 1),
      (Integer.MAX_VALUE / 2, 2, Integer.MAX_VALUE - 1),
      (Integer.MAX_VALUE, -1, Integer.MIN_VALUE + 1))

  test("test_safeMultiplyInt") {
    safeMultiplyIntProvider.foreach {
      case (a, b, expected) =>
        assertEquals(Math.multiplyExact(a, b), expected)
    }
  }

val safeMultiplyIntProviderOverflow: List[(Int, Int)] =
    List(
      (Integer.MIN_VALUE, 2),
      (Integer.MIN_VALUE / 2 - 1, 2),
      (Integer.MAX_VALUE, 2),
      (Integer.MAX_VALUE / 2 + 1, 2),
      (Integer.MIN_VALUE, -1),
      (-1, Integer.MIN_VALUE))

  test("test_safeMultiplyInt_overflow") {
    safeMultiplyLongProviderOverflow.foreach {
      case (a, b) =>
        assertThrows[ArithmeticException] {
          Math.multiplyExact(a, b)
        }
    }
  }

  val safeMultiplyLongProvider: List[(Long, Int, Long)] =
    List(
      (Long.MinValue: JLong, 1: Integer, Long.MinValue: JLong),
      ((Long.MinValue / 2): JLong, 2: Integer, Long.MinValue: JLong),
      (-1: JLong, -1: Integer, 1: JLong),
      (-1: JLong, 1: Integer, -1: JLong),
      (0: JLong, -1: Integer, 0: JLong),
      (0: JLong, 0: Integer, 0: JLong),
      (0: JLong, 1: Integer, 0: JLong),
      (1: JLong, -1: Integer, -1: JLong),
      (1: JLong, 1: Integer, 1: JLong),
      ((Long.MaxValue / 2): JLong, 2: Integer, (Long.MaxValue - 1): JLong),
      (Long.MaxValue: JLong, -1: Integer, (Long.MinValue + 1): JLong),
      (-1: JLong, Integer.MIN_VALUE: Integer, -Integer.MIN_VALUE.toLong: JLong))

  test("test_safeMultiplyLong") {
    safeMultiplyLongProvider.foreach {
      case (a, b, expected) =>
        assertEquals(Math.multiplyExact(a, b), expected)
    }
  }

  val safeMultiplyLongProviderOverflow: List[(Long, Int)] =
    List(
      (Long.MinValue: JLong, 2: Integer),
      ((Long.MinValue / 2 - 1): JLong, 2: Integer),
      (Long.MaxValue: JLong, 2: Integer),
      ((Long.MaxValue / 2 + 1): JLong, 2: Integer),
      (Long.MinValue: JLong, -1: Integer))

  test("test_safeMultiplyLong_overflow") {
    safeMultiplyLongProviderOverflow.foreach {
      case (a, b) =>
        assertThrows[ArithmeticException] {
          Math.multiplyExact(a, b)
        }
    }
  }

  val safeMultiplyLongLongProvider: List[(Long, Long, Long)] =
    List(
      (Long.MinValue, 1, Long.MinValue),
      (Long.MinValue / 2, 2, Long.MinValue),
      (-1, -1, 1),
      (-1, 1, -1),
      (0, -1, 0),
      (0, 0, 0),
      (0, 1, 0),
      (1, -1, -1),
      (1, 1, 1),
      (Long.MaxValue / 2, 2, Long.MaxValue - 1),
      (Long.MaxValue, -1, Long.MinValue + 1))

  test("test_safeMultiplyLongLong") {
    safeMultiplyLongLongProvider.foreach {
      case (a, b, expected) =>
        assertEquals(Math.multiplyExact(a, b), expected)
    }
  }

  val safeMultiplyLongLongProviderOverflow: List[(Long, Long)] =
    List(
      (Long.MinValue, 2),
      (Long.MinValue / 2 - 1, 2),
      (Long.MaxValue, 2),
      (Long.MaxValue / 2 + 1, 2),
      (Long.MinValue, -1),
      (-1, Long.MinValue))

  test("test_safeMultiplyLongLong_overflow") {
    safeMultiplyLongLongProviderOverflow.foreach {
      case (a, b) =>
        assertThrows[ArithmeticException] {
          Math.multiplyExact(a, b)
        }
    }
  }

  val safeToIntProvider: List[Long] =
    List(
      (Integer.MIN_VALUE),
      (Integer.MIN_VALUE + 1),
      (-1),
      (0),
      (1),
      (Integer.MAX_VALUE - 1),
      (Integer.MAX_VALUE))

  test("test_safeToInt") {
    safeToIntProvider.foreach { l =>
      assertEquals(Math.toIntExact(l), l)
    }
  }

  val safeToIntProviderOverflow: List[Long] =
    List(
      (Long.MinValue),
      (Integer.MIN_VALUE - 1L),
      (Integer.MAX_VALUE + 1L),
      (Long.MaxValue))

  test("test_safeToInt_overflow") {
    safeToIntProviderOverflow.foreach {
      case l =>
        assertThrows[ArithmeticException] {
          Math.toIntExact(l)
        }
    }
  }

  test("test_safeCompare_int") {
    doTest_safeCompare_int(Integer.MIN_VALUE, Integer.MIN_VALUE + 1, Integer.MIN_VALUE + 2, -2, -1, 0, 1, 2, Integer.MAX_VALUE - 2, Integer.MAX_VALUE - 1, Integer.MAX_VALUE)
  }

  private def doTest_safeCompare_int(values: Int*): Unit = {
    var i: Int = 0
    while (i < values.length) {
      val a: Int = values(i)
      var j: Int = 0
      while (j < values.length) {
        val b: Int = values(j)
        assertEquals(Integer.compare(a, b), if (a < b) -1 else if (a > b) 1 else 0, a + " <=> " + b)
        j += 1
      }
      i += 1
    }
  }

  test("test_safeCompare_long") {
    doTest_safeCompare_long(Long.MinValue, Long.MinValue + 1, Long.MinValue + 2, Integer.MIN_VALUE, Integer.MIN_VALUE + 1, Integer.MIN_VALUE + 2, -2, -1, 0, 1, 2, Integer.MAX_VALUE - 2, Integer.MAX_VALUE - 1, Integer.MAX_VALUE, Long.MaxValue - 2, Long.MaxValue - 1, Long.MaxValue)
  }

  private def doTest_safeCompare_long(values: Long*): Unit = {
    var i: Int = 0
    while (i < values.length) {
      val a: Long = values(i)
      var j: Int = 0
      while (j < values.length) {
        val b: Long = values(j)
        assertEquals(java.lang.Long.compare(a, b), if (a < b) -1 else if (a > b) 1 else 0, a + " <=> " + b)
        j += 1
      }
      i += 1
    }
  }

  val data_floorDiv: List[(Long, Int, Long)] =
    List(
      (5L: JLong, 4: Integer, 1L: JLong),
      (4L: JLong, 4: Integer, 1L: JLong),
      (3L: JLong, 4: Integer, 0L: JLong),
      (2L: JLong, 4: Integer, 0L: JLong),
      (1L: JLong, 4: Integer, 0L: JLong),
      (0L: JLong, 4: Integer, 0L: JLong),
      (-1L: JLong, 4: Integer, -1L: JLong),
      (-2L: JLong, 4: Integer, -1L: JLong),
      (-3L: JLong, 4: Integer, -1L: JLong),
      (-4L: JLong, 4: Integer, -1L: JLong),
      (-5L: JLong, 4: Integer, -2L: JLong))

  test("test_floorDiv_long") {
    data_floorDiv.foreach {
      case (a, b, expected) =>
        assertEquals(Math.floorDiv(a, b), expected)
    }
  }

  test("test_floorDiv_int") {
    data_floorDiv.foreach {
      case (a, b, expected) =>
        if (a <= Integer.MAX_VALUE && a >= Integer.MIN_VALUE) {
          assertEquals(Math.floorDiv(a.toInt, b), expected.toInt)
        }
    }
  }

  val data_floorMod: List[(Long, Long, Int)] =
    List(
      (5L: JLong, 4, 1),
      (4L: JLong, 4, 0),
      (3L: JLong, 4, 3),
      (2L: JLong, 4, 2),
      (1L: JLong, 4, 1),
      (0L: JLong, 4, 0),
      (-1L: JLong, 4, 3),
      (-2L: JLong, 4, 2),
      (-3L: JLong, 4, 1),
      (-4L: JLong, 4, 0),
      (-5L: JLong, 4, 3))

  test("test_floorMod_long") {
    data_floorMod.foreach {
      case (a, b, expected) =>
        assertEquals(Math.floorMod(a, b), expected)
    }
  }

  test("test_floorMod_int") {
    data_floorMod.foreach {
      case (a, b, expected) =>
        if (a <= Integer.MAX_VALUE && a >= Integer.MIN_VALUE) {
          assertEquals(Math.floorMod(a.toInt, b), expected)
        }
    }
  }
}
