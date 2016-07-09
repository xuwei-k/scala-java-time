import org.scalajs.sbtplugin.ScalaJSJUnitPlugin
import sbt._

parallelExecution in ThisBuild := false

name := "scala-java-time"
organization := "org.threeten"
version := "2.0-M1-SNAPSHOT"
isSnapshot := true

val scalaVer = "2.11.8"
val crossScalaVer = Seq(scalaVer)

lazy val commonSettings = Seq(
  scalacOptions ++= Seq("-Xexperimental"),
  scalaVersion := scalaVer,
  crossScalaVersions := crossScalaVer,
  publishArtifact in (Compile, packageDoc) := false,
  publishArtifact in packageDoc := false
)

lazy val threetenbpRoot = project.in(file("."))
  .aggregate(threetenbpJVM, threetenbpJS)
  .settings(
    scalaVersion := scalaVer,
    publish := {},
    publishLocal := {},
    crossScalaVersions := crossScalaVer
  )


lazy val threetenbpCross = crossProject.crossType(CrossType.Full).in(file("."))
  .jsConfigure(_.enablePlugins(ScalaJSJUnitPlugin))
  .settings(commonSettings: _*)
  .settings(
    testOptions += Tests.Argument(TestFramework("com.novocode.junit.JUnitFramework"), "-v", "-a")
  ).jvmSettings(
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest"       % "3.0.0-RC4" % "test",
      "com.novocode"  %   "junit-interface" % "0.9"       % "test",
      "org.testng"    %   "testng"          % "6.9.10"    % "test"
    )
  ).jsSettings(
    libraryDependencies += "com.github.cquiroz" %%% "scala-java-locales" % "0.1.0+29"
  )

lazy val threetenbpJVM = threetenbpCross.jvm.settings(commonSettings: _*)

lazy val threetenbpJS  = threetenbpCross.js.settings(commonSettings: _*)
