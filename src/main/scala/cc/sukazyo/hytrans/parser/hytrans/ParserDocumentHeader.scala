package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.lexis.hytrans.{Lexis4HyTrans, LexisDocumentHead}
import cc.sukazyo.hytrans.parser.hytrans.HyTransParser.ParseNodeEvent

class ParserDocumentHeader extends NodeParser4HyTrans {
	
	override def parse (using node: Lexis4HyTrans)(using event: ParseNodeEvent): Unit = {
		
		node match
			case docHeader: LexisDocumentHead =>
				
				import event.context
				event.logger.debug(s"find a new document header with version ${docHeader.documentVersion}")
				if !List("1.0", "1").contains(docHeader.documentVersion) then
					throw new Exception(s"Unsupported document version: '${docHeader.documentVersion}'")
				context >> { (oldDocumentContext: DocumentContext) =>
					if oldDocumentContext.nonEmpty then
						event.addDocument(oldDocumentContext.buildDocument)
						event.logger.debug("built old document")
					else
						event.logger.debug("old document is empty, skipped")
				}
				context << DocumentContext.getDefault
				event.logger.debug("created a new document context")
				event.setComplete()
				
			case _ =>
	}
	
}
