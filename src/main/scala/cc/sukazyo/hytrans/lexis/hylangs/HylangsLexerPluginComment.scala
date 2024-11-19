package cc.sukazyo.hytrans.lexis.hylangs

import cc.sukazyo.std.data.EncapsulateValue

class HylangsLexerPluginComment extends HylangsLexerPlugin {
	
	override def tryLine (line: String): Option[List[HylangsLineLexis]] = {
		if (line.trim.startsWith("#")) {
			(HylangsLineLexis.CommentLine() :: Nil).toSome
		} else {
			None
		}
	}
	
}
