import sbtrelease.ReleaseStateTransformations._

name:="content-api-client"

resolvers += Resolver.sonatypeRepo("releases")

enablePlugins(BuildInfoPlugin)
pomExtra := (
  <url>https://github.com/guardian/content-api-scala-client</url>
    <developers>
      <developer>
        <id>jennysivapalan</id>
        <name>Jenny Sivapalan</name>
        <url>https://github.com/jennysivapalan</url>
      </developer>
      <developer>
        <id>maxharlow</id>
        <name>Max Harlow</name>
        <url>https://github.com/maxharlow</url>
      </developer>
      <developer>
        <id>nicl</id>
        <name>Nic Long</name>
        <url>https://github.com/nicl</url>
      </developer>
      <developer>
        <id>mchv</id>
        <name>Mariot Chauvin</name>
        <url>https://github.com/mchv</url>
      </developer>
      <developer>
        <id>LATaylor-guardian</id>
        <name>Luke Taylor</name>
        <url>https://github.com/LATaylor-guardian</url>
      </developer>
      <developer>
        <id>cb372</id>
        <name>Chris Birchall</name>
        <url>https://github.com/cb372</url>
      </developer>
    </developers>
  )
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ => false }
description := "Scala client for the Guardian's Content API"
scalaVersion := "2.12.3"
crossScalaVersions := Seq("2.11.11", scalaVersion.value)
releasePublishArtifactsAction := PgpKeys.publishSigned.value
organization := "com.gu"
licenses := Seq("Apache v2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))
scmInfo := Some(ScmInfo(
  url("https://github.com/guardian/content-api-scala-client"),
  "scm:git:git@github.com:guardian/content-api-scala-client.git"
))
javacOptions ++= Seq("-source", "1.7", "-target", "1.7")
scalacOptions ++= Seq("-deprecation", "-unchecked")
releaseProcess := Seq(
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)
buildInfoKeys := Seq[BuildInfoKey](version)
buildInfoPackage := "com.gu.contentapi.buildinfo"
buildInfoObject := "CapiBuildInfo"

val CapiModelsVersion = "11.48"

libraryDependencies ++= Seq(
  "com.gu" %% "content-api-models-scala" % CapiModelsVersion,
  "com.squareup.okhttp3" % "okhttp" % "3.9.0",
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.google.guava" % "guava" % "19.0" % "test",
  "org.mockito" % "mockito-all" % "1.9.0" % "test"

)

initialCommands in console := """
  import com.gu.contentapi.client._
  import com.gu.contentapi.client.model._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Await
  import scala.concurrent.duration._
"""