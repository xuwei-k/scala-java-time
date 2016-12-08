import sbt._

import io.github.soc.testng.{TestNGPlugin, TestNGScalaJSPlugin}

enablePlugins(MicrositesPlugin)

val scalaVer = "2.11.8"
val crossScalaVer = Seq(scalaVer, "2.10.6", "2.12.0")

lazy val commonSettings = Seq(
  name         := "scala-java-time",
  description  := "java.time API implementation in Scala and Scala.js",
  version      := "2.0.0-M5",
  organization := "com.github.cquiroz",
  homepage     := Some(url("https://github.com/cquiroz/scala-java-time")),
  licenses     := Seq("BSD 3-Clause License" -> url("https://opensource.org/licenses/BSD-3-Clause")),

  scalaVersion       := scalaVer,
  crossScalaVersions := crossScalaVer,

  publishArtifact in Test := false,
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
  pomExtra := pomData,
  pomIncludeRepository := { _ => false }
)

lazy val root = project.in(file("."))
  .aggregate(scalajavatimeJVM, scalajavatimeJS)
  .enablePlugins(MicrositesPlugin)
  .settings(
    scalaVersion := scalaVer,
    crossScalaVersions := crossScalaVer,
    // No, SBT, we don't want any artifacts for root.
    // No, not even an empty jar.
    // Invoking Cthulhu:
    packageBin in Global := file(""),
    packagedArtifacts    := Map(),
    publish              := {},
    publishLocal         := {},
    publishArtifact      := false,
    Keys.`package`       := file(""))

lazy val scalajavatime = crossProject.crossType(CrossType.Full).in(file("."))
  .jvmConfigure(_.enablePlugins(TestNGPlugin))
  .jsConfigure(_.enablePlugins(TestNGScalaJSPlugin))
  .settings(commonSettings: _*)
  .jvmSettings(
    micrositeExtraMdFiles := Map(file("README.md") -> "index.md"),

    resolvers += Resolver.sbtPluginRepo("releases"),
    // Fork the JVM test to ensure that the custom flags are set
    fork in Test := true,
    baseDirectory in Test := baseDirectory.value.getParentFile,
    // Use CLDR provider for locales
    // https://docs.oracle.com/javase/8/docs/technotes/guides/intl/enhancements.8.html#cldr
    javaOptions in Test ++= Seq("-Djava.locale.providers=CLDR"),
    TestNGPlugin.testNGSuites := Seq(((resourceDirectory in Test).value / "testng.xml").absolutePath)
  ).jsSettings(
    libraryDependencies ++= Seq(
      "com.github.cquiroz" %%% "scala-java-locales" % "0.4.0-cldr30"
    )
  )

/**
  * Copy source files and translate them to the java.time package
  */
def copyAndReplace(srcDirs: Seq[File], destinationDir: File): Seq[File] = {
  // Copy a directory and return the list of files
  def copyDirectory(source: File, target: File, overwrite: Boolean = false, preserveLastModified: Boolean = false): Set[File] =
    IO.copy(PathFinder(source).***.pair(Path.rebase(source, target)), overwrite, preserveLastModified)

  val onlyScalaDirs = srcDirs.filter(_.getName.endsWith("scala"))
  // Copy the source files from the base project, exclude classes on java.util and dirs
  val generatedFiles: List[java.io.File] = onlyScalaDirs.foldLeft(Set.empty[File]) { (files, sourceDir) =>
    files ++ copyDirectory(sourceDir, destinationDir, overwrite = true)
  }.filterNot(_.isDirectory).filterNot(_.getParentFile.getName == "util").toList

  // These replacements will in practice rename all the classes from
  // org.threeten to java.time
  def replacements(line: String): String = {
    line
      .replaceAll("package org.threeten$", "package java")
      .replaceAll("package object bp", "package object time")
      .replaceAll("package org.threeten.bp", "package java.time")
      .replaceAll("import org.threeten.bp", "import java.time")
      .replaceAll("private\\s*\\[bp\\]", "private[time]")
  }

  // Visit each file and read the content replacing key strings
  generatedFiles.foreach { f =>
    val replacedLines = IO.readLines(f).map(replacements)
    IO.writeLines(f, replacedLines)
  }
  generatedFiles
}

