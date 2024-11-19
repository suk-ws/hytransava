package cc.sukazyo.hytrans.lexis.hylangs

import cc.sukazyo.hytrans.lexis.Indent

trait HylangsLineLexis

object HylangsLineLexis {
	
	class CommentLine extends HylangsLineLexis
	
	case class TreeLine (
		indent: Indent,
		lang: String,
		priority: Option[Double]
	) extends HylangsLineLexis
	
}
