package scala.scalajs.locale

import java.text.DecimalFormatSymbols
import java.util.Locale

/**
  * Interface describing LDNL
  */
object ldml {

  case class LDMLLocale(language: String, territory: Option[String], variant: Option[String], script: Option[String])
  case class LDML(locale: LDMLLocale) {
    // TODO support script and extensions
    def languageTag: String = locale.language + locale.territory.fold("")(t => s"-$t") + locale.variant.fold("")(v => s"-$v") + locale.script.fold("")(s => s"#$s")

    def scalaSafeName: String = locale.language + locale.territory.fold("")(t => s"_$t") + locale.variant.fold("")(v => s"_$v") + locale.script.fold("")(s => s"_$s")

    def toLocale: Locale =
      new Locale(locale.language, locale.territory.getOrElse(""), locale.variant.getOrElse(""))
  }

}

object LocaleRegistry {
  import ldml._

  // The spec requires some locales by default
  val en_US: LDML = LDML(LDMLLocale("en", Some("US"), None, None))

  case class LocaleCldr(locale: Locale, decimalFormatSymbol: Option[DecimalFormatSymbols])

  private val defaultLocales: Map[String, LDML] = Map(
    en_US.languageTag -> en_US
  )
  private var locales: Map[String, LDML] = Map.empty

  /**
    * Attempts to give a Locale for the given tag if avaibale
    */
  def localeForLanguageTag(languageTag: String): Option[Locale] = {
    // TODO Support alternative tags for the same locale
    (defaultLocales ++ locales).get(languageTag).map(_.toLocale)
  }

  /**
    * Attempts to give a Locale for the given tag if avaibale
    */
  def decimalFormatSymbol(locale: Locale): Option[DecimalFormatSymbols] = ???

  /**
    * Cleans the registry, useful for testing
    */
  def resetRegistry(): Unit = {
    //locales = Map.empty
  }

  def installLocale(json: String): Unit = {
    // TODO Support all the options for unicode, including variants, numeric regions, etc
    val simpleLocaleRegex = "([a-zA-Z]{2,3})[-_]([a-zA-Z]{2})?.*".r

    /*val localeJson = js.JSON.parse(json).asInstanceOf[CLDR]

    // Read basic locale data
    val localeName = localeJson.locale.toString
    val locale = localeName match {
      case simpleLocaleRegex(lang, region) => Some(new Locale(lang, region, ""))
      case _                               => None
    }*/

    /*val dfs = if (localeJson.number.nu.contains("latn")) {
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
    }*/

    /*locale.foreach {l =>
      locales = locales + (localeName -> LocaleCldr(l, dfs)) + (localeName.replaceAll("-", "_") -> LocaleCldr(l, dfs))
    }*/

  }
}
