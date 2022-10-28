import play.sbt.PlayImport._
import sbt._

object Dependencies {

  val scalaVersion = "2.13.10"

  // innFactory Utils
  val scalaUtil = "de.innfactory.scala-utils" %% "scala-utils" % "2.0.1"
  val smithy4play = "de.innfactory" %% "smithy4play" % "0.3.2"

  // Test
  val scalatestPlus = "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

  lazy val list = Seq(
    scalaUtil,
    guice,
    scalatestPlus,
    smithy4play,
  )
}
