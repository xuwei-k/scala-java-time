package java.util

import scala.scalajs.locale.LocaleRegistry

object Locale {

  private var defaultLocale: Option[Locale] = None

  val CANADA: Locale              = LocaleRegistry.en_CA.toLocale
  val CANADA_FRENCH: Locale       = LocaleRegistry.fr_CA.toLocale
  val CHINA: Locale               = LocaleRegistry.zh_CN_Hans.toLocale
  val CHINESE: Locale             = LocaleRegistry.zh.toLocale
  val ENGLISH: Locale             = LocaleRegistry.en.toLocale
  val FRANCE: Locale              = LocaleRegistry.fr_FR.toLocale
  val FRENCH: Locale              = LocaleRegistry.fr.toLocale
  val GERMAN: Locale              = LocaleRegistry.de.toLocale
  val GERMANY: Locale             = LocaleRegistry.de_DE.toLocale
  val ITALIAN: Locale             = LocaleRegistry.it.toLocale
  val ITALY: Locale               = LocaleRegistry.it_IT.toLocale
  val JAPAN: Locale               = LocaleRegistry.ja_JP.toLocale
  val JAPANESE: Locale            = LocaleRegistry.ja.toLocale
  val KOREA: Locale               = LocaleRegistry.ko_KR.toLocale
  val KOREAN: Locale              = LocaleRegistry.ko.toLocale
  val PRC: Locale                 = LocaleRegistry.zh_CN_Hans.toLocale
  val ROOT: Locale                = new Locale("", "", "")
  val SIMPLIFIED_CHINESE: Locale  = CHINA
  val TAIWAN: Locale              = LocaleRegistry.zh_TW_Hant.toLocale
  val TRADITIONAL_CHINESE: Locale = TAIWAN
  val UK: Locale                  = LocaleRegistry.en_GB.toLocale
  val US: Locale                  = LocaleRegistry.en_US.toLocale

  val PRIVATE_USE_EXTENSION: Char = 'x'
  val UNICODE_LOCALE_EXTENSION: Char = 'u'


  def getAvailableLocales(): Array[Locale] = Array(US, CANADA)

  def getDefault(): Locale = defaultLocale
    .getOrElse(throw new IllegalStateException("No default locale set"))

  def setDefault(newLocale: Locale): Unit = {
    defaultLocale = Some(newLocale)
  }

  def forLanguageTag(languageTag: String): Locale = LocaleRegistry
    .localeForLanguageTag(languageTag).getOrElse(ROOT)
}

class Locale(private val language: String,
             private val country: String,
             private val variant: String) {

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
