package scala.scalajs.testsuite.javalibex

import java.util.Locale

import org.junit.{Before, Test}
import org.junit.Assert._

import scala.scalajs.js.LocaleDB
import scala.scalajs.testsuite.utils.AssertThrows._

class LocaleTest {
  @Before def reset(): Unit = {
    // Ensure no locale has been installed
    LocaleDB.resetDatabase()
  }

  @Test def test_no_default_locale(): Unit = {

    expectThrows(classOf[IllegalStateException], Locale.getDefault)
  }

  @Test def test_loaded_en_US(): Unit = {
    LocaleDB.installLocale(enUS)

    assertEquals(Locale.forLanguageTag("en_US").getLanguage, "en")
    assertEquals(Locale.forLanguageTag("en_US").getCountry, "US")
    assertEquals(Locale.forLanguageTag("en-US").getLanguage, "en")
    assertEquals(Locale.forLanguageTag("en-US").getCountry, "US")

    // Not installed locales return an empty locale as in the JVM
    assertEquals(Locale.forLanguageTag("en_CA").getCountry, "")
    assertEquals(Locale.forLanguageTag("en_CA").getLanguage, "")
    assertEquals(Locale.forLanguageTag("en_CA").getVariant, "")
  }

  // Taken from Intl.js
  val enUS =
    """
      |{
      |    "locale": "en-US",
      |    "date": {
      |        "ca": [
      |            "gregory",
      |            "buddhist",
      |            "chinese",
      |            "coptic",
      |            "dangi",
      |            "ethioaa",
      |            "ethiopic",
      |            "generic",
      |            "hebrew",
      |            "indian",
      |            "islamic",
      |            "islamicc",
      |            "japanese",
      |            "persian",
      |            "roc"
      |        ],
      |        "hourNo0": true,
      |        "hour12": true,
      |        "formats": {
      |            "short": "{1}, {0}",
      |            "medium": "{1}, {0}",
      |            "full": "{1} 'at' {0}",
      |            "long": "{1} 'at' {0}",
      |            "availableFormats": {
      |                "d": "d",
      |                "E": "ccc",
      |                "Ed": "d E",
      |                "Ehm": "E h:mm a",
      |                "EHm": "E HH:mm",
      |                "Ehms": "E h:mm:ss a",
      |                "EHms": "E HH:mm:ss",
      |                "Gy": "y G",
      |                "GyMMM": "MMM y G",
      |                "GyMMMd": "MMM d, y G",
      |                "GyMMMEd": "E, MMM d, y G",
      |                "h": "h a",
      |                "H": "HH",
      |                "hm": "h:mm a",
      |                "Hm": "HH:mm",
      |                "hms": "h:mm:ss a",
      |                "Hms": "HH:mm:ss",
      |                "hmsv": "h:mm:ss a v",
      |                "Hmsv": "HH:mm:ss v",
      |                "hmv": "h:mm a v",
      |                "Hmv": "HH:mm v",
      |                "M": "L",
      |                "Md": "M/d",
      |                "MEd": "E, M/d",
      |                "MMM": "LLL",
      |                "MMMd": "MMM d",
      |                "MMMEd": "E, MMM d",
      |                "MMMMd": "MMMM d",
      |                "ms": "mm:ss",
      |                "y": "y",
      |                "yM": "M/y",
      |                "yMd": "M/d/y",
      |                "yMEd": "E, M/d/y",
      |                "yMMM": "MMM y",
      |                "yMMMd": "MMM d, y",
      |                "yMMMEd": "E, MMM d, y",
      |                "yMMMM": "MMMM y",
      |                "yQQQ": "QQQ y",
      |                "yQQQQ": "QQQQ y"
      |            },
      |            "dateFormats": {
      |                "yMMMMEEEEd": "EEEE, MMMM d, y",
      |                "yMMMMd": "MMMM d, y",
      |                "yMMMd": "MMM d, y",
      |                "yMd": "M/d/yy"
      |            },
      |            "timeFormats": {
      |                "hmmsszzzz": "h:mm:ss a zzzz",
      |                "hmsz": "h:mm:ss a z",
      |                "hms": "h:mm:ss a",
      |                "hm": "h:mm a"
      |            }
      |        }
      |    },
      |    "number": {
      |        "nu": [
      |            "latn"
      |        ],
      |        "patterns": {
      |            "decimal": {
      |                "positivePattern": "{number}",
      |                "negativePattern": "{minusSign}{number}"
      |            },
      |            "currency": {
      |                "positivePattern": "{currency}{number}",
      |                "negativePattern": "{minusSign}{currency}{number}"
      |            },
      |            "percent": {
      |                "positivePattern": "{number}{percentSign}",
      |                "negativePattern": "{minusSign}{number}{percentSign}"
      |            }
      |        },
      |        "symbols": {
      |            "latn": {
      |                "decimal": ".",
      |                "group": ",",
      |                "nan": "NaN",
      |                "plusSign": "+",
      |                "minusSign": "-",
      |                "percentSign": "%",
      |                "infinity": "∞"
      |            }
      |        }
      |    }
      |}
    """.stripMargin
}
