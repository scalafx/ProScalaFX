// Project name
name := "ProScalaFX"

// Current version
version := "25.0.2-R37"

// Scala version
scalaVersion := "3.8.2"

// Set the main Scala source directory to be <base>/src
Compile / scalaSource := baseDirectory(_ / "src").value

Compile / resourceDirectory := baseDirectory(_ / "src").value

scalacOptions ++= Seq("-deprecation", "-feature", "-rewrite", "-source:3.8-migration", "-explain", "-explain-types")

// Add ScalaFX dependency, exclude JavaFX transitive dependencies, may not match this OS
libraryDependencies += "org.scalafx" %% "scalafx" % "25.0.2-R37"

// Fork a new JVM for 'run' and 'test:run'
fork := true
