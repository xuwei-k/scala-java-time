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

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.threeten.bp.Month.JANUARY
import org.threeten.bp.chrono.IsoChronology
import org.threeten.bp.format.{DateTimeFormatter, DateTimeParseException}
import org.threeten.bp.temporal.ChronoField._
import org.threeten.bp.temporal.ChronoUnit.{HOURS, MINUTES, NANOS, SECONDS}
import org.threeten.bp.temporal._

class TestZonedDateTimeSerialization extends FunSuite with AssertionsHelper with BeforeAndAfter with AbstractTest {
  private var TEST_PARIS_GAP_2008_03_30_02_30: LocalDateTime = null
  private var TEST_PARIS_OVERLAP_2008_10_26_02_30: LocalDateTime = null
  private var TEST_LOCAL_2008_06_30_11_30_59_500: LocalDateTime = null
  private var TEST_DATE_TIME: ZonedDateTime = null
  private var TEST_DATE_TIME_PARIS: ZonedDateTime = null

  before {
    TEST_LOCAL_2008_06_30_11_30_59_500 = LocalDateTime.of(2008, 6, 30, 11, 30, 59, 500)
    TEST_DATE_TIME = ZonedDateTime.of(TEST_LOCAL_2008_06_30_11_30_59_500, TestZonedDateTime.ZONE_0100)
    TEST_DATE_TIME_PARIS = ZonedDateTime.of(TEST_LOCAL_2008_06_30_11_30_59_500, TestZonedDateTime.ZONE_PARIS)
    TEST_PARIS_OVERLAP_2008_10_26_02_30 = LocalDateTime.of(2008, 10, 26, 2, 30)
    TEST_PARIS_GAP_2008_03_30_02_30 = LocalDateTime.of(2008, 3, 30, 2, 30)
  }

  test("test_serialization") {
    assertSerializable(TEST_DATE_TIME)
  }

  test("test_serialization_format") {
    val zdt: ZonedDateTime = LocalDateTime.of(2012, 9, 16, 22, 17, 59, 470 * 1000000).atZone(ZoneId.of("Europe/London"))
    assertEqualsSerialisedForm(zdt)
  }

}
