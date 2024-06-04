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

ThisBuild / version := ProjectConfig.version

ThisBuild / resolvers ++= ProjectConfig.dependencyRepos

val encoding = "UTF-8"
val javaTarget = "17"

lazy val root = (project in file("."))
	.settings(
		
		name := ProjectConfig.package_name,
		// TODO: descriptions
		
		moduleName := ProjectConfig.package_id,
		idePackagePrefix := Some({ProjectConfig.package_id}),
		
		libraryDependencies ++= ProjectConfig.dependencies,
		
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
		
		publishTo := ProjectConfig.publishTo,
		credentials ++= ProjectConfig.publishCredentials,
		
	)
