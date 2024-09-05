val scala3Version = "3.5.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Minimal Path Exercise",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    mainClass := Some("io.cosmo.exercise.MyApp"),

    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.9",
      "dev.zio" %% "zio-streams" % "2.1.9"
    )

  )
