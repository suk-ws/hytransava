package cc.sukazyo.hytrans.var_text

import cc.sukazyo.hytrans.var_text.VTNodeVar.RenderedVar
import cc.sukazyo.hytrans.var_text.VarText.RenderingSequence

/** A variable node of a [[VarText]], contains a `var_id`.
  *
  * While rendering, this will search `var_id` in the provided [[Var]]s and renders the
  * matches [[Var.text]]. If there's no matches [[Var]] provided, this will render a placeholder
  * formatted like `${var_id}`
  */
class VTNodeVar (
	var_id: String
) extends VTNode {
	
	override val renderOrdering: Float = 0f
	
	override def render (using sequence: RenderingSequence, vars: Map[String, String])(index: Int): RenderedVar = {
		RenderedVar(var_id, vars.get(var_id))
	}
	
	override def toString: String =
		s"var_def(id={$var_id})"
	
	override def serialize: String =
		s"{$var_id}"
	
}

object VTNodeVar {
	class RenderedVar (var_id: String, result: Option[String]) extends RenderedSegment {
		override def text: String =
			result.getOrElse(s"$${$var_id}")
		override def serialize: String = {
			result match
				case Some(text) => s"$${$var_id=$result}"
				case None => s"$${$var_id!!}"
		}
	}
}
