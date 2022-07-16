// Project name
name := "ProScalaFX"

// Current version
version := "18.0.1-R28"

// Version of scala to use
val scala2Version = "2.13.8"
val scala3Version = "3.1.3"
// To cross compile with Scala 2 and Scala 3
crossScalaVersions := Seq(scala2Version, scala3Version)
scalaVersion := scala2Version

// Set the main Scala source directory to be <base>/src
Compile / scalaSource := baseDirectory(_ / "src").value

Compile / resourceDirectory := baseDirectory(_ / "src").value

// Append -deprecation to the options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-feature")

// Point to location of a snapshot repository for ScalaFX
resolvers ++= Opts.resolver.sonatypeOssSnapshots

//resolvers += Opts.resolver.sonatypeStaging

// Add ScalaFX dependency, exclude JavaFX transitive dependencies, may not mach this OS
libraryDependencies += "org.scalafx" %% "scalafx" % "18.0.1-R28"

// Fork a new JVM for 'run' and 'test:run'
fork := true
