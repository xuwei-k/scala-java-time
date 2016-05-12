package scalajs.testsuite.javalibex

import java.util.Locale

import org.junit.{Before, Ignore, Test}
import org.junit.Assert._

import scala.scalajs.locale.LocaleRegistry
import scalajs.testsuite.utils.AssertThrows.expectThrows

class LocaleTest {
  @Before def reset(): Unit = {
    // Ensure no locale has been installed
    LocaleRegistry.resetRegistry()
  }

  // Unlike the JVM, the Js backend cannot give a default locale
  @Ignore @Test def test_no_default_locale(): Unit = {
    expectThrows(classOf[IllegalStateException], Locale.getDefault)
  }

  @Test def test_null_constructor(): Unit = {
    expectThrows(classOf[NullPointerException], new Locale(null))
    expectThrows(classOf[NullPointerException], new Locale("", null))
    expectThrows(classOf[NullPointerException], new Locale("", "", null))
  }

  @Test def test_default_CANADA(): Unit = {
    assertEquals("en", Locale.forLanguageTag("en-CA").getLanguage)
    assertEquals("CA", Locale.forLanguageTag("en-CA").getCountry)
    assertEquals("", Locale.forLanguageTag("en-CA").getVariant)
    assertEquals("", Locale.forLanguageTag("en-CA").getScript)

    assertEquals(Locale.CANADA, Locale.forLanguageTag("en-CA"))
  }

  @Test def test_default_CANADA_FRENCH(): Unit = {
    assertEquals("fr", Locale.forLanguageTag("fr-CA").getLanguage)
    assertEquals("CA", Locale.forLanguageTag("fr-CA").getCountry)
    assertEquals("", Locale.forLanguageTag("fr-CA").getVariant)
    assertEquals("", Locale.forLanguageTag("fr-CA").getScript)

    assertEquals(Locale.CANADA_FRENCH, Locale.forLanguageTag("fr-CA"))
  }

  @Test def test_default_CHINA(): Unit = {
    assertEquals("zh", Locale.forLanguageTag("zh-CN").getLanguage)
    assertEquals("CN", Locale.forLanguageTag("zh-CN").getCountry)
    assertEquals("", Locale.forLanguageTag("zh-CN").getVariant)
    assertEquals("", Locale.forLanguageTag("zh-CN").getScript)

    assertEquals(Locale.CHINA, Locale.forLanguageTag("zh-CN"))
  }

  @Test def test_default_CHINESE(): Unit = {
    assertEquals("zh", Locale.forLanguageTag("zh").getLanguage)
    assertEquals("", Locale.forLanguageTag("zh").getCountry)
    assertEquals("", Locale.forLanguageTag("zh").getVariant)
    assertEquals("", Locale.forLanguageTag("zh").getScript)

    assertEquals(Locale.CHINESE, Locale.forLanguageTag("zh"))
  }

  @Test def test_default_ENGLISH(): Unit = {
    assertEquals("en", Locale.forLanguageTag("en").getLanguage)
    assertEquals("", Locale.forLanguageTag("en").getCountry)
    assertEquals("", Locale.forLanguageTag("en").getVariant)
    assertEquals("", Locale.forLanguageTag("en").getScript)

    assertEquals(Locale.ENGLISH, Locale.forLanguageTag("en"))
  }

  @Test def test_default_FRANCE(): Unit = {
    assertEquals("fr", Locale.forLanguageTag("fr-FR").getLanguage)
    assertEquals("FR", Locale.forLanguageTag("fr-FR").getCountry)
    assertEquals("", Locale.forLanguageTag("fr-FR").getVariant)
    assertEquals("", Locale.forLanguageTag("fr-FR").getScript)

    assertEquals(Locale.FRANCE, Locale.forLanguageTag("fr-FR"))
  }

  @Test def test_default_FRENCH(): Unit = {
    assertEquals("fr", Locale.forLanguageTag("fr").getLanguage)
    assertEquals("", Locale.forLanguageTag("fr").getCountry)
    assertEquals("", Locale.forLanguageTag("fr").getVariant)
    assertEquals("", Locale.forLanguageTag("fr").getScript)

    assertEquals(Locale.FRENCH, Locale.forLanguageTag("fr"))
  }

  @Test def test_default_GERMAN(): Unit = {
    assertEquals("de", Locale.forLanguageTag("de").getLanguage)
    assertEquals("", Locale.forLanguageTag("de").getCountry)
    assertEquals("", Locale.forLanguageTag("de").getVariant)
    assertEquals("", Locale.forLanguageTag("de").getScript)

    assertEquals(Locale.GERMAN, Locale.forLanguageTag("de"))
  }

