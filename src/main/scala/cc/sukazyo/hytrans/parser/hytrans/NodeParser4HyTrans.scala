package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.lexis.hytrans.Lexis4HyTrans

trait NodeParser4HyTrans {
	
	def parse (using node: Lexis4HyTrans)(using event: HyTransParser.ParseNodeEvent): Unit
	
}
