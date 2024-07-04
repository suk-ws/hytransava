package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.data.hytrans.{LocalizedContent, LocalizedDocument}

import java.nio.charset.Charset
import scala.collection.mutable

case class DocumentContext (
	
	documentVersion: String,
	
	var encoding: Charset,
	var indentSize: Int,
	
) {
	
	private val builtItems: mutable.Map[String, LocalizedContent] = mutable.SeqMap.empty[String, LocalizedContent]
	
	def addItem (key: String, content: LocalizedContent): Unit =
		builtItems += (key -> content)
	
	def isEmpty: Boolean =
		builtItems.isEmpty
	
	def nonEmpty: Boolean =
		builtItems.nonEmpty
	
	def buildDocument: LocalizedDocument =
		LocalizedDocument(builtItems.toMap)
	
}

object DocumentContext {
	
	def getDefault: DocumentContext = DocumentContext(
		documentVersion = "1.0",
		encoding = Charset.forName("UTF-8"),
		indentSize = 1
	)
	
}
