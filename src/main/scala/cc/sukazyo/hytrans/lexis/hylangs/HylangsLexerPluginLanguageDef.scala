package cc.sukazyo.hytrans.lexis.hylangs

import cc.sukazyo.hytrans.lexis.Indent
import cc.sukazyo.std.data.EncapsulateValue

import scala.util.boundary
import scala.util.boundary.break

class HylangsLexerPluginLanguageDef extends HylangsLexerPlugin {
	
	override def tryLine (line: String): Option[List[HylangsLineLexis]] = {
		
		type CommentChars = ' ' | '\t'
		var indentChar: Option[CommentChars] = None
		var indentSize: Int = 0
		def indentIsChar (char: CommentChars): Unit =
			if indentChar.isDefined && (indentChar.get == char) then
				indentSize += 1
			else if indentChar.isEmpty then
				indentChar = Some(char)
				indentSize = 1
			else throw IllegalArgumentException(".")
		
		boundary { line foreach {
			case ' ' => indentIsChar(' ')
			case '\t' => indentIsChar('\t')
			case _ => break()
		}}
		
		val indent = indentChar match
			case Some(char) => Indent.fromChar(char, indentSize)
			case None => Indent.None
		
		val clearLine = line.drop(indentSize)
		val parameters = clearLine.split(",")
		val language = parameters(0).trim
		val priority = (if parameters.length > 1 then Some(parameters(1)) else None)
			.map(
				_.trim
					.replace("_", "")
					.replace(" ", "")
					.replace("'", "")
					.toDouble
			)
		
		Some(List(
			HylangsLineLexis.TreeLine(indent, language, priority)
		))
		
	}
	
}
