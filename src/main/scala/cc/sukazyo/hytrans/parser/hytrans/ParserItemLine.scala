package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.lexis.hytrans.{Lexis4HyTrans, LexisItemLine}
import cc.sukazyo.hytrans.lexis.hytrans.LexisItemLine.LinePrefix
import cc.sukazyo.hytrans.parser.hytrans.HyTransParser.ParseNodeEvent
import cc.sukazyo.hytrans.parser.hytrans.ItemContentBuilder.ContentLine

class ParserItemLine extends NodeParser4HyTrans {
	
	def parse (using node: Lexis4HyTrans)(using event: ParseNodeEvent): Unit = {
		node match
			case itemLine: LexisItemLine =>
				
				import event.context
				val docContext = event.getDocContext
				
				val prefixed: LinePrefix = itemLine.prefixTag
				val realLineContent: String =
					itemLine.content.drop(docContext.indentSize)
				val taken = itemLine.content.take(docContext.indentSize)
				if !taken.isBlank then
					event.logger.warn(
						s"""Found a line that contains valid character in indent sized location:
						   |  at line: "${itemLine.content}"
						   |  chars will be cut: "$taken" """.stripMargin
					)
				
				context >> { (itemBuilder: ItemBuilder) =>
					itemBuilder.contentBuilder.addLine(ContentLine(realLineContent, prefixed, docContext.lineWrapMode))
				} || {
					event.logger.warn("found content line which have no keys declared: " + itemLine.content)
				}
				
				event.setComplete()
				
			case _ =>
	}
	
}
