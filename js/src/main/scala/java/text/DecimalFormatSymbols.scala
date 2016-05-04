package java.text

import org.threeten.bp.format.DecimalStyle

object DecimalFormatSymbols {
  def getAvailableLocales: Array[java.util.Locale] = java.util.Locale.getAvailableLocales()
  def getInstance: DecimalFormatSymbols = defaultInstance
  def getInstance(locale: java.util.Locale): DecimalFormatSymbols = defaultInstance

  private val defaultInstance = new DecimalFormatSymbols(
    DecimalStyle.STANDARD.getZeroDigit,
    DecimalStyle.STANDARD.getPositiveSign,
    DecimalStyle.STANDARD.getNegativeSign,
    DecimalStyle.STANDARD.getDecimalSeparator)
}

class DecimalFormatSymbols private (zeroDigit: Char, positiveSign: Char, negativeSign: Char, decimalSeparator: Char) {
  def getZeroDigit: Char        = zeroDigit
  def getPositiveSign: Char     = positiveSign
  def getMinusSign: Char        = negativeSign
  def getDecimalSeparator: Char = decimalSeparator
}
