package cc.sukazyo.hytrans.lexis.hytrans

import cc.sukazyo.hytrans.lexis.hytrans.LexisItemLine.LinePrefix
import cc.sukazyo.hytrans.lexis.LineLexis.LineLexisImpl

case class LexisItemLine (
	
	prefixTag: LinePrefix,
	content: String
	
) extends Lexis4HyTrans

object LexisItemLine extends Lexer4HyTrans[LexisItemLine] {
	
	enum LinePrefix(val tag: Char):
		case normal extends LinePrefix('|')
		case hard_break extends LinePrefix('/')
		case non_break extends LinePrefix('\\')
	object LinePrefix:
		val prefixes: Map[Char, LinePrefix] = LinePrefix.values.map(x => x.tag -> x).toMap
		def supportedPrefixes: List[Char] =
			prefixes.keys.toList
		def getFromPrefixTag (tag: Char): Either[IllegalArgumentException, LinePrefix] =
			prefixes.get(tag) match
				case Some(x) => Right(x)
				case None => Left(IllegalArgumentException(s"LinePrefix is unsupported for '$tag' is tag"))
	
	override val starterCharacter: List[Char] = LinePrefix.supportedPrefixes
	
	override def parseLineContented (line: LineLexisImpl): LexisItemLine = {
		LexisItemLine(
			LinePrefix.getFromPrefixTag(line.signal).toTry.get,
			line.content
		)
	}
	
}
