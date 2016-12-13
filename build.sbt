import sbt._
import io.github.soc.testng.{TestNGPlugin, TestNGScalaJSPlugin}
import TZDBTasks._

enablePlugins(MicrositesPlugin)

val scalaVer = "2.11.8"
val crossScalaVer = Seq(scalaVer, "2.10.6", "2.12.0")

lazy val downloadFromZip: TaskKey[Unit] =
  taskKey[Unit]("Download the tzdb tarball and extract it")

lazy val commonSettings = Seq(
  name         := "scala-java-time",
  description  := "java.time API implementation in Scala and Scala.js",
  version      := "2.0.0-M7",
  organization := "io.github.cquiroz",
  homepage     := Some(url("https://github.com/cquiroz/scala-java-time")),
  licenses     := Seq("BSD 3-Clause License" -> url("https://opensource.org/licenses/BSD-3-Clause")),

  scalaVersion       := scalaVer,
  crossScalaVersions := crossScalaVer,
  autoAPIMappings    := true,

  scalacOptions in Compile ++= Seq(
    "-deprecation",
    "-feature",
    // Enable when documentation does not produce warnings
    //"-Xfatal-warnings",
    "-encoding", "UTF-8"
  ),

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
  pomIncludeRepository := { _ => false },
  resolvers += Resolver.sonatypeRepo("snapshots"),
  libraryDependencies ++= Seq(
    "org.scalatest" %%% "scalatest" % "3.0.1" % "test"
  )
)

lazy val root = project.in(file("."))
  .aggregate(scalajavatimeJVM, scalajavatimeJS)
  .settings(commonSettings: _*)
  .settings(
    name                 := "scalajavatime",
    // No, SBT, we don't want any artifacts for root.
    // No, not even an empty jar.
    publish              := {},
    publishLocal         := {},
    Keys.`package`       := file(""))

lazy val tzDbSettings = Seq(
  downloadFromZip := {
    val tzdbDir = ((resourceDirectory in Compile) / "tzdb").value
    val tzdbTarball = ((resourceDirectory in Compile) / "tzdb.tar.gz").value
    if (java.nio.file.Files.notExists(tzdbDir.toPath)) {
      println(s"tzdb data missing. downloading latest...")
      IO.download(
        new URL(s"http://www.iana.org/time-zones/repository/releases/tzdata2016i.tar.gz"),
        tzdbTarball)
      Unpack.gunzipTar(tzdbTarball, tzdbDir)
      tzdbTarball.delete()
    } else {
      println("tzdb files already available")
    }
  },
  compile in Compile := (compile in Compile).dependsOn(downloadFromZip).value,
  sourceGenerators in Compile += Def.task {
    generateTZDataSources((sourceManaged in Compile).value,
      ((resourceDirectory in Compile) / "tzdb").value)
  }.taskValue
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
      .replaceAll("import zonedb.threeten", "import zonedb.java")
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
    javaOptions in Test ++= Seq("-Duser.language=en", "-Duser.country=US", "-Djava.locale.providers=CLDR"),
    TestNGPlugin.testNGSuites := Seq(((resourceDirectory in Test).value / "testng.xml").absolutePath)
  ).jsSettings(
    tzDbSettings: _*
  ).jsSettings(
    sourceGenerators in Compile += Def.task {
        val srcDirs = (sourceDirectories in Compile).value
        val destinationDir = (sourceManaged in Compile).value
        copyAndReplace(srcDirs, destinationDir)
      }.taskValue,
    parallelExecution in Test := false,
    libraryDependencies ++= Seq(
      "com.github.cquiroz" %%% "scala-java-locales" % "0.3.1-cldr30"
    )
  )

lazy val scalajavatimeJVM = scalajavatime.jvm
lazy val scalajavatimeJS  = scalajavatime.js

lazy val docs = project.in(file("docs")).dependsOn(scalajavatimeJVM, scalajavatimeJS)
  .settings(commonSettings)
  .settings(name := "docs")
  .enablePlugins(MicrositesPlugin)
  .settings(
    micrositeName             := "scala-java-time",
    micrositeAuthor           := "Carlos Quiroz",
    micrositeGithubOwner      := "cquiroz",
    micrositeGithubRepo       := "scala-java-time",
    micrositeBaseUrl          := "/scala-java-time",
    //micrositeDocumentationUrl := "/scala-java-time/docs/",
    micrositeHighlightTheme   := "color-brewer"
  )

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
      <name>Matias Irland</name>
      <url>https://github.com/matir91</url>
    </contributor>
    <contributor>
      <name>Pap Lorinc</name>
      <url>https://github.com/paplorinc</url>
    </contributor>
    <contributor>
      <name>Philippe Marschall</name>
      <url>https://github.com/marschall</url>
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
