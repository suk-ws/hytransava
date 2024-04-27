ThisBuild / scalaVersion := "3.4.1"

ThisBuild / organization := "cc.sukazyo"
ThisBuild / organizationName := "A.C. Sukazyo Eyre"

ThisBuild / version := ProjectConfig.version

val encoding = "UTF-8"
val javaTarget = "17"

lazy val root = (project in file("."))
	.settings(
		
		name := ProjectConfig.package_name,
		
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
