import java.io.File

import kuyfi.TZDBCodeGenerator

object TZDBTasks {

  def generateTZDataSources(base: File, data: File): Seq[File] = {
    val dataPath = base.toPath.resolve("tzdb")
    val path = dataPath.resolve(s"tzdb.scala")

    path.getParent.toFile.mkdirs()
    val tzdbFile = path.toFile
    TZDBCodeGenerator.exportAll(data, tzdbFile).unsafePerformIO()

    List(tzdbFile)
  }
}
