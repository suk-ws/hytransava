package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.lexis.hytrans.*

import java.nio.charset.{Charset, StandardCharsets}

class ParserDocumentParam extends NodeParser4HyTrans {
	
	override def parse (using node: Lexis4HyTrans)(using event: HyTransParser.ParseNodeEvent): Unit = {
		
		node match
			case docParam: LexisDocumentParam =>
				
				val docContext = event.getDocContext
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
					case "line-wrap" =>
						DocumentContext.LineWrapMode.byName(docParam.value) match
							case Some(mode) =>
								docContext.lineWrapMode = mode
								event.setComplete()
							case None =>
								event.logger.error(s"Cannot set line wrap mode due to `${docParam.value}` is not a valid setting value.")
					case _ =>
						event.logger.warn("Unsupported document parameter: " + docParam.key)
				
			case _ =>
	}
	
}
