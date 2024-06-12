package cc.sukazyo.hytrans
package lexis.hytrans

import lexis.LineLexis.LineLexisImpl

case class LexisDocumentParam (
	
	key: String,
	value: String
	
) extends Lexis4HyTrans

object LexisDocumentParam extends Lexer4HyTrans[LexisDocumentParam] {
	
	override val starterCharacter: List[Char] = '&' :: Nil
	
	override def parseLineContented (line: LineLexisImpl): LexisDocumentParam = {
		val (key, value) = line.content.split("=", 2) match
			case Array(key, value) => (key, value)
			case Array(key) => (key, "")
			case _ => ("", "")
		LexisDocumentParam(key, value)
	}
	
}
