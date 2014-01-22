	import sbt._
import Keys._
object ApplicationClasspath extends Plugin {
	val propertyName = "sbt.application.class.path"
	val setupClassPath = TaskKey[Unit]("setup-classpath", s"""Put this application's classpath in the "$propertyName" system property.""")
	val newSettings = Seq(
		setupClassPath <<= (dependencyClasspath in Compile) map { cp =>
			val cpStr = cp map { case Attributed(str) => str} mkString(System.getProperty("path.separator"))
			println(s"ClassPath value: $cpStr")
			System.setProperty(propertyName, cpStr)
		},
		run in Compile <<= (run in Compile).dependsOn(setupClassPath)
	)
}
