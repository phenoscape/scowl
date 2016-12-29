
organization  := "org.phenoscape"

name          := "scowl"

version       := "1.2-SNAPSHOT"

crossScalaVersions := Seq("2.10.6", "2.11.7")

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

javaOptions += "-Xmx8G"

libraryDependencies ++= {
  Seq(
    "net.sourceforge.owlapi" %  "owlapi-distribution"    % "4.2.7",
    "org.scalatest"          %% "scalatest"              % "2.2.6" % Test,
    "org.scalaz"             %% "scalaz-core"            % "7.2.1" % Test
  )
}
