
organization  := "org.phenoscape"

name          := "scowl-owlapi5"

version       := "1.4.1-SNAPSHOT"

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

scalaVersion  := "3.0.2"

crossScalaVersions := Seq("2.13.8", "3.0.2")

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

javaOptions += "-Xmx8G"

libraryDependencies ++= {
  Seq(
    "net.sourceforge.owlapi" %  "owlapi-distribution"    % "5.1.20",
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