package cc.sukazyo.hytrans
package lexis.hytrans

import lexis.LineLexis
import lexis.LineLexis.LineLexisImpl

case class LexisCommentLine (
	
	starter: Option[String],
	commentBody: String
	
) extends Lexis4HyTrans

object LexisCommentLine extends Lexer4HyTrans[LexisCommentLine] {
	
	override val starterCharacter: List[Char] = '#' :: ' ' :: '\t' :: Nil
	
	override def parseLineContented (line: LineLexisImpl): LexisCommentLine = {
		LexisCommentLine(
			Some(line.signal.toString), line.content
		)
	}
	
}
