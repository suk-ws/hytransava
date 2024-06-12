package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.std.contexts.GivenContext

import cc.sukazyo.hytrans.lexis.hytrans.Lexis4HyTrans

import cc.sukazyo.hytrans.parser.hytrans.HyTransParser.ParseNodeEvent
import cc.sukazyo.hytrans.lexis.hytrans.LexisItemKey

class ParserItemKey extends NodeParser4HyTrans {
	
	override def parse
	(using node: Lexis4HyTrans)
	(using context: GivenContext, event: ParseNodeEvent): Unit = {
		node match
			case itemKey: LexisItemKey =>
				
				context >> { (docContext: DocumentContext) =>
					context >> { (itemBuilder: DocItem.Builder) =>
						if itemBuilder.nonEmpty then
							docContext.addItem(itemBuilder.build)
						else
							event.logger.warn("found item key which have no lines declared: " + itemKey.key)
					}
					context << DocItem.Builder(itemKey.key)
					event.setComplete()
				} || event.missingRequiredContext
				
			case _ =>
	}
	
}
