package cc.sukazyo.hytrans.parser.hytrans

import scala.collection.mutable

case class DocItem (
	key: String,
	lines: List[String]
) {
	
	def toKVItem: (String, String) =
		(key, lines.mkString("\n"))
	
}

object DocItem {
	
	class Builder (key: String) {
		
		private val linesBuffer = mutable.ListBuffer.empty[String]
		
		def addLine (line: String): Unit =
			linesBuffer += line
		
		def isEmpty: Boolean =
			linesBuffer.isEmpty
		
		def nonEmpty: Boolean =
			linesBuffer.nonEmpty
		
		def build: DocItem =
			DocItem(key, linesBuffer.toList)
		
	}
	
}
