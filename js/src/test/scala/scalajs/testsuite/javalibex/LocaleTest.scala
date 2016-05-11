package scalajs.testsuite.javalibex

import java.util

import org.junit.{Before, Test}
import org.junit.Assert._

import scala.scalajs.locale.LocaleRegistry

import scalajs.testsuite.utils.AssertThrows.expectThrows

class LocaleTest extends LocaleTestData {
  @Before def reset(): Unit = {
    // Ensure no locale has been installed
    LocaleRegistry.resetRegistry()
  }

  @Test def test_no_default_locale(): Unit = {
    expectThrows(classOf[IllegalStateException], util.Locale.getDefault)
  }

  @Test def test_loaded_en_US(): Unit = {
    LocaleRegistry.installLocale(enUS)

    assertEquals(util.Locale.forLanguageTag("en_US").getLanguage, "en")
    assertEquals(util.Locale.forLanguageTag("en_US").getCountry, "US")
    assertEquals(util.Locale.forLanguageTag("en-US").getLanguage, "en")
    assertEquals(util.Locale.forLanguageTag("en-US").getCountry, "US")

    // Not installed locales return an empty locale as in the JVM
    assertEquals(util.Locale.forLanguageTag("en_CA").getCountry, "")
    assertEquals(util.Locale.forLanguageTag("en_CA").getLanguage, "")
    assertEquals(util.Locale.forLanguageTag("en_CA").getVariant, "")
  }
  
}
