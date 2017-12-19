package org.threeten.bp.zone

import java.util.Iterator

import scala.collection.JavaConverters._

import scala.scalajs.reflect._

private[zone] object ZoneRulesProviderPlatformHelper {
  // Load via reflection the tzdb
  // find the package name dynamically to support both org.threeten.bp and java.time packages
  val packageName = this.getClass.getName.split("\\.").init.mkString(".")
  val optClassData = Reflect.lookupInstantiatableClass(s"$packageName.TzdbZoneRulesProvider")
  val providers = optClassData.fold(List[ZoneRulesProvider](new DefaultTzdbZoneRulesProvider())){ c =>
    val instance = c.newInstance().asInstanceOf[ZoneRulesProvider]
    List[ZoneRulesProvider](instance)
  }

  def loadAdditionalZoneRulesProviders: Iterator[ZoneRulesProvider] =
    providers.asJava.iterator()

}
