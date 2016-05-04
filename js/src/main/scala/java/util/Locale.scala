package java.util

object Locale {
  def getDefault: Locale = new Locale("", "")
  val US: Locale    = new Locale("en", "US")
  val CANADA: Locale    = new Locale("en", "CA")

  def getAvailableLocales(): Array[Locale] = Array(US, CANADA)
}

class Locale(language: String, country: String) {
  def getCountry: String = country
  def getLanguage: String = language
}
