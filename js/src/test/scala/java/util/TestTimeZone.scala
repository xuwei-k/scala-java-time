package java.util

import org.scalatest.FunSuite
import org.threeten.bp.ZoneId

class TestTimeZone extends FunSuite {

  val sampleTimeZones = Seq("UTC", "GMT", "Europe/Madrid", "Australia/Sydney")
  val sampleOffsets = Seq(0L, 0L, 3600000L, 36000000L)
  val sampleDisplayNames = Seq(
    "Coordinated Universal Time",
    "Greenwich Mean Time",
    "Central European Time",
    "Australian Eastern Standard Time (New South Wales)"
  )

  test("default") {
    TimeZone.getDefault.getID === "GMT"
  }

  test("getTimeZone") {
    val zonesWithOffsets = sampleTimeZones.zip(sampleOffsets)

    for ((tzId, offset) <- zonesWithOffsets) {
      val zoneId = ZoneId.of(tzId)
      val tz = TimeZone.getTimeZone(tzId)

      assert(tz.getID === tzId)
      assert(tz.getRawOffset === offset)
      //assert(tz.toZoneId === zoneId)
    }
  }

  test("availableIDs") {
    val availableIds = TimeZone.getAvailableIDs
    for (tzId <- sampleTimeZones) {
      assert(availableIds.contains(tzId))
    }
  }

  test("availableIDs by offset") {
    val zonesByOffsets = sampleOffsets.zip(sampleTimeZones).groupBy(_._1).mapValues(_.map(_._2))
    for ((offset, ids) <- zonesByOffsets) {
      val tzs = TimeZone.getAvailableIDs(offset.toInt)
      assert(ids.forall(tzs.contains))
    }
  }

  test("getDisplayName") {
    val zonesWithNames = sampleTimeZones.zip(sampleDisplayNames)
    for ((id, name) <- zonesWithNames) {
      val tz = TimeZone.getTimeZone(id)
      assert(tz.getDisplayName(Locale.ENGLISH) === name)
    }
  }

}
