package org.threeten.bp.zone

import java.util
import java.util.{Collection => JCollection, Map => JMap, Set => JSet}
import java.util.AbstractMap
import java.util.AbstractMap.SimpleEntry
import java.util.Comparator

import scala.reflect.ClassTag
import scala.collection.JavaConverters._

// TreeMap is not available in Scala.js however it is needed for Time Zone support
// This is a simple implementation of NavigableMap
private[zone] class ZoneMap[K: ClassTag, V](implicit ordering: Ordering[K]) extends AbstractMap[K, V] with java.util.NavigableMap[K, V] {
  // Use an immutable TreeMap and replace it completely on updates
  private var map = scala.collection.immutable.TreeMap[K, V]()

  override def descendingMap() = ???

  override def firstEntry(): java.util.Map.Entry[K, V] = {
    val fk = firstKey()
    map.get(fk).map(new SimpleEntry(firstKey(), _)).getOrElse(null.asInstanceOf[java.util.Map.Entry[K, V]])
  }

  override def navigableKeySet() = ???

  override def subMap(fromKey: K, fromInclusive: Boolean, toKey: K, toInclusive: Boolean) = ???

  override def subMap(fromKey: K, toKey: K) = ???

  override def headMap(toKey: K, inclusive: Boolean) = ???

  override def headMap(toKey: K) = ???

  override def ceilingKey(key: K) = ???

  override def higherEntry(key: K) = ???

  override def ceilingEntry(key: K) = ???

  override def pollFirstEntry() = ???

  override def floorKey(key: K) = ???

  override def floorEntry(key: K) = ???

  override def lowerEntry(key: K) = ???

  override def pollLastEntry() = ???

  override def descendingKeySet() = ???

  override def lastEntry(): java.util.Map.Entry[K, V] = {
      val fk = firstKey()
      map.get(fk).map(new SimpleEntry(lastKey(), _)).getOrElse(null.asInstanceOf[java.util.Map.Entry[K, V]])
    }

  override def tailMap(fromKey: K, inclusive: Boolean) = ???

  override def tailMap(fromKey: K) = ???

  override def lowerKey(key: K) = ???

  override def higherKey(key: K) = ???

  override def firstKey(): K = map.firstKey

  override def comparator(): Comparator[K] = map.ordering

  override def lastKey(): K = map.lastKey

  override def values(): JCollection[V] = map.values.asJavaCollection

  override def put(key: K, value: V): V = {
    map = map + ((key, value))
    value
  }

  override def clear(): Unit =
    map = scala.collection.immutable.TreeMap[K, V]()

  override def entrySet(): JSet[JMap.Entry[K, V]] = {
    map.map {
      case (k, v) => new SimpleEntry[K, V](k, v): JMap.Entry[K, V]
    }.toSet.asJava
  }
}
