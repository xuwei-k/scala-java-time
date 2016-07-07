import org.scalajs.sbtplugin.cross.CrossProject

parallelExecution in ThisBuild := false

name := "threetenbp"
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
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % "3.0.0-RC4" % "test",
      "junit" % "junit" % "4.12" % "test",
      "org.testng" % "testng" % "6.9.10" % "test"
    )
  )

lazy val threetenbpJVM = threetenbpCross.jvm
  .settings(commonSettings: _*)
lazy val threetenbpJS = threetenbpCross.js
  .settings(commonSettings: _*)
