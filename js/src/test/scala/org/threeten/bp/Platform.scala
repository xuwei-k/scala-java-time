package org.threeten.bp

object Platform {
  type NPE = scala.scalajs.js.JavaScriptException

  /** Returns `true` if and only if the code is executing on a JVM.
   *  Note: Returns `false` when executing on any JS VM.
   */
  final val executingInJVM = true

  def setupLocales(): Unit = {
    if (Platform.executingInJVM) {
      import locales.LocaleRegistry
      import locales.cldr.data.pt_BR

      LocaleRegistry.installLocale(pt_BR)
    }
  }

}
