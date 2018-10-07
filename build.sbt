// Project name
name := "ProScalaFX"

// Current version
version := "8.0.181-R13"

// Version of scala to use
scalaVersion := "2.12.7"

// Set the main Scala source directory to be <base>/src
scalaSource in Compile := baseDirectory(_ / "src").value

resourceDirectory in Compile := baseDirectory(_ / "src").value

// Append -deprecation to the options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-feature")

// Point to location of a snapshot repository for ScalaFX
resolvers += Opts.resolver.sonatypeSnapshots

//resolvers += Opts.resolver.sonatypeStaging

// ScalaFX dependency
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.181-R13"

// Set the prompt (for this build) to include the project id.
shellPrompt := { state => System.getProperty("user.name") + ":" + Project.extract(state).currentRef.project + "> " }

// Fork a new JVM for 'run' and 'test:run'
fork := true

