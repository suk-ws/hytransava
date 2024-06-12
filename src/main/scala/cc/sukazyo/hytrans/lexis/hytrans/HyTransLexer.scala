package cc.sukazyo.hytrans
package lexis.hytrans

import lexis.LineLexis

import scala.util.boundary
import scala.util.boundary.break

class HyTransLexer (
	lineLexers: List[Lexer4HyTransSimple[?]],
) {
	
	def parseFromLines (lines: List[LineLexis]): List[Lexis4HyTrans] =
		lines.map { line =>
			boundary[Lexis4HyTrans] {
				for (lexer <- lineLexers) {
					if lexer.isCurrentLexisLine(line) then
						break(lexer.parseLine(line))
				}
				LexisEmptyLine.parseLine(line)
			}
		}
	
}

object HyTransLexer {
	
	def defaultLexer (): HyTransLexer =
		HyTransLexer(defaultExtensions())
	
	def defaultExtensions (): List[Lexer4HyTransSimple[?]] =
		List(
			LexisCommentLine,
			LexisDocumentHead,
			LexisDocumentParam,
			LexisItemLine,
			LexisItemKey
			// this lexer will be used as fallback by default, so it does not need to be added manually
//			LexisEmptyLine
		)
	
}
