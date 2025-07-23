// Project name
name := "ProScalaFX"

// Current version
version := "24.0.0-R35"

// Version of scala to use
val scala2Version = "2.13.16"
val scala3Version = "3.3.5"
// To cross-compile with Scala 2 and Scala 3
crossScalaVersions := Seq(scala2Version, scala3Version)
scalaVersion       := scala3Version

// Set the main Scala source directory to be <base>/src
Compile / scalaSource := baseDirectory(_ / "src").value

Compile / resourceDirectory := baseDirectory(_ / "src").value

// Append -deprecation to the options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-feature")
scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, _)) => Seq("-Xcheckinit", "-Xsource:3")
    case Some((3, _)) => Seq("-rewrite", "-source:3.3-migration", "-explain", "-explain-types")
    case _            => Seq.empty[String]
  }
}

// Point to location of a snapshot repository for ScalaFX
resolvers += Resolver.sonatypeCentralSnapshots
resolvers += Resolver.mavenLocal

// Add ScalaFX dependency, exclude JavaFX transitive dependencies, may not match this OS
libraryDependencies += "org.scalafx" %% "scalafx" % "24.0.0-R35"

// Fork a new JVM for 'run' and 'test:run'
fork := true
