package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.data.hytrans.Document

import java.nio.charset.Charset
import scala.collection.immutable.TreeMap
import scala.collection.mutable.ListBuffer

case class DocumentContext (
	
	documentVersion: String,
	
	var encoding: Charset,
	var indentSize: Int,
	
) {
	
	private val builtItems: ListBuffer[DocItem] = ListBuffer.empty[DocItem]
	
	def addItem (item: DocItem): Unit =
		builtItems += item
	
	def isEmpty: Boolean =
		builtItems.isEmpty
	
	def nonEmpty: Boolean =
		builtItems.nonEmpty
	
	def buildDocument: Document =
		Document(TreeMap.from[String, String](
			builtItems.map(_.toKVItem)
		))
	
}

object DocumentContext {
	
	def getDefault: DocumentContext = DocumentContext(
		documentVersion = "1.0",
		encoding = Charset.forName("UTF-8"),
		indentSize = 1
	)
	
}
