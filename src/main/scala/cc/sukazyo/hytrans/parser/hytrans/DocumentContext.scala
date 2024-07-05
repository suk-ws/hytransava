package cc.sukazyo.hytrans.parser.hytrans

import cc.sukazyo.hytrans.data.hytrans.{LocalizedContent, LocalizedDocument}
import cc.sukazyo.hytrans.lexis.hytrans.LexisItemLine.LinePrefix
import cc.sukazyo.hytrans.parser.hytrans.DocumentContext.LineWrapMode

import java.nio.charset.Charset
import scala.collection.mutable

case class DocumentContext (
	
	documentVersion: String,
	
	var encoding: Charset,
	var indentSize: Int,
	var lineWrapMode: LineWrapMode
	
) {
	
	private val builtItems: mutable.Map[String, LocalizedContent] = mutable.SeqMap.empty
	private val beforeBuiltCleanupTasks: mutable.Map[Class[?], DocumentContext=>Unit] = mutable.SeqMap.empty
	
	def addItem (key: String, content: LocalizedContent): Unit =
		builtItems += (key -> content)
	
	def isEmpty: Boolean =
		builtItems.isEmpty
	
	def nonEmpty: Boolean =
		builtItems.nonEmpty
	
	def registerOnDocumentEnd (owner: Class[?])(program: DocumentContext=>Unit): Unit =
		beforeBuiltCleanupTasks += (owner -> program)
	def removeOnDocumentEnd (owner: Class[?]): Unit =
		beforeBuiltCleanupTasks.remove(owner)
	
	def buildDocument: LocalizedDocument =
		beforeBuiltCleanupTasks.values.foreach(_(this))
		LocalizedDocument(builtItems.toMap)
	
}

object DocumentContext {
	
	def getDefault: DocumentContext = DocumentContext(
		documentVersion = "1.0",
		encoding = Charset.forName("UTF-8"),
		indentSize = 1,
		lineWrapMode = LineWrapMode.Auto
	)
	
	sealed trait LineWrapMode:
		def shouldIndent (linePrefix: LinePrefix): Boolean =
			linePrefix match
				case LinePrefix.normal => this.shouldIndentNormally
				case LinePrefix.hard_break => true
				case LinePrefix.non_break => false
		def shouldIndentNormally: Boolean
	object LineWrapMode {
		
		case object Auto extends LineWrapMode:
			override def shouldIndentNormally: Boolean = true
		case object Manual extends LineWrapMode:
			override def shouldIndentNormally: Boolean = false
		
		def byName (name: String): Option[LineWrapMode] =
			name match
				case "auto" | "yes" | "true" => Some(Auto)
				case "manual" | "no" | "false" => Some(Manual)
				case _ => None
		
	}
	
}
