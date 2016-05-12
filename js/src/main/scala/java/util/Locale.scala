package java.util

import scala.scalajs.locale.LocaleRegistry

object Locale {

  private var defaultLocale: Option[Locale] = None
  val US: Locale      = LocaleRegistry.en_US.toLocale
  val ENGLISH: Locale = LocaleRegistry.en_US.toLocale
  val CANADA: Locale  = new Locale("en", "CA", "")

  private val EMPTY: Locale = new Locale("", "", "")

  def getAvailableLocales(): Array[Locale] = Array(US, CANADA)

  def getDefault(): Locale = defaultLocale
    .getOrElse(throw new IllegalStateException("No default locale set"))

  def setDefault(newLocale: Locale): Unit = {
    defaultLocale = Some(newLocale)
  }

  def forLanguageTag(languageTag: String): Locale = LocaleRegistry
    .localeForLanguageTag(languageTag).getOrElse(EMPTY)
}

class Locale(private val language: String, private val country: String, private val variant: String) {
  // Required by the javadocs
  if (language == null || country == null || variant == null) {
    throw new NullPointerException("Null argument to constructor not allowed")
  }

  // Additional constructors
  def this(language: String, country: String) = this(language, country, "")
  def this(language: String) = this(language, "", "")

  def getLanguage(): String = language
  def getCountry(): String = country
  def getVariant(): String = variant
  def getScript(): String = ""

  // TODO Add other methods on the public interface
  def getUnicodeLocaleType(key: String):String = ???

  override def equals(x: Any):Boolean = x match {
    case l: Locale =>
      language == l.language && country == l.country && variant == l.variant
    case _         =>
      false
  }
}
