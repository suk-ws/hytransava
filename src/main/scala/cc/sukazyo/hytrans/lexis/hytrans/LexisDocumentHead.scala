package cc.sukazyo.hytrans.lexis.hytrans

import cc.sukazyo.hytrans.lexis.LineLexis.LineLexisImpl

case class LexisDocumentHead (
	
	documentVersion: String
	
) extends Lexis4HyTrans

object LexisDocumentHead extends Lexer4HyTrans[LexisDocumentHead] {
	
	override val starterCharacter: List[Char] = '%' :: Nil
	
	override def parseLineContented (line: LineLexisImpl): LexisDocumentHead = {
		LexisDocumentHead(line.content)
	}
	
}
