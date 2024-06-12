package cc.sukazyo.hytrans
package parser.hytrans

import java.nio.charset.Charset
import scala.collection.mutable.ListBuffer

case class DocumentContext (
	
	documentVersion: String,
	
	var encoding: Charset,
	var indentSize: Int,
	
) {
	
	private val builtKeys: ListBuffer[DocItem] = ListBuffer.empty[DocItem]
	
	def addItem (item: DocItem): Unit =
		builtKeys += item
	
	def toItemKVMap: Map[String, String] =
		builtKeys
			.map(_.toKVItem)
			.toMap
	
}

object DocumentContext {
	
	def getDefault: DocumentContext = DocumentContext(
		documentVersion = "1.0",
		encoding = Charset.forName("UTF-8"),
		indentSize = 1
	)
	
}
