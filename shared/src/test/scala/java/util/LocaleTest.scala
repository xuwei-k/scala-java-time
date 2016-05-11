package scala.scalajs.testsuite.javalibex

import java.util.Locale

import org.junit.{Before, Test}
import org.junit.Assert._

import scala.scalajs.js.LocaleRegistry
import scala.scalajs.testsuite.utils.AssertThrows._

class LocaleTest extends LocaleTestData {
  @Before def reset(): Unit = {
    // Ensure no locale has been installed
    LocaleRegistry.resetRegistry()
  }

  @Test def test_no_default_locale(): Unit = {

    expectThrows(classOf[IllegalStateException], Locale.getDefault)
  }

  @Test def test_loaded_en_US(): Unit = {
    LocaleRegistry.installLocale(enUS)

    assertEquals(Locale.forLanguageTag("en_US").getLanguage, "en")
    assertEquals(Locale.forLanguageTag("en_US").getCountry, "US")
    assertEquals(Locale.forLanguageTag("en-US").getLanguage, "en")
    assertEquals(Locale.forLanguageTag("en-US").getCountry, "US")

    // Not installed locales return an empty locale as in the JVM
    assertEquals(Locale.forLanguageTag("en_CA").getCountry, "")
    assertEquals(Locale.forLanguageTag("en_CA").getLanguage, "")
    assertEquals(Locale.forLanguageTag("en_CA").getVariant, "")
  }
  
}