  @Test def test_default_GERMANY(): Unit = {
    assertEquals("de", Locale.forLanguageTag("de-DE").getLanguage)
    assertEquals("DE", Locale.forLanguageTag("de-DE").getCountry)
    assertEquals("", Locale.forLanguageTag("de-DE").getVariant)
    assertEquals("", Locale.forLanguageTag("de-DE").getScript)

    assertEquals(Locale.GERMANY, Locale.forLanguageTag("de-DE"))
  }

  @Test def test_default_JAPAN(): Unit = {
    assertEquals("ja", Locale.forLanguageTag("ja-JP").getLanguage)
    assertEquals("JP", Locale.forLanguageTag("ja-JP").getCountry)
    assertEquals("", Locale.forLanguageTag("ja-JP").getVariant)
    assertEquals("", Locale.forLanguageTag("ja-JP").getScript)

    assertEquals(Locale.JAPAN, Locale.forLanguageTag("ja-JP"))
  }

  @Test def test_default_JAPANESE(): Unit = {
    assertEquals("ja", Locale.forLanguageTag("ja").getLanguage)
    assertEquals("", Locale.forLanguageTag("ja").getCountry)
    assertEquals("", Locale.forLanguageTag("ja").getVariant)
    assertEquals("", Locale.forLanguageTag("ja").getScript)

    assertEquals(Locale.JAPANESE, Locale.forLanguageTag("ja"))
  }

  @Test def test_default_KOREA(): Unit = {
    assertEquals("ko", Locale.forLanguageTag("ko-KR").getLanguage)
    assertEquals("KR", Locale.forLanguageTag("ko-KR").getCountry)
    assertEquals("", Locale.forLanguageTag("ko-KR").getVariant)
    assertEquals("", Locale.forLanguageTag("ko-KR").getScript)

    assertEquals(Locale.KOREA, Locale.forLanguageTag("ko-KR"))
  }

  @Test def test_default_KOREAN(): Unit = {
    assertEquals("ko", Locale.forLanguageTag("ko").getLanguage)
    assertEquals("", Locale.forLanguageTag("ko").getCountry)
    assertEquals("", Locale.forLanguageTag("ko").getVariant)
    assertEquals("", Locale.forLanguageTag("ko").getScript)

    assertEquals(Locale.KOREAN, Locale.forLanguageTag("ko"))
  }

  @Test def test_default_PRC(): Unit = {
    assertEquals("zh", Locale.forLanguageTag("zh-CN").getLanguage)
    assertEquals("CN", Locale.forLanguageTag("zh-CN").getCountry)
    assertEquals("", Locale.forLanguageTag("zh-CN").getVariant)
    assertEquals("", Locale.forLanguageTag("zh-CN").getScript)

    assertEquals(Locale.PRC, Locale.forLanguageTag("zh-CN"))
  }

  @Test def test_default_ROOT(): Unit = {
    assertEquals("", Locale.forLanguageTag("").getLanguage)
    assertEquals("", Locale.forLanguageTag("").getCountry)
    assertEquals("", Locale.forLanguageTag("").getVariant)
    assertEquals("", Locale.forLanguageTag("").getScript)

    assertEquals(Locale.ROOT, Locale.forLanguageTag(""))
  }

  @Test def test_extension_flags(): Unit = {
    assertTrue('u' == Locale.UNICODE_LOCALE_EXTENSION)
    assertTrue('x' == Locale.PRIVATE_USE_EXTENSION)
  }

  @Test def test_default_TAIWAN(): Unit = {
    assertEquals("zh", Locale.forLanguageTag("zh-TW").getLanguage)
    assertEquals("TW", Locale.forLanguageTag("zh-TW").getCountry)
    assertEquals("", Locale.forLanguageTag("zh-TW").getVariant)
    assertEquals("", Locale.forLanguageTag("zh-TW").getScript)

    assertEquals(Locale.TAIWAN, Locale.forLanguageTag("zh-TW"))
  }

  @Test def test_chinese_equivalences(): Unit = {
    assertEquals(Locale.SIMPLIFIED_CHINESE, Locale.CHINA)
    assertEquals(Locale.TRADITIONAL_CHINESE, Locale.TAIWAN)
  }

  @Test def test_default_GB(): Unit = {
    assertEquals("en", Locale.forLanguageTag("en-GB").getLanguage)
    assertEquals("GB", Locale.forLanguageTag("en-GB").getCountry)
    assertEquals("", Locale.forLanguageTag("en-GB").getVariant)
    assertEquals("", Locale.forLanguageTag("en-GB").getScript)

    assertEquals(Locale.UK, Locale.forLanguageTag("en-GB"))
  }

}
