package org.threeten.bp

object Platform {
  type NPE = NullPointerException

  /** Returns `true` if and only if the code is executing on a JVM.
   *  Note: Returns `false` when executing on any JS VM.
   */
  final val executingInJVM = false

  def setupLocales(): Unit = ()
}
