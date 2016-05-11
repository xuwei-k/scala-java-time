package scala.scalajs.js

import java.util.Locale

object LocaleDB {
  private var locales: Map[String, Locale] = Map.empty

  def forLanguageTag(languageTag: String): Option[Locale] = {
    locales.get(languageTag)
  }

  def resetDatabase(): Unit = {
    locales = Map.empty
  }

  def installLocale(json: String): Unit = {
    // TODO Support all the options for unicode, including variants, numeric regions, etc
    val simpleLocaleRegex = "([a-zA-Z]{2,3})[-_]([a-zA-Z]{2})?.*".r

    val localeName = JSON.parse(json).locale.toString
    val u = localeName match {
      case simpleLocaleRegex(lang, region) => Some(new Locale(lang, region, ""))
      case _                               => None
    }

    u.foreach {l =>
      locales = locales + (localeName -> l) + (localeName.replaceAll("-", "_") -> l)
    }
  }
}
