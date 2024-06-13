package cc.sukazyo.hytrans.lexis.hytrans

import cc.sukazyo.hytrans.lexis.LineLexis
import cc.sukazyo.hytrans.lexis.LineLexis.LineLexisImpl

trait Lexer4HyTransSimple [T <: Lexis4HyTrans] {
	
	def isCurrentLexisLine (line: LineLexis): Boolean
	
	def parseLine (line: LineLexis): T
	
}
