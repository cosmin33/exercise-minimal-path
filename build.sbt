val scala3Version = "3.5.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Minimal Path Exercise",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

  )
