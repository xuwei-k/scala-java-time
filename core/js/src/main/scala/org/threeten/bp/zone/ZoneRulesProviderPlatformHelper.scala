package org.threeten.bp.zone

import java.util.Iterator

import scala.collection.JavaConverters._

import scala.scalajs.reflect._

private[zone] object ZoneRulesProviderPlatformHelper {
  println(this.getClass.getName)
  val optClassData = Reflect.lookupInstantiatableClass("org.threeten.bp.zone.TzdbZoneRulesProvider")
  val providers = optClassData.fold(List[ZoneRulesProvider](new DefaultTzdbZoneRulesProvider())){ c =>
    val instance = c.newInstance().asInstanceOf[ZoneRulesProvider]
    List[ZoneRulesProvider](instance)
  }
  println(providers)
  def loadAdditionalZoneRulesProviders: Iterator[ZoneRulesProvider] = {
    println("Load")
    providers.asJava.iterator()
  }
}
