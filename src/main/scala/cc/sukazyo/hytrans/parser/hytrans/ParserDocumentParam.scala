package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.lexis.hytrans.*

import cc.sukazyo.std.contexts.GivenContext
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class ParserDocumentParam extends NodeParser4HyTrans {
	
	override def parse
	(using node: Lexis4HyTrans)
	(using context: GivenContext, event: HyTransParser.ParseNodeEvent): Unit = {
		node match
			case docParam: LexisDocumentParam =>
				
				context >> { (docContext: DocumentContext) =>
					docParam.key match
						case "encoding" =>
							val charset = Charset.forName(docParam.value)
							if charset != StandardCharsets.UTF_8 then
								throw Exception("Charset which is not UTF-8 is not supported yet!")
							docContext.encoding = charset
							event.setComplete()
						case "indent" =>
							try {
								val indent: Int = docParam.value.toInt
								if indent < 0 then
									event.logger.warn(s"Indent size cannot be negative in: ${docParam.value}")
									event.logger.warn("This will be ignored.")
								else
									docContext.indentSize = indent
									event.setComplete()
							} catch case e: NumberFormatException =>
								event.logger.error("Cannot set indent size: " + e.getMessage)
								event.logger.warn("This will be ignored.")
						case _ =>
							event.logger.warn("Unsupported document parameter: " + docParam.key)
				} || event.missingRequiredContext
				
			case _ =>
	}
	
}
