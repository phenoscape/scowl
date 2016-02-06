
organization  := "org.phenoscape"

name          := "scowl"

version       := "1.0-SNAPSHOT"

crossScalaVersions := Seq("2.10.6", "2.11.7")

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

javaOptions += "-Xmx8G"

libraryDependencies ++= {
  Seq(
    "junit"                  %   "junit"                  % "4.10" % "test",
    "net.sourceforge.owlapi" %   "owlapi-distribution"    % "3.5.0"
  )
}
