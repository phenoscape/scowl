
organization  := "org.phenoscape"

name          := "scowl"

version       := "1.4.0"

publishMavenStyle := true

publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
    else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

Test / publishArtifact := false

licenses := Seq("MIT license" -> url("https://opensource.org/licenses/MIT"))

homepage := Some(url("https://github.com/phenoscape/scowl"))

scalaVersion  := "3.0.1"

crossScalaVersions := Seq("2.13.6", "3.0.1")

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

javaOptions += "-Xmx8G"

libraryDependencies ++= {
  Seq(
    "net.sourceforge.owlapi" %  "owlapi-distribution"    % "4.5.16",
    "org.scalatest"          %% "scalatest"              % "3.2.9"    % Test,
    "org.scalaz"             %% "scalaz-core"            % "7.4.0-M7" % Test
  )
}

pomExtra := <scm>
  <url>git@github.com:phenoscape/scowl.git</url>
  <connection>scm:git:git@github.com:phenoscape/scowl.git</connection>
</scm>
  <developers>
    <developer>
      <id>balhoff</id>
      <name>Jim Balhoff</name>
      <email>jim@balhoff.org</email>
    </developer>
  </developers>