package cc.sukazyo.hytrans.lexis.hylangs

trait HylangsLexerPlugin {
	
	def tryLine (line: String): Option[List[HylangsLineLexis]]
	
}
