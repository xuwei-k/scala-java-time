import java.io.File

import kuyfi.TZDBCodeGenerator

object TZDBTasks {

  def generateTZDataSources(base: File, data: File): Seq[File] = {
    val dataPath = base.toPath.resolve("tzdb")
    val paths = List(("zonedb.threeten", "org.threeten.bp", dataPath.resolve(s"tzdb_threeten.scala")), ("zonedb.java", "java.time", dataPath.resolve(s"tzdb_java.scala")))

    paths.foreach(_._3.getParent.toFile.mkdirs())
    paths.foreach(p => TZDBCodeGenerator.exportAll(data, p._3.toFile, p._1, p._2).unsafePerformIO())

    paths.map(_._3.toFile)
  }
}
