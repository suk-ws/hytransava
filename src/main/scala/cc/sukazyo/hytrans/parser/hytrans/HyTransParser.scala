package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.data.hytrans.Document
import cc.sukazyo.hytrans.lexis.hytrans.*
import cc.sukazyo.hytrans.parser.hytrans.HyTransParser.{NodeParserWhenNoNodes, ParseNodeEvent, ParserParameter, ParsingParameter}
import cc.sukazyo.messiva.logger.Logger
import cc.sukazyo.std.contexts.GivenContext
import cc.sukazyo.std.contexts.GivenContext.ContextNotGivenException

import scala.collection.mutable.ListBuffer
import scala.util.boundary

object HyTransParser {
	
	trait ParserParameter (val logger: Logger)
	
	trait ParsingParameter (val context: GivenContext, protected val parsedDocument: ListBuffer[Document])
		extends ParserParameter {
		
		def getDocContext: DocumentContext =
			try {
				context >!> classOf[DocumentContext]
			} catch case e: ContextNotGivenException =>
				throw Exception("Missing required DocumentContext in GivenContext of HyTransParser.").initCause(e)
		
		def addDocument (document: Document): Unit =
			parsedDocument += document
			logger.trace(s"document added, now have ${parsedDocument.size} documents")
		
		def getBuiltDocuments: List[Document] =
			logger.trace("built result documents with an array of size " + parsedDocument.size)
			parsedDocument.toList
		
		private class ParseNodeEventImpl extends ParseNodeEvent
			with ParsingParameter(context, parsedDocument)
			with ParserParameter(logger)
		def genParseNodeEvent: ParseNodeEvent =
			ParseNodeEventImpl()
		
	}
	
	trait ParseNodeEvent extends ParsingParameter {
		
		private var _isComplete: Boolean = false
		def isComplete: Boolean = _isComplete
		def setComplete(): Unit =
			_isComplete = true
		
	}
	
	private object NodeParserWhenNoNodes extends NodeParser4HyTrans {
		override def parse(using node: Lexis4HyTrans)(using event: ParseNodeEvent): Unit = {
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
	
	private val nodeParsers: List[NodeParser4HyTrans] = _nodeParsers.appended(NodeParserWhenNoNodes)
	logger.debug(s"initialized HyTransParser with ${nodeParsers.size} plugins")
	
	private object ThisParameter extends ParserParameter(logger) {
		
		private class ParsingParameterImpl (context: GivenContext, parsedDocument: ListBuffer[Document])
			extends ParsingParameter (context, parsedDocument) with ParserParameter(this.logger)
		def genParsingParameter: ParsingParameter =
			ParsingParameterImpl(GivenContext(), ListBuffer.empty)
		
	}
	
	def parse (lexis: List[Lexis4HyTrans]): List[Document] = {
		
		
		given parameter: ParsingParameter = ThisParameter.genParsingParameter
		parameter.context << DocumentContext.getDefault
		
		for (node <- lexis) {
			
			given event: ParseNodeEvent = parameter.genParseNodeEvent
			
			boundary { for (parserHandler <- nodeParsers) {
				
				logger.debug(s"calling ${parserHandler.getClass.getSimpleName} for node $node")
				parserHandler.parse(
					using node
				)
				
				if event.isComplete then
					logger.debug("succeed!")
					boundary.break()
				
			}}
			
		}
		
		val lastDoc = parameter.getDocContext
		if lastDoc.nonEmpty then
			parameter.addDocument(lastDoc.buildDocument)
			logger.debug("built last document")
		else
			logger.debug("last document is empty, skipped")
			logger.warn("does your document is empty? cannot find any items in the last document in this file.")
		parameter.getBuiltDocuments
		
	}
	
}
