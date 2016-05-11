package scala.scalajs.js

import java.text.DecimalFormatSymbols
import java.util.Locale

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

object LocaleRegistry {
  case class LocaleCldr(locale: Locale, decimalFormatSymbol: Option[DecimalFormatSymbols])

  private var locales: Map[String, LocaleCldr] = Map.empty

  // TODO verify how stable is the CLDR json
  @ScalaJSDefined
  trait CLDRNumberLatnSymbols extends js.Object {
    val decimal: String
    val minusSign: String
  }

  @ScalaJSDefined
  trait CLDRNumberSymbols extends js.Object {
    val latn: CLDRNumberLatnSymbols
  }

  @ScalaJSDefined
  trait CLDRNumber extends js.Object {
    val nu: Array[String]
    val symbols: CLDRNumberSymbols
  }

  /**
    * Interface describing the json structure expected
    */
  @ScalaJSDefined
  trait CLDR extends js.Object {
    val locale: String
    val number: CLDRNumber
  }

  def forLanguageTag(languageTag: String): Option[Locale] = {
    locales.get(languageTag).map(_.locale)
  }

  def resetDatabase(): Unit = {
    locales = Map.empty
  }

  def installLocale(json: String): Unit = {
    // TODO Support all the options for unicode, including variants, numeric regions, etc
    val simpleLocaleRegex = "([a-zA-Z]{2,3})[-_]([a-zA-Z]{2})?.*".r

    val localeJson = JSON.parse(json).asInstanceOf[CLDR]

    // Read basic locale data
    val localeName = localeJson.locale.toString
    val locale = localeName match {
      case simpleLocaleRegex(lang, region) => Some(new Locale(lang, region, ""))
      case _                               => None
    }

    val dfs = if (localeJson.number.nu.contains("latn")) {
      // Uses latin numbers
      val zeroSign = '0'
      val decimal = localeJson.number.symbols.latn.decimal.charAt(0)
      val negativeSign = localeJson.number.symbols.latn.minusSign.charAt(0)

      val decimalFormatSymbol = new DecimalFormatSymbols()
      decimalFormatSymbol.setZeroDigit(zeroSign)
      decimalFormatSymbol.setDecimalSeparator(decimal)
      decimalFormatSymbol.setMinusSign(negativeSign)
      Some(decimalFormatSymbol)
    } else {
      None
    }

    locale.foreach {l =>
      locales = locales + (localeName -> LocaleCldr(l, dfs)) + (localeName.replaceAll("-", "_") -> LocaleCldr(l, dfs))
    }

  }
}
