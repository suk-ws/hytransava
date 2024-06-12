package cc.sukazyo.hytrans;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import org.junit.platform.console.options.TestConsoleOutputOptions;
import org.junit.platform.console.options.TestDiscoveryOptions;
import org.junit.platform.console.tasks.ConsoleTestExecutor;

public class JUnitRunner {
	
	public static void runTests () {
		
		PrintWriter out = new PrintWriter(System.out);
		
		TestDiscoveryOptions discoveryOptions = new TestDiscoveryOptions();
		discoveryOptions.setIncludedClassNamePatterns(List.of(".+[.$]JTest.*"));
		discoveryOptions.setIncludedPackages(List.of("cc.sukazyo.hytrans"));
		discoveryOptions.setScanClasspath(true);
		discoveryOptions.setScanModulepath(true);
		TestConsoleOutputOptions consoleOutOptions = new TestConsoleOutputOptions();
		
		ConsoleTestExecutor executor = new ConsoleTestExecutor(discoveryOptions, consoleOutOptions);
		
		executor.execute(out, Optional.empty());
		
	}
	
}
