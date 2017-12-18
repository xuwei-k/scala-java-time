package org.threeten.bp.zone

import java.util.Iterator

import scala.collection.JavaConverters._

private[zone] object ZoneRulesProviderPlatformHelper {
  def loadAdditionalZoneRulesProviders: Iterator[ZoneRulesProvider] = List(new TzdbZoneRulesProvider(): ZoneRulesProvider).asJava.iterator()
}
