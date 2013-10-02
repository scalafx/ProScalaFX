// Project name
name := "ScalaFXPro"

// Current version
version := "1.0.0-SNAPSHOT"

// Version of scala to use
scalaVersion := "2.9.3"

// Set the main Scala source directory to be <base>/src
scalaSource in Compile <<= baseDirectory(_ / "src")

resourceDirectory in Compile <<= baseDirectory(_ / "src")

// Set the Scala test directory to be <base>/test/src
scalaSource in Test <<= baseDirectory(_ / "test/src")

// Append -deprecation to the options passed to the Scala compiler
scalacOptions += "-deprecation"

// Point to location of a snapshot repository for ScalaFX
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

//resolvers += "Sonatype OSS Staging" at "https://oss.sonatype.org/content/repositories/staging"

// ScalaFX dependency
libraryDependencies += "org.scalafx" %% "scalafx" % "1.0.0-M6-SNAPSHOT"

// Test dependencies
libraryDependencies += "junit" % "junit" % "4.11" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

// ScalaStyle
org.scalastyle.sbt.ScalastylePlugin.Settings

// Add JavaFX 2 to the unmanaged classpath
// For Java 7 update 06+ the JFXRT JAR is part of the Java Runtime Environment
unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/jfxrt.jar"))

// Fork a new JVM for 'run' and 'test:run'
fork := true

// Fork a new JVM for 'test:run', but not 'run'
fork in Test := true

// Only use a single thread for building
parallelExecution := false

// Execute tests in the current project serially
parallelExecution in Test := false


