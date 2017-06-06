---
layout: home
title:  "scala-java-time"
section: "home"
---

## Scala Java-Time

[![Build Status](https://travis-ci.org/cquiroz/scala-java-time.svg?branch=master)](https://travis-ci.org/cquiroz/scala-java-time)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.cquiroz/scala-java-time_2.11.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.cquiroz/scala-java-time_2.11)
[![Scala.js](http://scala-js.org/assets/badges/scalajs-0.6.8.svg)](http://scala-js.org)

This project provides an implementation of the `java.time` package, a date and time library that was added in Java 8.
The implementation is based on the original BSD-licensed reference implementation (before it was contributed to OpenJDK).

#### Example

```tut:book
// On scala.js you can pick either java.time or org.threeten.bp package to import
//import java.time._
import org.threeten.bp._

// always returns 2009-02-13T23:31:30
val fixedClock = Clock.fixed(Instant.ofEpochSecond(1234567890L), ZoneOffset.ofHours(0))
val date = LocalDateTime.now(fixedClock)

date.getMonth      == Month.FEBRUARY
date.getDayOfWeek  == DayOfWeek.FRIDAY
date.getDayOfMonth == 13
date.getHour       == 23

val tomorrow = date.plusDays(1)

val duration = Duration.between(date, tomorrow)
duration.toMinutes == 1440L

val period = Period.between(date.toLocalDate, tomorrow.toLocalDate)
period.get(temporal.ChronoUnit.DAYS) == 1L

val date1 = LocalDate.of(2001, 1, 31)
date1.plusMonths(1) == LocalDate.of(2001, 2, 28)
val date2 = date1.`with`(temporal.TemporalAdjusters.next(DayOfWeek.SUNDAY))
date2 == LocalDate.of(2001, 2, 4)

val offsetTime = OffsetTime.of(date.toLocalTime, ZoneOffset.ofHours(1))
offsetTime.isBefore(OffsetTime.of(date.toLocalTime, ZoneOffset.ofHours(0))) == true

val format1 = format.DateTimeFormatter.ofPattern("MMMM MM d HH mm ss EE EEEE yyyy G", java.util.Locale.GERMAN)
date.format(format1) == "Februar 02 13 23 31 30 Fr. Freitag 2009 n. Chr."

chrono.JapaneseDate.now(fixedClock).toString     == "Japanese Heisei 21-02-13"
chrono.ThaiBuddhistDate.now(fixedClock).toString == "ThaiBuddhist BE 2552-02-13"
chrono.MinguoDate.now(fixedClock).toString       == "Minguo ROC 98-02-13"
```

#### Usage

The *scala-java-time* library is currently available for Scala (JVM, version 8 and later) and Scala.js (JavaScript).
Both Scala 2.11 and Scala 2.12 (2.12.0-M8 and later) are supported.

To get started with SBT, add one (or both) of these dependencies:

- `libraryDependencies += "io.github.cquiroz" %%  "scala-java-time" % "2.0.0-M12"` (for Scala)
- `libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-M12"` (for Scala.js, [Scala.js plugin](http://www.scala-js.org/tutorial/basic/#sbt-setup) required)

#### Building
This project builds using sbt.
Run `sbt scalajavatimeCrossJVM/test` to run the test suite on the JVM and
`sbt scalajavatimeCrossJS/test` to run the test suite in JavaScript.

#### Status

All parts of this library work perfectly fine with Scala.js in the browser.

#### Contributing

We welcome all contributions, including ideas, suggestions, bug reports, bug fixes and code!
We are especially interested in contributions that tackle the following issues:

* Modularize the tzdb database
* Find ways to reduce the size of the library

Have a look at the [issues](https://github.com/cquiroz/scala-java-time/issues) or [issues](https://github.com/soc/scala-java-time/issues) to find something to work on! Let us know if you need help!

#### Plans

##### 2.0

The current version is published containing the code in both packages: `org.threeten.bp` and `java.time`.

A stable release of 2.0 will be published after a (hopefully) short RC phase.

#### Time-zone data (JVM)
The time-zone database is stored as a pre-compiled dat file that is included in the built jar.
The version of the time-zone data used is stored within the dat file (near the start).
Updating the time-zone database involves using the `TzdbZoneRulesCompiler` class
and re-compiling the jar file.
Pull requests with later versions of the dat file will be accepted.

#### FAQs

##### Is this project derived from OpenJDK?

No. This project is derived from the Reference Implementation previously hosted on GitHub.
That project had a BSD license, which has been preserved here.
Thus, this project is a fork of the original code before entry to OpenJDK.

##### What is the relation to [this](https://github.com/soc/scala-java-time/) project

This is a fork from the original [project](https://github.com/soc/scala-java-time/) aim to complete the API to work on Scala.js

##### Are there are differences with the Java Time API?

The project aims to be an exact port of the java time API to scala.
The only differences are classes not on the official java API but still present as private, e.g. `DateTimeTextProvider`
in the format package that have been moved to the `internal` package to ease compatibility across versions
