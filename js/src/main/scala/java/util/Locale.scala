package java.util

object Locale {
  var defaultLocale: Option[Locale] = None
  val US: Locale    = new Locale("en", "US")
  val CANADA: Locale    = new Locale("en", "CA")

  def getAvailableLocales(): Array[Locale] = Array(US, CANADA)

  def getDefault(): Locale = defaultLocale
    .getOrElse(throw new IllegalStateException("No default locale set"))

  def setDefault(newLocale: Locale): Unit = {
    defaultLocale = Some(newLocale)
  }
}

class Locale(language: String, country: String, variant: String) {
  def getCountry(): String = Option(country).getOrElse("")
  def getLanguage(): String = Option(language).getOrElse("")
  def getVariant(): String = Option(variant).getOrElse("")
}
