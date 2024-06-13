package cc.sukazyo.hytrans.lexis

object LineLexer {
	
	def parseStringDocument (text: String): List[LineLexis] = {
		text
			.split("\n|\r|\r\n")
			.map(LineLexis.fromLine)
			.toList
	}
	
}
