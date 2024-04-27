package cc.sukazyo

package object hytrans {
	
	@main
	def runAllTests (): Unit = {
		
		JUnitRunner.runTests()
		
		SuiteHytransava().execute()
		
	}
	
}
