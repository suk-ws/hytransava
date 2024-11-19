package cc.sukazyo.hytrans.lexis.hylangs

import scala.util.boundary
import scala.util.boundary.break

class HylangsLexer (val plugins: List[HylangsLexerPlugin]) {
	
	def parse (content: String): List[HylangsLineLexis] = {
		
		val lines = content.split("\n")
		var lexis: List[HylangsLineLexis] = List()
		
		for (line <- lines) {
			
			boundary { for (plugin <- plugins) {
				val result = plugin.tryLine(line)
				if (result.isDefined) {
					lexis = lexis ++ result.get
					break()
				}
			}}
			
		}
		
		lexis
		
	}
	
}

object HylangsLexer {
	
	val defaultLexerPlugins = List(
		HylangsLexerPluginComment(),
		HylangsLexerPluginLanguageDef()
	)
	
	def defaultLexer = new HylangsLexer(defaultLexerPlugins)
	
	def withDefaultLexer (plugins: List[HylangsLexerPlugin]) = new HylangsLexer(
		defaultLexerPlugins ++ plugins
	)
	
}
