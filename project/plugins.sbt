import sbt.Keys._

resolvers += Resolver.sonatypeRepo("public")

addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC13")

addSbtPlugin("com.47deg"  % "sbt-microsites" % "0.7.10")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.0")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")

addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.3.0")

addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.3.6")

val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.21")

addSbtPlugin("org.portable-scala" % "sbt-crossproject" % "0.3.0")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)

addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.3.0")

addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.5.7")

// Incompatible with 2.12.0-M5
// addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")
// addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.1.0")

libraryDependencies ++= Seq(
  "io.github.cquiroz" %% "kuyfi" % "0.6.1",
  "org.apache.commons" % "commons-compress" % "1.12",
  "com.eed3si9n" %% "gigahorse-okhttp" % "0.3.0"
)
