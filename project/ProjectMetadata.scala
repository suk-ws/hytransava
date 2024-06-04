import sbt.*

object ProjectMetadata {
	
	val package_name: String = ProjectConfig.package_name
	val package_group: String = ProjectConfig.package_group
	val package_id: String = ProjectConfig.package_id
	
	val isSnapshot: Boolean = ProjectConfig.SNAPSHOT
	
	val version: String = Seq(
		ProjectConfig.version,
		if (isSnapshot) "SNAPSHOT" else null
	).filterNot(x => x == null).mkString("-")
	
	val resolvers: Seq[Resolver] = ProjectConfig.dependencyRepos
	val dependencies: Seq[ModuleID] = ProjectConfig.dependencies
	
	val publishTo: Option[Resolver] = ProjectConfig.publishTo
	val publishCredentials: Seq[Credentials] = ProjectConfig.publishCredentials
	
}
