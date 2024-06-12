package cc.sukazyo.hytrans
package parser.hytrans

import lexis.hytrans.*

import cc.sukazyo.std.contexts.GivenContext

class ParserComments extends NodeParser4HyTrans {
	
	override def parse
	(using node: Lexis4HyTrans)
	(using context: GivenContext, event: HyTransParser.ParseNodeEvent): Unit = {
		node match
			case commentNode: LexisCommentLine =>
				// comment line can be safely ignored
				event.setComplete()
			case emptyLine: LexisEmptyLine =>
				// empty line can also be safely ignored
				event.setComplete()
			case _ =>
	}
	
}
