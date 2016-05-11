package java.util

import java.text.DecimalFormatSymbols

import org.junit.Assert._
import org.junit.{Before, Test}

class DecimalFormatSymbolsTest extends LocaleTestData {
  @Before def reset(): Unit = {
    // Ensure no locale has been installed
    LocaleRegistry.resetRegistry()
  }

  @Test def test_decimal_format_symborn_en_US(): Unit = {
    LocaleRegistry.installLocale(enUS)
    val dfs = DecimalFormatSymbols.getInstance(Locale.forLanguageTag("en_US"))

    assertEquals(dfs.getDecimalSeparator, '.')
    assertEquals(dfs.getMinusSign, '-')
    assertEquals(dfs.getZeroDigit, '0')
  }

}
