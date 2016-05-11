package scalajs.testsuite.javalibex

import java.text.DecimalFormatSymbols
import java.util.Locale

import org.junit.{Before, Test}
import org.junit.Assert.assertTrue

import scala.scalajs.locale.LocaleRegistry

class DecimalFormatSymbolsTest extends LocaleTestData {
  @Before def reset(): Unit = {
    // Ensure no locale has been installed
    LocaleRegistry.resetRegistry()
  }

  @Test def test_decimal_format_symborn_en_US(): Unit = {
    LocaleRegistry.installLocale(enUS)
    val dfs = DecimalFormatSymbols.getInstance(Locale.forLanguageTag("en_US"))

    assertTrue(dfs.getDecimalSeparator == '.')
    assertTrue(dfs.getMinusSign == '-')
    assertTrue(dfs.getZeroDigit == '0')
  }

}
