import org.scalajs.sbtplugin.ScalaJSJUnitPlugin
import sbt._

parallelExecution in ThisBuild := false

name := "threetenbp"
organization := "org.threeten"
version := "1.3.3-SNAPSHOT"
isSnapshot := true

lazy val commonSettings = Seq(
  scalacOptions ++= Seq("-Xexperimental"),
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.11.8"),
  publishArtifact in (Compile, packageDoc) := false,
  publishArtifact in packageDoc := false,
  publish := {},
  publishLocal := {}
)

lazy val threetenbpCross = crossProject.crossType(CrossType.Full).in(file("."))
  .jsConfigure(_.enablePlugins(ScalaJSJUnitPlugin))

lazy val threetenbp = threetenbpCross.jvm
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % "3.0.0-M15" % "test",
      "com.novocode" % "junit-interface" % "0.9" % "test",
      "org.testng" % "testng" % "6.9.10" % "test"
    )
  )

lazy val threetenbpJS = threetenbpCross.js
  .settings(commonSettings: _*)
  .settings(
    scalaJSUseRhino := false,
    resolvers +=
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    testOptions +=
      Tests.Argument(TestFramework("com.novocode.junit.JUnitFramework"),
        "-v", "-a"),
    libraryDependencies ++= Seq(
      "com.github.cquiroz" %%% "scalajs-locales" % "0.1.0-SNAPSHOT",
      "org.scalatest" %%% "scalatest" % "3.0.0-M15" % "test",
      "com.novocode" % "junit-interface" % "0.9" % "test")
  )

