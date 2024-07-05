package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.lexis.hytrans.{Lexis4HyTrans, LexisItemKey}
import cc.sukazyo.hytrans.parser.hytrans.HyTransParser.ParseNodeEvent

class ParserItemKey extends NodeParser4HyTrans {
	
	override def parse (using node: Lexis4HyTrans)(using event: ParseNodeEvent): Unit = {
		node match
			case itemKey: LexisItemKey =>
				
				import event.context
				val docContext = event.getDocContext
				
				context >> { (itemBuilder: ItemBuilder) =>
					if itemBuilder.contentBuilder.nonEmpty then
						docContext.addItem(itemBuilder.itemKey, itemBuilder.contentBuilder.build)
					else
						event.logger.warn("found item key which have no lines declared: " + itemKey.key)
				}
				
				context << ItemBuilder(itemKey.key)
				docContext.registerOnDocumentEnd (this.getClass) { endedDocumentContext =>
					context >> { (unfinishedItem: ItemBuilder) =>
						endedDocumentContext.addItem(unfinishedItem.itemKey, unfinishedItem.contentBuilder.build)
					}
				}
				
				event.setComplete()
				
			case _ =>
	}
	
}
