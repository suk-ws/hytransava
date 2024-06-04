aether.AetherKeys.aetherOldVersionMethod := true

ThisBuild / scalaVersion := "3.4.1"

ThisBuild / organization := "cc.sukazyo"
ThisBuild / organizationName := "Sukazyo Workshop"
ThisBuild / organizationHomepage := Some(url("https://sukazyo.cc"))
ThisBuild / developers := List(
	Developer(
		id = "Eyre_S",
		name = "A.C.Sukazyo Eyre",
		email = "sukazyo@outlook.com",
		url = url("https://sukazyo.sukazyo.cc")
	)
)

ThisBuild / licenses += "MIT" -> url("https://github.com/suk-ws/hytransava/blob/master/LICENSE")

ThisBuild / version := ProjectMetadata.version
ThisBuild / versionScheme := Some("semver-spec")

ThisBuild / resolvers ++= ProjectMetadata.resolvers

val encoding = "UTF-8"
val javaTarget = "17"

lazy val root = (project in file("."))
	.settings(
		
		name := ProjectMetadata.package_name,
		// TODO: descriptions
		
		crossPaths := false,
		
		moduleName := ProjectMetadata.package_name,
		idePackagePrefix := Some({ProjectMetadata.package_id}),
		
		libraryDependencies ++= ProjectMetadata.dependencies,
		
		scalacOptions ++= Seq(
			"-language:postfixOps",
			"-language:implicitConversions",
			"-encoding", encoding,
		),
		javacOptions ++= Seq(
			"-encoding", encoding,
			"-source", javaTarget,
			"-target", javaTarget,
		),
		
		autoAPIMappings := true,
		
		publishTo := ProjectMetadata.publishTo,
		credentials ++= ProjectMetadata.publishCredentials,
		
	)
