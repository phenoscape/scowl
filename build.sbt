
lazy val commonSettings = Seq(
  organization := "org.phenoscape",
  version := "1.4.1",
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  Test / publishArtifact := false,
  licenses := Seq("MIT license" -> url("https://opensource.org/licenses/MIT")),
  homepage := Some(url("https://github.com/phenoscape/scowl")),
  scalaVersion := "3.0.2",
  crossScalaVersions := Seq("2.13.8", "3.0.2"),
  scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8"),
  javaOptions += "-Xmx8G",
  libraryDependencies ++= {
    Seq(
      "org.scalatest" %% "scalatest" % "3.2.11" % Test,
      "org.scalaz" %% "scalaz-core" % "7.4.0-M7" % Test
    )
  },
  pomExtra := <scm>
    <url>git@github.com:phenoscape/scowl.git</url>
    <connection>scm:git:git@github.com:phenoscape/scowl.git</connection>
  </scm>
    <developers>
      <developer>
        <id>balhoff</id>
        <name>Jim Balhoff</name>
        <email>balhoff@renci.org</email>
      </developer>
    </developers>
)

lazy val owlapi4 = project
  .in(file("owlapi4"))
  .settings(commonSettings)
  .settings(name := "scowl",
    Compile / scalaSource := baseDirectory.value / ".." / "src" / "main" / "scala",
    Test / scalaSource := baseDirectory.value / ".." / "src" / "test" / "scala",
    libraryDependencies ++= {
      Seq(
        "net.sourceforge.owlapi" % "owlapi-distribution" % "4.5.26"
      )
    }
  )

lazy val owlapi5 = project
  .in(file("owlapi5"))
  .settings(commonSettings)
  .settings(name := "scowl-owlapi5",
    Compile / scalaSource := baseDirectory.value / ".." / "src" / "main" / "scala",
    Test / scalaSource := baseDirectory.value / ".." / "src" / "test" / "scala",
    libraryDependencies ++= {
      Seq(
        "net.sourceforge.owlapi" % "owlapi-distribution" % "5.1.20"
      )
    }
  )

lazy val parentProject = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name := "scowl-project",
    publish / skip := true,
    Compile / scalaSource := baseDirectory.value / "dummy",
    Test / scalaSource := baseDirectory.value / "dummy"
  )
  .aggregate(owlapi4, owlapi5)

Global / useGpg := false