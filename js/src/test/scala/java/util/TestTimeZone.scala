package java.util

import org.scalatest.FunSuite

class TestTimeZone extends FunSuite {
  test("default") {
    TimeZone.getDefault.getID === "GMT"
  }
}
