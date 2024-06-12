package cc.sukazyo.hytrans
package parser.hytrans

import lexis.hytrans.*

import cc.sukazyo.messiva.logger.Logger
import cc.sukazyo.std.contexts.GivenContext

import java.nio.charset.{Charset, StandardCharsets}
import cc.sukazyo.hytrans.parser.hytrans.HyTransParser.ParseNodeEvent
import cc.sukazyo.hytrans.parser.hytrans.HyTransParser._Parameter
import cc.sukazyo.hytrans.parser.hytrans.HyTransParser.NodeParserWhenNoNodes
import scala.util.boundary

object HyTransParser {
	
	trait _Parameter {
		val logger: Logger
		def missingRequiredContext: Nothing
	}
	
	trait ParseNodeEvent extends _Parameter {
		private var _isComplete: Boolean = false
		def isComplete: Boolean = _isComplete
		def setComplete(): Unit =
			_isComplete = true
	}
	object ParseNodeEvent {
		def apply(source: _Parameter): ParseNodeEvent =
			new ParseNodeEvent {
				override val logger: Logger = source.logger
				override def missingRequiredContext: Nothing =
					source.missingRequiredContext
			}
	}
	
	private object NodeParserWhenNoNodes extends NodeParser4HyTrans {
		override def parse(using node: Lexis4HyTrans)(using context: GivenContext, event: ParseNodeEvent): Unit = {
			event.logger.warn(
				s"""Parsing the following node failed due to cannot found proper parser:
				   |  type: ${node.getClass}
				   |  stringed:
				   |${node.toString.indent(4)}""".stripMargin)
		}
	}
	
	def getStandardParserPlugins: List[NodeParser4HyTrans] =
		List(
			ParserComments(),
			ParserDocumentParam(),
			ParserDocumentHeader(),
			ParserItemKey(),
			ParserItemLine()
		)
	
}

class HyTransParser (
	
	logger: Logger,
	
	_nodeParsers: List[NodeParser4HyTrans]
	
) {
	
	val nodeParsers: List[NodeParser4HyTrans] = _nodeParsers.appended(NodeParserWhenNoNodes)
	logger.debug(s"initialized HyTransParser with ${nodeParsers.size} plugins")
	
	private val _logger = logger
	object Parameter extends _Parameter {
		
		val logger: Logger = _logger
		
		def missingRequiredContext: Nothing =
			throw Exception("Missing required GivenContext")
		
	}
	
	def parse (lexis: List[Lexis4HyTrans]): Map[String, String] = {
		
		given context: GivenContext = GivenContext()
		context << DocumentContext.getDefault
		
		for (node <- lexis) {
			
			given event: ParseNodeEvent = ParseNodeEvent(Parameter)
			
			boundary { for (parserHandler <- nodeParsers) {
				
				logger.debug(s"calling ${parserHandler.getClass.getSimpleName} for node ${node}")
				parserHandler.parse(
					using node
				)
				
				if event.isComplete then
					logger.debug("succeed!")
					boundary.break()
				
			}}
			
		}
		
		context >> { (docContext: DocumentContext) =>
			docContext.toItemKVMap
		} || Parameter.missingRequiredContext
		
	}
	
}
