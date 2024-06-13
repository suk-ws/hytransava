package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.lexis.hytrans.{Lexis4HyTrans, LexisItemKey}
import cc.sukazyo.hytrans.parser.hytrans.HyTransParser.ParseNodeEvent

class ParserItemKey extends NodeParser4HyTrans {
	
	override def parse (using node: Lexis4HyTrans)(using event: ParseNodeEvent): Unit = {
		node match
			case itemKey: LexisItemKey =>
				
				import event.context
				val docContext = event.getDocContext
				context >> { (itemBuilder: DocItem.Builder) =>
					if itemBuilder.nonEmpty then
						docContext.addItem(itemBuilder.build)
					else
						event.logger.warn("found item key which have no lines declared: " + itemKey.key)
				}
				context << DocItem.Builder(itemKey.key)
				event.setComplete()
				
			case _ =>
	}
	
}
