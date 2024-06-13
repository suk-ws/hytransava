package cc.sukazyo.hytrans.lexis.hytrans

import cc.sukazyo.hytrans.lexis.LineLexis
import cc.sukazyo.hytrans.lexis.LineLexis.LineLexisImpl

case class LexisItemKey (
	
	key: String
	
) extends Lexis4HyTrans

object LexisItemKey extends Lexer4HyTrans[LexisItemKey] {
	
	override val starterCharacter: List[Char] = Nil
	
	override def isCurrentLexisLine(line: LineLexis): Boolean =
		val head = line match
			case LineLexisImpl(raw, _, _) => raw
			case _ => return false
		true
	
	override def parseLineContented (line: LineLexisImpl): LexisItemKey = {
		// currently only support the whole line is the key.
		//  there may be others parameters specific for key after.
		LexisItemKey(line.raw)
	}
	
}
