package java.text

import org.threeten.bp.format.DecimalStyle


class DecimalFormatSymbols private(zeroDigit: Char, positiveSign: Char, negativeSign: Char, decimalSeparator: Char) {
  def getZeroDigit: Char        = zeroDigit
  def getPositiveSign: Char     = positiveSign
  def getMinusSign: Char        = negativeSign
  def getDecimalSeparator: Char = decimalSeparator
}
