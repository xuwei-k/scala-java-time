package scalajs.testsuite.javalibex

import java.util.Locale

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
    expectThrows(classOf[IllegalStateException], Locale.getDefault)
  }

  @Test def test_default_en_US(): Unit = {
    assertEquals("en", Locale.forLanguageTag("en-US").getLanguage)
    assertEquals("US", Locale.forLanguageTag("en-US").getCountry)
    assertEquals("", Locale.forLanguageTag("en-US").getVariant)
    assertEquals("", Locale.forLanguageTag("en-US").getScript)
  }
  
}
