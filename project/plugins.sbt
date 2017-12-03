import sbt.Keys._

resolvers += Resolver.sonatypeRepo("public")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.21")

addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC13")

addSbtPlugin("com.47deg"  % "sbt-microsites" % "0.7.10")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.0")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")

// Incompatible with 2.12.0-M5
// addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")
// addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.1.0")

libraryDependencies ++= Seq(
  "io.github.cquiroz" %% "kuyfi" % "0.6.1",
  "org.apache.commons" % "commons-compress" % "1.12",
  "com.eed3si9n" %% "gigahorse-okhttp" % "0.3.0"
)
