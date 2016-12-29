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
package org.threeten.bp.format

import java.util.Locale

import org.scalatest.FunSuite
import org.threeten.bp.AssertionsHelper
import org.threeten.bp.Platform
import org.threeten.bp.temporal.ChronoField.{AMPM_OF_DAY, DAY_OF_WEEK, MONTH_OF_YEAR}
import org.threeten.bp.temporal.{ChronoField, TemporalField}

/** Test SimpleDateTimeTextProvider. */
class TestSimpleDateTimeTextProvider extends FunSuite with AssertionsHelper {
  private val enUS: Locale = new Locale("en", "US")
  private val ptBR: Locale = new Locale("pt", "BR")
  private val frFR: Locale = new Locale("fr", "FR")

  def data_text: List[List[Any]] =
    List[List[Any]](
      List(DAY_OF_WEEK, 1, TextStyle.SHORT, enUS, "Mon"), List(DAY_OF_WEEK, 2, TextStyle.SHORT, enUS, "Tue"), List(DAY_OF_WEEK, 3, TextStyle.SHORT, enUS, "Wed"), List(DAY_OF_WEEK, 4, TextStyle.SHORT, enUS, "Thu"), List(DAY_OF_WEEK, 5, TextStyle.SHORT, enUS, "Fri"), List(DAY_OF_WEEK, 6, TextStyle.SHORT, enUS, "Sat"), List(DAY_OF_WEEK, 7, TextStyle.SHORT, enUS, "Sun"),
      List(DAY_OF_WEEK, 1, TextStyle.SHORT, ptBR, "Seg"), List(DAY_OF_WEEK, 2, TextStyle.SHORT, ptBR, "Ter"), List(DAY_OF_WEEK, 3, TextStyle.SHORT, ptBR, "Qua"), List(DAY_OF_WEEK, 4, TextStyle.SHORT, ptBR, "Qui"), List(DAY_OF_WEEK, 5, TextStyle.SHORT, ptBR, "Sex"), List(DAY_OF_WEEK, 6, TextStyle.SHORT, ptBR, "S\u00E1b"), List(DAY_OF_WEEK, 7, TextStyle.SHORT, ptBR, "Dom"),
      List(DAY_OF_WEEK, 1, TextStyle.FULL, enUS, "Monday"), List(DAY_OF_WEEK, 2, TextStyle.FULL, enUS, "Tuesday"), List(DAY_OF_WEEK, 3, TextStyle.FULL, enUS, "Wednesday"), List(DAY_OF_WEEK, 4, TextStyle.FULL, enUS, "Thursday"), List(DAY_OF_WEEK, 5, TextStyle.FULL, enUS, "Friday"), List(DAY_OF_WEEK, 6, TextStyle.FULL, enUS, "Saturday"), List(DAY_OF_WEEK, 7, TextStyle.FULL, enUS, "Sunday"),
      List(DAY_OF_WEEK, 1, TextStyle.FULL, ptBR, "Segunda-feira"), List(DAY_OF_WEEK, 2, TextStyle.FULL, ptBR, "Ter\u00E7a-feira"), List(DAY_OF_WEEK, 3, TextStyle.FULL, ptBR, "Quarta-feira"), List(DAY_OF_WEEK, 4, TextStyle.FULL, ptBR, "Quinta-feira"), List(DAY_OF_WEEK, 5, TextStyle.FULL, ptBR, "Sexta-feira"), List(DAY_OF_WEEK, 6, TextStyle.FULL, ptBR, "S\u00E1bado"), List(DAY_OF_WEEK, 7, TextStyle.FULL, ptBR, "Domingo"),
      List(MONTH_OF_YEAR, 1, TextStyle.SHORT, enUS, "Jan"), List(MONTH_OF_YEAR, 2, TextStyle.SHORT, enUS, "Feb"), List(MONTH_OF_YEAR, 3, TextStyle.SHORT, enUS, "Mar"), List(MONTH_OF_YEAR, 4, TextStyle.SHORT, enUS, "Apr"), List(MONTH_OF_YEAR, 5, TextStyle.SHORT, enUS, "May"), List(MONTH_OF_YEAR, 6, TextStyle.SHORT, enUS, "Jun"), List(MONTH_OF_YEAR, 7, TextStyle.SHORT, enUS, "Jul"), List(MONTH_OF_YEAR, 8, TextStyle.SHORT, enUS, "Aug"), List(MONTH_OF_YEAR, 9, TextStyle.SHORT, enUS, "Sep"), List(MONTH_OF_YEAR, 10, TextStyle.SHORT, enUS, "Oct"), List(MONTH_OF_YEAR, 11, TextStyle.SHORT, enUS, "Nov"), List(MONTH_OF_YEAR, 12, TextStyle.SHORT, enUS, "Dec"),
      List(MONTH_OF_YEAR, 1, TextStyle.SHORT, frFR, "janv."), List(MONTH_OF_YEAR, 2, TextStyle.SHORT, frFR, "f\u00E9vr."), List(MONTH_OF_YEAR, 3, TextStyle.SHORT, frFR, "mars"), List(MONTH_OF_YEAR, 4, TextStyle.SHORT, frFR, "avr."), List(MONTH_OF_YEAR, 5, TextStyle.SHORT, frFR, "mai"), List(MONTH_OF_YEAR, 6, TextStyle.SHORT, frFR, "juin"), List(MONTH_OF_YEAR, 7, TextStyle.SHORT, frFR, "juil."), List(MONTH_OF_YEAR, 8, TextStyle.SHORT, frFR, "ao\u00FBt"), List(MONTH_OF_YEAR, 9, TextStyle.SHORT, frFR, "sept."), List(MONTH_OF_YEAR, 10, TextStyle.SHORT, frFR, "oct."), List(MONTH_OF_YEAR, 11, TextStyle.SHORT, frFR, "nov."), List(MONTH_OF_YEAR, 12, TextStyle.SHORT, frFR, "d\u00E9c."),
      List(MONTH_OF_YEAR, 1, TextStyle.FULL, enUS, "January"), List(MONTH_OF_YEAR, 2, TextStyle.FULL, enUS, "February"), List(MONTH_OF_YEAR, 3, TextStyle.FULL, enUS, "March"), List(MONTH_OF_YEAR, 4, TextStyle.FULL, enUS, "April"), List(MONTH_OF_YEAR, 5, TextStyle.FULL, enUS, "May"), List(MONTH_OF_YEAR, 6, TextStyle.FULL, enUS, "June"), List(MONTH_OF_YEAR, 7, TextStyle.FULL, enUS, "July"), List(MONTH_OF_YEAR, 8, TextStyle.FULL, enUS, "August"), List(MONTH_OF_YEAR, 9, TextStyle.FULL, enUS, "September"), List(MONTH_OF_YEAR, 10, TextStyle.FULL, enUS, "October"), List(MONTH_OF_YEAR, 11, TextStyle.FULL, enUS, "November"), List(MONTH_OF_YEAR, 12, TextStyle.FULL, enUS, "December"),
      List(MONTH_OF_YEAR, 1, TextStyle.FULL, ptBR, "Janeiro"), List(MONTH_OF_YEAR, 2, TextStyle.FULL, ptBR, "Fevereiro"), List(MONTH_OF_YEAR, 3, TextStyle.FULL, ptBR, "Mar\u00E7o"), List(MONTH_OF_YEAR, 4, TextStyle.FULL, ptBR, "Abril"), List(MONTH_OF_YEAR, 5, TextStyle.FULL, ptBR, "Maio"), List(MONTH_OF_YEAR, 6, TextStyle.FULL, ptBR, "Junho"), List(MONTH_OF_YEAR, 7, TextStyle.FULL, ptBR, "Julho"), List(MONTH_OF_YEAR, 8, TextStyle.FULL, ptBR, "Agosto"), List(MONTH_OF_YEAR, 9, TextStyle.FULL, ptBR, "Setembro"), List(MONTH_OF_YEAR, 10, TextStyle.FULL, ptBR, "Outubro"), List(MONTH_OF_YEAR, 11, TextStyle.FULL, ptBR, "Novembro"), List(MONTH_OF_YEAR, 12, TextStyle.FULL, ptBR, "Dezembro"),
      List(AMPM_OF_DAY, 0, TextStyle.SHORT, enUS, "AM"), List(AMPM_OF_DAY, 1, TextStyle.SHORT, enUS, "PM"))

  test("getText") {
    data_text.foreach {
      case (field: TemporalField) :: (value: Number) :: (style: TextStyle) :: (locale: Locale) :: (expected: String) :: Nil =>
        Platform.setupLocales()
        val tp: DateTimeTextProvider = DateTimeTextProvider.getInstance

        // Work around difference between JRE locale data and CLDR data:
        // JRE specifies capitalized brazilian month and day-of-week names,
        // while CLDR specifies lower-cased names.
        if (locale == ptBR && (field == ChronoField.MONTH_OF_YEAR || field == ChronoField.DAY_OF_WEEK))
          assertEquals(tp.getText(field, value.longValue, style, locale).capitalize, expected)
        else
          assertEquals(tp.getText(field, value.longValue, style, locale), expected)
      case _ =>
        fail()
    }
  }
}
