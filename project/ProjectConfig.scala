import sbt.*

object ProjectConfig {
	
	val package_name :String = "hytransava"
	val package_group: String = "cc.sukazyo"
	val package_id: String = s"$package_group.hytrans"
	val version: String = "0.1.0-alpha1"
	
	val SNAPSHOT: Boolean = true
	
	val dependencies: Seq[ModuleID] = Seq(
		
		"org.scalatest" %% "scalatest"          % "3.2.18" % Test,
		"org.scalatest" %% "scalatest-freespec" % "3.2.18" % Test,
		// for test report
		"com.vladsch.flexmark" % "flexmark"                 % "0.64.8" % Test,
		"com.vladsch.flexmark" % "flexmark-profile-pegdown" % "0.64.8" % Test,
		
		"org.junit.platform" % "junit-platform-launcher" % "1.10.2" % Test,
		"org.junit.platform" % "junit-platform-commons"  % "1.4.2"  % Test,
		"org.junit.jupiter"  % "junit-jupiter-engine"    % "5.10.2" % Test,
//		"org.junit.vintage"  % "junit-vintage-engine"    % "5.4.2"  % Test,
//		"net.aichler"        % "jupiter-interface"       % "0.11.1" % Test,
//		"org.assertj"        % "assertj-core"            % "3.25.3" % Test,
//		"com.novocode"       % "junit-interface"         % "0.11"   % Test,
		
	)
	
	val publishTarget: String = "workshop"
	val publishTo: Option[Resolver] = {
		publishTarget match {
			case "workshop" => SNAPSHOT match {
				case true => Some("-ws-snapshots" at "https://mvn.sukazyo.cc/snapshots")
				case false => Some("-ws-releases" at "https://mvn.sukazyo.cc/releases")
			}
			case "local" => Some(Resolver.file("build", file("S:/__tests/artifacts")))
			case _ => None
		}
	}
	val publishCredentials: Seq[Credentials] = Seq(
			if (publishTarget == "workshop") Credentials(Path.userHome / ".sbt" / ("workshop-mvn"+".credentials")) else null
	).filterNot(_ == null)
	
}