lazy val scalajavatime = crossProject.crossType(CrossType.Full).in(file("."))
  .jvmConfigure(_.enablePlugins(TestNGPlugin))
  .jsConfigure(_.enablePlugins(TestNGScalaJSPlugin))
  .settings(commonSettings: _*)
  .jvmSettings(
    sourceGenerators in Compile += Def.task {
        val srcDirs = (sourceDirectories in Compile).value
        val destinationDir = (sourceManaged in Compile).value
        copyAndReplace(srcDirs, destinationDir)
      }.taskValue,
    resolvers += Resolver.sbtPluginRepo("releases"),
    // Fork the JVM test to ensure that the custom flags are set
    fork in Test := true,
    baseDirectory in Test := baseDirectory.value.getParentFile,
    // Use CLDR provider for locales
    // https://docs.oracle.com/javase/8/docs/technotes/guides/intl/enhancements.8.html#cldr
    javaOptions in Test ++= Seq("-Djava.locale.providers=CLDR"),
    TestNGPlugin.testNGSuites := Seq(((resourceDirectory in Test).value / "testng.xml").absolutePath)
  ).jsSettings(
    sourceGenerators in Compile += Def.task {
        val srcDirs = (sourceDirectories in Compile).value
        val destinationDir = (sourceManaged in Compile).value
        copyAndReplace(srcDirs, destinationDir)
      }.taskValue,
    libraryDependencies ++= Seq(
      "com.github.cquiroz" %%% "scala-java-locales" % "0.4.0-cldr30"
    )
  )

lazy val scalajavatimeJVM = scalajavatime.jvm
lazy val scalajavatimeJS  = scalajavatime.js

lazy val pomData =
  <scm>
    <url>git@github.com:cquiroz/scala-java-time.git</url>
    <connection>scm:git:git@github.com:cquiroz/scala-java-time.git</connection>
  </scm>
  <developers>
    <developer>
      <id>cquiroz</id>
      <name>Carlos Quiroz</name>
      <url>https://github.com/cquiroz</url>
      <roles>
        <role>Project Lead (current Scala version)</role>
      </roles>
    </developer>
    <developer>
      <id>soc</id>
      <name>Simon Ochsenreither</name>
      <url>https://github.com/soc</url>
      <roles>
        <role>Project Lead (original Scala version)</role>
      </roles>
    </developer>
    <developer>
      <id>jodastephen</id>
      <name>Stephen Colebourne</name>
      <url>https://github.com/jodastephen</url>
      <roles>
        <role>Project Lead (original Java implementation)</role>
      </roles>
   </developer>
  </developers>
  <contributors>
    <contributor>
      <name>Carlos Quiroz</name>
      <url>https://github.com/cquiroz/</url>
    </contributor>
    <contributor>
      <name>Javier Fernandez-Ivern</name>
      <url>https://github.com/ivern</url>
    </contributor>
    <contributor>
      <name>Martin Baker</name>
      <url>https://github.com/kemokid</url>
    </contributor>
    <contributor>
      <name>Keith Harris</name>
      <url>https://github.com/keithharris</url>
    </contributor>
    <contributor>
      <name>Ludovic Hochet</name>
      <url>https://github.com/lhochet</url>
    </contributor>
    <contributor>
      <name>Pap Lorinc</name>
      <url>https://github.com/paplorinc</url>
    </contributor>
    <contributor>
      <name>Michael Nascimento Santos</name>
      <url>https://github.com/sjmisterm</url>
    </contributor>
    <contributor>
      <name>Roger Riggs</name>
      <url>https://github.com/RogerRiggs</url>
    </contributor>
    <contributor>
      <name>Siebe Schaap</name>
      <url>https://github.com/sschaap</url>
    </contributor>
    <contributor>
      <name>Sherman Shen</name>
    </contributor>
  </contributors>
