// Project name
name := "ProScalaFX"

// Current version
version := "17.0.1-R26"

// Version of scala to use
val scala2Version = "2.13.7"
val scala3Version = "3.1.0"
// To cross compile with Scala 2 and Scala 3
crossScalaVersions := Seq(scala2Version, scala3Version)
scalaVersion := scala2Version

// Set the main Scala source directory to be <base>/src
Compile / scalaSource := baseDirectory(_ / "src").value

Compile / resourceDirectory := baseDirectory(_ / "src").value

// Append -deprecation to the options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-feature")

// Point to location of a snapshot repository for ScalaFX
resolvers += Opts.resolver.sonatypeSnapshots

//resolvers += Opts.resolver.sonatypeStaging

// Add ScalaFX dependency, exclude JavaFX transitive dependencies, may not mach this OS
libraryDependencies += "org.scalafx" %% "scalafx" % "17.0.1-R26"

// Add OS specific JavaFX dependencies
val javafxModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
libraryDependencies ++= javafxModules.map(m => "org.openjfx" % s"javafx-$m" % "17.0.1" classifier osName)

// Fork a new JVM for 'run' and 'test:run'
fork := true
