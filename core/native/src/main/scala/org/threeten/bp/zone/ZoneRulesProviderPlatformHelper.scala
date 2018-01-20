package org.threeten.bp.zone

import java.util.Iterator

private[zone] object ZoneRulesProviderPlatformHelper {

  def loadAdditionalZoneRulesProviders: Iterator[ZoneRulesProvider] =
    java.util.Collections.emptyIterator()

}
