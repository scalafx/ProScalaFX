// Project name
name := "ProScalaFX"

// Current version
version := "1.0.0-SNAPSHOT"

// Version of scala to use
scalaVersion := "2.10.3"

// Set the main Scala source directory to be <base>/src
scalaSource in Compile <<= baseDirectory(_ / "src")

resourceDirectory in Compile <<= baseDirectory(_ / "src")

// Set the Scala test directory to be <base>/test/src
scalaSource in Test <<= baseDirectory(_ / "test/src")

// Append -deprecation to the options passed to the Scala compiler
scalacOptions += "-deprecation"

// Point to location of a snapshot repository for ScalaFX
resolvers += Opts.resolver.sonatypeSnapshots

//resolvers += Opts.resolver.sonatypeStaging

// ScalaFX dependency
libraryDependencies += "org.scalafx" %% "scalafx" % "1.0.0-R9-SNAPSHOT"

// Test dependencies
libraryDependencies += "junit" % "junit" % "4.11" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.1" % "test"

// Set the prompt (for this build) to include the project id.
shellPrompt := { state => System.getProperty("user.name") + ":" + Project.extract(state).currentRef.project + "> " }

// Add JavaFX 2 to the unmanaged classpath
// For Java 7 update 06+ the JFXRT JAR is part of the Java Runtime Environment
unmanagedJars in Compile += Attributed.blank(file(scala.util.Properties.javaHome + "/lib/jfxrt.jar"))

// Fork a new JVM for 'run' and 'test:run'
fork := true

// Fork a new JVM for 'test:run', but not 'run'
fork in Test := true

// Only use a single thread for building
parallelExecution := false

// Execute tests in the current project serially
parallelExecution in Test := false


