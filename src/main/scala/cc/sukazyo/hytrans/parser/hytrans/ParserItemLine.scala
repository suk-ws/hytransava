package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.std.contexts.GivenContext

import cc.sukazyo.hytrans.lexis.hytrans.Lexis4HyTrans

import cc.sukazyo.hytrans.parser.hytrans.HyTransParser.ParseNodeEvent
import cc.sukazyo.hytrans.lexis.hytrans.LexisItemLine

class ParserItemLine extends NodeParser4HyTrans {
	
	def parse(using node: Lexis4HyTrans)
	(using context: GivenContext, event: ParseNodeEvent): Unit = {
		node match
			case itemLine: LexisItemLine =>
				
				context >> { (docContext: DocumentContext) =>
					
					// TODO: differences between `\` `|` `/`
					val realLineContent: String =
						itemLine.content.drop(docContext.indentSize)
					val taken = itemLine.content.take(docContext.indentSize)
					if !taken.isBlank then
						event.logger.warn(
							s"""Found a line that contains valid character in indent sized location:
							   |  at line: "${itemLine.content}"
							   |  chars will be cut: "$taken" """.stripMargin
						)
					
					context >> { (itemBuilder: DocItem.Builder) =>
						itemBuilder.addLine(realLineContent)
					} || {
						event.logger.warn("found content line which have no keys declared: " + itemLine.content)
					}
					
					event.setComplete()
					
				} || event.missingRequiredContext
				
			case _ =>
	}
	
}
