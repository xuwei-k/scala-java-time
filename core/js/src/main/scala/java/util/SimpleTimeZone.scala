package java.util

class SimpleTimeZone(rawOffset: Int, var ID: String) extends TimeZone {
  override def setRawOffset(offsetMillis: Int) = ???

  override def getOffset(era: Int, year: Int, month: Int, day: Int, dayOfWeek: Int, milliseconds: Int) = ???

  override def useDaylightTime() = ???

  override def getRawOffset = rawOffset

  override def inDaylightTime(date: Date) = ???

  /* concrete methods */
  override def getID: String = ID
  override def setID(id: String): Unit = ID = id

}
