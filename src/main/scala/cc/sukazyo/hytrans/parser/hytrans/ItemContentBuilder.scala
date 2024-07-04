package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.data.hytrans.LocalizedContent
import cc.sukazyo.hytrans.lexis.hytrans.LexisItemLine.LinePrefix
import cc.sukazyo.hytrans.parser.hytrans.ItemContentBuilder.ContentLine

import scala.collection.mutable.ListBuffer

class ItemContentBuilder {
	
	val content: ListBuffer[ContentLine] = ListBuffer.empty
	
	def addLine (line: String, prefix: LinePrefix): Unit =
		content += ContentLine(line, prefix)
	
	def build: LocalizedContent =
		LocalizedContent(content.map(_.line).mkString("\n")) // TODO: if new line
	
	def nonEmpty: Boolean =
		content.nonEmpty
	
}

object ItemContentBuilder {
	
	case class ContentLine (
		line: String,
		prefix: LinePrefix
	)
	
}
