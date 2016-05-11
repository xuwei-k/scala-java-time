package java.text

import java.util.Locale

import scala.scalajs.locale.LocaleRegistry

object DecimalFormatSymbols {
  def getAvailableLocales():Array[Locale] = Array.empty
  def getInstance(locale: Locale):DecimalFormatSymbols =
    LocaleRegistry.decimalFormatSymbol(locale).getOrElse(throw new IllegalArgumentException("Unknown locale"))
}

class DecimalFormatSymbols() {
  private[this] var zeroDigit: Option[Char] = None
  private[this] var minusSign: Option[Char] = None
  private[this] var decimalSeparator: Option[Char] = None

  def getZeroDigit(): Char        = zeroDigit.getOrElse(0)
  def getMinusSign(): Char        = minusSign.getOrElse(0)
  def getDecimalSeparator(): Char = decimalSeparator.getOrElse(0)

  def setZeroDigit(zeroDigit: Char):Unit =
    this.zeroDigit = Some(zeroDigit)
  def setMinusSign(minusSign: Char):Unit =
    this.minusSign = Some(minusSign)
  def setDecimalSeparator(decimalSeparator: Char):Unit =
    this.decimalSeparator = Some(decimalSeparator)

  // TODO Complete the rest of the public methods
}
