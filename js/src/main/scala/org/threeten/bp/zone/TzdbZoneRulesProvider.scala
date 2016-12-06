package org.threeten.bp.zone
import java.util

import org.threeten.bp.DateTimeException

final class TzdbZoneRulesProvider extends ZoneRulesProvider {
  import tzdb.tzdb._
  import scala.collection.JavaConverters._

  /** SPI method to get the available zone IDs.
    *
    * This obtains the IDs that this {@code ZoneRulesProvider} provides.
    * A provider should provide data for at least one region.
    *
    * The returned regions remain available and valid for the lifetime of the application.
    * A dynamic provider may increase the set of regions as more data becomes available.
    *
    * @return the unmodifiable set of region IDs being provided, not null
    * @throws ZoneRulesException if a problem occurs while providing the IDs
    */
  override protected def provideZoneIds: util.Set[String] = new java.util.HashSet((allZones.keySet ++ zoneLinks.keySet).asJava)

  /** SPI method to get the rules for the zone ID.
    *
    * This loads the rules for the region and version specified.
    * The version may be null to indicate the "latest" version.
    *
    * @param regionId the time-zone region ID, not null
    * @return the rules, not null
    * @throws java.time.DateTimeException if rules cannot be obtained
    */
  override protected def provideRules(regionId: String, forCaching: Boolean): ZoneRules = {
    val actualRegion = zoneLinks.getOrElse(regionId, regionId)
    allZones.get(actualRegion).fold(throw new DateTimeException(s"TimeZone Region $actualRegion unknown"))(identity)
  }

  /** SPI method to get the history of rules for the zone ID.
    *
    * This returns a map of historical rules keyed by a version string.
    * The exact meaning and format of the version is provider specific.
    * The version must follow lexicographical order, thus the returned map will
    * be order from the oldest known rules to the newest available rules.
    * The default 'TZDB' group uses version numbering consisting of the year
    * followed by a letter, such as '2009e' or '2012f'.
    *
    * Implementations must provide a result for each valid zone ID, however
    * they do not have to provide a history of rules.
    * Thus the map will always contain one element, and will only contain more
    * than one element if historical rule information is available.
    *
    * The returned versions remain available and valid for the lifetime of the application.
    * A dynamic provider may increase the set of versions as more data becomes available.
    *
    * @param zoneId the zone region ID as used by { @code ZoneId}, not null
    * @return a modifiable copy of the history of the rules for the ID, sorted
    *         from oldest to newest, not null
    * @throws ZoneRulesException if history cannot be obtained for the zone ID
    */
  override protected def provideVersions(zoneId: String): util.NavigableMap[String, ZoneRules] = {
    val actualRegion = zoneLinks.getOrElse(zoneId, zoneId)
    allZones.get(actualRegion).fold(throw new DateTimeException(s"TimeZone Region $actualRegion unknown")) {
      x =>
        val r = new ZoneMap[String, ZoneRules]
        r.put("2016f", x)
        r
    }
  }
}
