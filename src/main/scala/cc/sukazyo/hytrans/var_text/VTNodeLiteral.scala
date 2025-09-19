package cc.sukazyo.hytrans.var_text

import cc.sukazyo.hytrans.var_text.VTNodeLiteral.RenderedLiteral
import cc.sukazyo.hytrans.var_text.VarText.RenderingSequence

/** A literal node of a [[VarText]].
  * 
  * Contains a [[String]] literal param `text`. And this will be rendered as is.
  */
case class VTNodeLiteral (
	text: String
) extends VTNode {
	
	override val renderOrdering: Float = -100f
	
	override def render (using sequence: RenderingSequence, vars: Map[String, String])(index: Int): RenderedLiteral =
		RenderedLiteral(text)
	
	override def toString: String =
		val prefix           = "literal|"
		val prefix_following = "       |"
		val pt = text.split('\n')
		(pt.headOption.map(prefix + _).getOrElse("") ::
			pt.drop(1).map(prefix_following + _).toList)
			.mkString("\n")
	
	override def serialize: String =
		text
			.replaceAll("/\\{", "//\\{")
			.replaceAll("\\{", "/\\{")
	
}

object VTNodeLiteral {
	class RenderedLiteral (_text: String) extends RenderedSegment {
		override def text: String = text
		override def serialize: String =
			text
				.replaceAll("/\\{", "//\\{")
				.replaceAll("\\{", "/\\{")
	}
}
