import sbt.Keys._

resolvers += Resolver.sonatypeRepo("public")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.13")
addSbtPlugin("io.github.soc" % "sbt-testng" % "4.0.0-M3")
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-M15")

addSbtPlugin("com.fortysevendeg"  % "sbt-microsites" % "0.3.3")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "1.1")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

// Incompatible with 2.12.0-M5
// addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")
// addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.1.0")

libraryDependencies ++= Seq(
  "io.github.cquiroz" %% "kuyfi" % "0.4.0-SNAPSHOT",
  "org.apache.commons" % "commons-compress" % "1.12"
)
