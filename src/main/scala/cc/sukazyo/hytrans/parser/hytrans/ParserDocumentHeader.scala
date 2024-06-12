package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.std.contexts.GivenContext

import cc.sukazyo.hytrans.lexis.hytrans.Lexis4HyTrans

import cc.sukazyo.hytrans.parser.hytrans.HyTransParser.ParseNodeEvent
import cc.sukazyo.hytrans.lexis.hytrans.LexisDocumentHead

class ParserDocumentHeader extends NodeParser4HyTrans {
	
	override def parse
	(using node: Lexis4HyTrans)
	(using context: GivenContext, event: ParseNodeEvent): Unit = {
		node match
			case docHeader: LexisDocumentHead =>
				
				if !List("1.0", "1").contains(docHeader.documentVersion) then
					throw new Exception(s"Unsupported document version: '${docHeader.documentVersion}'")
				context << DocumentContext.getDefault
				event.setComplete()
				
			case _ =>
	}
	
}
