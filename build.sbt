name := "akka-streams-demo"

description := "Akka streams demo"

scalaVersion in ThisBuild := "2.11.8"

scalacOptions in ThisBuild := Seq(
  "-encoding", "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-Xlint",
  "-optimise"
)

fork := true

libraryDependencies ++= {
  val akkaV       = "2.4.3"
  Seq(
    "com.typesafe.akka" %% "akka-actor"              % akkaV,
    "com.typesafe.akka" %% "akka-stream"             % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental"  % akkaV
  )
}
