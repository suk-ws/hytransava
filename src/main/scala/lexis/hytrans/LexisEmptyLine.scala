package cc.sukazyo.hytrans
package lexis.hytrans

import lexis.LineLexis
import lexis.LineLexis.LineLexisEmpty

class LexisEmptyLine extends Lexis4HyTrans

object LexisEmptyLine extends Lexer4HyTransSimple[LexisEmptyLine] {
	
	override def isCurrentLexisLine (line: LineLexis): Boolean =
		line match
			case _: LineLexisEmpty => true
			case _ => false
	
	override def parseLine (line: LineLexis): LexisEmptyLine =
		LexisEmptyLine()
	
}
