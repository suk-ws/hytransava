package cc.sukazyo.hytrans
package lexis.hytrans

import lexis.LineLexis
import lexis.LineLexis.LineLexisImpl

trait Lexer4HyTransSimple [T <: Lexis4HyTrans] {
	
	def isCurrentLexisLine (line: LineLexis): Boolean
	
	def parseLine (line: LineLexis): T
	
}
