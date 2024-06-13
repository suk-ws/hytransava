package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.lexis.hytrans.Lexis4HyTrans

import cc.sukazyo.std.contexts.GivenContext

trait NodeParser4HyTrans {
	
	def parse (using node: Lexis4HyTrans)(using context: GivenContext, event: HyTransParser.ParseNodeEvent): Unit
	
}
