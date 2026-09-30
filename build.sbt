// Project name
name := "ProScalaFX"

// Current version
version := "26.0.0-R38"

// Scala version
scalaVersion := "3.9.0"

// Set the main Scala source directory to be <base>/src
Compile / scalaSource := baseDirectory(_ / "src").value

Compile / resourceDirectory := baseDirectory(_ / "src").value

scalacOptions ++= Seq("-deprecation", "-feature", "-rewrite", "-source:3.9-migration", "-explain", "-explain-types")

// Add ScalaFX dependency, exclude JavaFX transitive dependencies, may not match this OS
libraryDependencies += "org.scalafx" %% "scalafx" % "26.0.0-R38"

// Fork a new JVM for 'run' and 'test:run'
fork := true
