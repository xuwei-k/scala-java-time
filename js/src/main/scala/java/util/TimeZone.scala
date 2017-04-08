package java.util

import org.threeten.bp.ZoneId

import scala.util.Try

import scala.scalajs.js
import js.annotation._

@js.native
trait DateTimeFormatOptions extends js.Object {
  val timeZone: js.UndefOr[String]
}

@js.native
@JSGlobal("Intl.DateTimeFormat")
class DateTimeFormat() extends js.Object {
  def resolvedOptions(): DateTimeFormatOptions = js.native
}

object TimeZone {
  final val SHORT = 0
  final val LONG  = 1

  private var default: TimeZone = {
    // This is supported since EcmaScript 1
    def offsetInMillis: Int = {
      val browserDate = new scalajs.js.Date()
      browserDate.getTimezoneOffset() * 60 * 1000
    }

    def timeZone: String = {
      def browserTZ: Try[String] = {
        Try {
          val browserDate = new scalajs.js.Date()
          browserDate.toTimeString().split(' ')(1).takeWhile(e => e != ' ')
        }
      }
  
      Try {
        // First try with the intl API
        new DateTimeFormat().resolvedOptions().timeZone.getOrElse(browserTZ.getOrElse("UTC"))
      }.orElse {
        // If it fails try to parse it from the date string
        browserTZ
      }.getOrElse("UTC") // Fallback to UTC
    }

    new SimpleTimeZone(offsetInMillis, timeZone)
  }

  def getDefault: TimeZone = default
  def setDefault(timeZone: TimeZone): Unit = default = timeZone

  def getTimeZone(timeZone: String): TimeZone = ???
  def getTimeZone(zoneId: ZoneId): TimeZone   = ???

  def getAvailableIDs: Array[String] = ???
  def getAvailableIDs(offsetMillis: Int): Array[String] = ???

}

@SerialVersionUID(3581463369166924961L)
abstract class TimeZone extends Serializable with Cloneable {
  /* values */
  private var ID: String = null

  /* abstract methods */
  def getOffset(era: Int, year: Int, month: Int, day: Int, dayOfWeek: Int, milliseconds: Int): Int
  def getRawOffset: Int
  def inDaylightTime(date: Date): Boolean
  def setRawOffset(offsetMillis: Int): Unit
  def useDaylightTime: Boolean

  /* concrete methods */
  def getID: String = ID
  def setID(id: String): Unit = ID = id

  def hasSameRules(that: TimeZone): Boolean = that match {
    case null => false
    case _    => this.useDaylightTime == that.useDaylightTime && this.getRawOffset == that.getRawOffset
  }

  def getDisplayName(daylight: Boolean, style: Int, locale: Locale): String = {
    if (style != TimeZone.SHORT || style != TimeZone.LONG)
      throw new IllegalArgumentException(s"Illegal timezone style: $style")
    ???
  }

  def getDisplayName(daylight: Boolean, style: Int): String =
    getDisplayName(daylight, style, Locale.getDefault(Locale.Category.DISPLAY))

  def	getDisplayName(locale: Locale): String =
    getDisplayName(false, TimeZone.LONG, locale)

  def getDisplayName: String =
    getDisplayName(false, TimeZone.LONG, Locale.getDefault(Locale.Category.DISPLAY))

  def getDSTSavings: Int =
    if (useDaylightTime) 3600000
    else 0

  def getOffset(date: Long) = ???

  def observesDaylightTime: Boolean = ???

  def toZoneId: ZoneId = ???

  override def clone: AnyRef = {
    val cloned = super.clone.asInstanceOf[TimeZone]
    cloned.ID = this.ID
    cloned
  }
}
