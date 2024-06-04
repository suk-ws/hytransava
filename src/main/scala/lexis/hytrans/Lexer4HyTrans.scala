package cc.sukazyo.hytrans
package lexis.hytrans

import lexis.LineLexis
import lexis.LineLexis.LineLexisImpl

trait Lexer4HyTrans [T <: Lexis4HyTrans] extends Lexer4HyTransSimple[T] {
	
	val starterCharacter: List[Char]
	
	override def isCurrentLexisLine (line: LineLexis): Boolean =
		starterCharacter.exists {
			line match
				case LineLexisImpl(_, starter, _) if starter == _ => true
				case _ => false
		}
	
	override def parseLine (line: LineLexis): T =
		line match
			case _line: LineLexisImpl => parseLineContented(_line)
			case _ => throw IllegalArgumentException(s"Got unexpected LineLexis type ${line.getClass} which is unsupported for standard lexer.")
	
	def parseLineContented (line: LineLexisImpl): T
	
}
