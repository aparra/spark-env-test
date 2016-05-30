import sbt.Keys._
import sbtassembly.AssemblyPlugin.autoImport._

object Assembly {
  def settings(includeScala: Boolean = false) = Seq(
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = includeScala),
    assemblyJarName in assembly := s"${name.value}-assembly.jar",
    test in assembly := {}
  )
}