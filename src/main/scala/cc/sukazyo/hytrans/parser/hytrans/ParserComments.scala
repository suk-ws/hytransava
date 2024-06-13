package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.lexis.hytrans.*

class ParserComments extends NodeParser4HyTrans {
	
	override def parse (using node: Lexis4HyTrans)(using event: HyTransParser.ParseNodeEvent): Unit = {
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
