import sbt.*

object ProjectConfig {
	
	val package_name :String = "hytransava"
	val package_group: String = "cc.sukazyo"
	val package_id: String = s"$package_group.hytrans"
	val version: String = "0.1.0"
	
	val SNAPSHOT: Boolean = true
	
	val dependencyRepos: Seq[Resolver] = Seq(
		Resolvers.ws_release,
		Resolvers.ws_snapshots
	)
	
	val dependencies: Seq[ModuleID] = Seq(
		
		"cc.sukazyo" % "da4a" % "0.2.0",
		"cc.sukazyo" % "messiva" % "0.2.0",
		
		"cc.sukazyo" % "resource-tools" % "0.3.1" % Test,
		
		"org.scalatest" %% "scalatest"          % "3.2.19" % Test,
		"org.scalatest" %% "scalatest-freespec" % "3.2.19" % Test,
		// for test report
		"com.vladsch.flexmark" % "flexmark"                 % "0.64.8" % Test,
		"com.vladsch.flexmark" % "flexmark-profile-pegdown" % "0.64.8" % Test,
		
		"org.junit.jupiter"  % "junit-jupiter"           % "5.13.4" % Test,
		"org.junit.platform" % "junit-platform-commons"  % "1.13.4" % Test,
		"org.junit.platform" % "junit-platform-launcher" % "1.13.4" % Test,
		"org.junit.platform" % "junit-platform-console"  % "1.13.4" % Test,
//		"org.junit.vintage"  % "junit-vintage-engine"    % "5.4.2"  % Test,
//		"net.aichler"        % "jupiter-interface"       % "0.11.1" % Test,
//		"org.assertj"        % "assertj-core"            % "3.25.3" % Test,
//		"com.novocode"       % "junit-interface"         % "0.11"   % Test,
		
	)
	
	val publishTarget: String = "workshop"
	
	val publishTo: Option[Resolver] = {
		publishTarget match {
			case "workshop" =>
				if (SNAPSHOT) Some(Resolvers.ws_snapshots)
				else Some(Resolvers.ws_release)
			case "local" => Some(Resolvers.local)
			case _ => None
		}
	}
	val publishCredentials: Seq[Credentials] = Seq(
			if (publishTarget == "workshop") Credentials(Path.userHome / ".sbt" / ("workshop-mvn"+".credentials")) else null
	).filterNot(_ == null)
	
	object Resolvers {
		val ws_snapshots = "-ws-snapshots" at "https://mvn.sukazyo.cc/snapshots"
		val ws_release = "-ws-releases" at "https://mvn.sukazyo.cc/releases"
		val local = Resolver.file("build", file("S:/__tests/artifacts"))
	}
	
}
