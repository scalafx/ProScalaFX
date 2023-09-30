// Project name
name := "ProScalaFX"

// Current version
version := "20.0.0-R31"

// Version of scala to use
val scala2Version = "2.13.12"
val scala3Version = "3.3.1"
// To cross compile with Scala 2 and Scala 3
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
resolvers ++= Opts.resolver.sonatypeOssSnapshots

//resolvers += Opts.resolver.sonatypeStaging

// Add ScalaFX dependency, exclude JavaFX transitive dependencies, may not mach this OS
libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31"

// Fork a new JVM for 'run' and 'test:run'
fork := true
