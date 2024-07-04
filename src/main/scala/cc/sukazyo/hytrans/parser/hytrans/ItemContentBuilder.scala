package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.data.hytrans.LocalizedContent
import cc.sukazyo.hytrans.lexis.hytrans.LexisItemLine.LinePrefix
import cc.sukazyo.hytrans.parser.hytrans.DocumentContext.LineWrapMode
import cc.sukazyo.hytrans.parser.hytrans.ItemContentBuilder.ContentLine

import scala.collection.mutable.ListBuffer

class ItemContentBuilder {
	
	val content: ListBuffer[ContentLine] = ListBuffer.empty
	
	def addLine (line: ContentLine): Unit =
		content += line
	
	def build: LocalizedContent =
		val rawBuilder = StringBuilder()
		rawBuilder `append` content.headOption.map(_.line).getOrElse("")
		for lineItem <- content.drop(1) do
			rawBuilder
				.append(lineItem.getIndent)
				.append(lineItem.line)
		LocalizedContent(
			raw = rawBuilder.toString()
		)
	
	def nonEmpty: Boolean =
		content.nonEmpty
	
}

object ItemContentBuilder {
	
	case class ContentLine (
		line: String,
		prefix: LinePrefix,
		defaultIndent: LineWrapMode
	) {
		
		def shouldIndent: Boolean =
			defaultIndent.shouldIndent(prefix)
		
		def getIndent: String =
			if shouldIndent then "\n" else ""
		
	}
	
}
