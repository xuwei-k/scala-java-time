package scalajs.testsuite.utils

/**
  * Equivalent to scala-js-java-time Platform to let test know where they are running
  * This will replaced by scala-js-java-time version once merged
  */
object Platform {
  final val executingInJVM = !System.getProperty("java.vm.name").startsWith("Scala.js")
}
