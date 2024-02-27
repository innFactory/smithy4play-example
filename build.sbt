ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .enablePlugins(Smithy4sCodegenPlugin, PlayScala)
  .aggregate(api)
  .dependsOn(api)
  .settings(
    scalaVersion := Dependencies.scalaVersion,
    GithubConfig.settings,
    name := "smithy4play-example",
    scalacOptions += "-Ymacro-annotations",
    libraryDependencies ++= Dependencies.list,
  )

lazy val api = (project in file("modules/api"))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    scalaVersion := Dependencies.scalaVersion,
    libraryDependencies ++= Dependencies.list,
    GithubConfig.settings,
    Compile / smithy4sInputDir := (ThisBuild / baseDirectory).value / "modules" / "api-definition" / "src" / "main" / "resources" / "META-INF" / "smithy",
    Compile / smithy4sOutputDir := (ThisBuild / baseDirectory).value / "modules" / "api" / "src" / "main" / "scala"
  )
lazy val apiDefinition = project in file("modules/api-definition")
