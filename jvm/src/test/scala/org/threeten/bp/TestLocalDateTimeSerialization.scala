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

import org.threeten.bp.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH
import org.threeten.bp.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR
import org.threeten.bp.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH
import org.threeten.bp.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR
import org.threeten.bp.temporal.ChronoField.AMPM_OF_DAY
import org.threeten.bp.temporal.ChronoField.CLOCK_HOUR_OF_AMPM
import org.threeten.bp.temporal.ChronoField.CLOCK_HOUR_OF_DAY
import org.threeten.bp.temporal.ChronoField.DAY_OF_MONTH
import org.threeten.bp.temporal.ChronoField.DAY_OF_WEEK
import org.threeten.bp.temporal.ChronoField.DAY_OF_YEAR
import org.threeten.bp.temporal.ChronoField.EPOCH_DAY
import org.threeten.bp.temporal.ChronoField.ERA
import org.threeten.bp.temporal.ChronoField.HOUR_OF_AMPM
import org.threeten.bp.temporal.ChronoField.HOUR_OF_DAY
import org.threeten.bp.temporal.ChronoField.MICRO_OF_DAY
import org.threeten.bp.temporal.ChronoField.MICRO_OF_SECOND
import org.threeten.bp.temporal.ChronoField.MILLI_OF_DAY
import org.threeten.bp.temporal.ChronoField.MILLI_OF_SECOND
import org.threeten.bp.temporal.ChronoField.MINUTE_OF_DAY
import org.threeten.bp.temporal.ChronoField.MINUTE_OF_HOUR
import org.threeten.bp.temporal.ChronoField.MONTH_OF_YEAR
import org.threeten.bp.temporal.ChronoField.NANO_OF_DAY
import org.threeten.bp.temporal.ChronoField.NANO_OF_SECOND
import org.threeten.bp.temporal.ChronoField.PROLEPTIC_MONTH
import org.threeten.bp.temporal.ChronoField.SECOND_OF_DAY
import org.threeten.bp.temporal.ChronoField.SECOND_OF_MINUTE
import org.threeten.bp.temporal.ChronoField.YEAR
import org.threeten.bp.temporal.ChronoField.YEAR_OF_ERA
import org.threeten.bp.temporal.ChronoUnit.HALF_DAYS
import org.threeten.bp.temporal.ChronoUnit.HOURS
import org.threeten.bp.temporal.ChronoUnit.MICROS
import org.threeten.bp.temporal.ChronoUnit.MILLIS
import org.threeten.bp.temporal.ChronoUnit.MINUTES
import org.threeten.bp.temporal.ChronoUnit.NANOS
import org.threeten.bp.temporal.ChronoUnit.SECONDS
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.reflect.Field
import java.lang.reflect.Modifier

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.threeten.bp.chrono.IsoChronology
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import org.threeten.bp.temporal.ChronoField
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.JulianFields
import org.threeten.bp.temporal.MockFieldNoValue
import org.threeten.bp.temporal.Temporal
import org.threeten.bp.temporal.TemporalAccessor
import org.threeten.bp.temporal.TemporalAdjuster
import org.threeten.bp.temporal.TemporalField
import org.threeten.bp.temporal.TemporalQueries
import org.threeten.bp.temporal.TemporalUnit

class TestLocalDateTimeSerialization extends FunSuite with AssertionsHelper with BeforeAndAfter {
  private val TEST_2007_07_15_12_30_40_987654321: LocalDateTime = LocalDateTime.of(2007, 7, 15, 12, 30, 40, 987654321)

  test("serialization") {
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val oos: ObjectOutputStream = new ObjectOutputStream(baos)
    oos.writeObject(TEST_2007_07_15_12_30_40_987654321)
    oos.close()
    val ois: ObjectInputStream = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray))
    assertEquals(ois.readObject, TEST_2007_07_15_12_30_40_987654321)
  }

  test("immutable") {
    val cls: Class[LocalDateTime] = classOf[LocalDateTime]
    assertTrue(Modifier.isPublic(cls.getModifiers))
    assertTrue(Modifier.isFinal(cls.getModifiers))
    val fields: Array[Field] = cls.getDeclaredFields
    for (field <- fields) {
      if (!field.getName.contains("$")) {
        if (Modifier.isStatic(field.getModifiers)) {
          assertTrue(Modifier.isFinal(field.getModifiers))
        }
        else {
          assertTrue(Modifier.isPrivate(field.getModifiers))
          assertTrue(Modifier.isFinal(field.getModifiers))
        }
      }
    }
  }

}
