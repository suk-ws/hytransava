package cc.sukazyo.hytrans
package lexis

object LineLexer {
	
	def parseStringDocument (text: String): List[LineLexis] = {
		text
			.split("\n|\r|\r\n")
			.map(LineLexis.fromLine)
			.toList
	}
	
}
