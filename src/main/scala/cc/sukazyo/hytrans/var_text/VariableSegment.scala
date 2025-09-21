package cc.sukazyo.hytrans.var_text

import cc.sukazyo.hytrans.var_text.VarText.RenderingContext
import cc.sukazyo.hytrans.var_text.VariableSegment.RenderedVariable

/** A variable node of a [[VarText]], contains a `var_id`.
  *
  * While rendering, this will search `var_id` in the provided [[Var]]s and renders the
  * matches [[Var.text]]. If there's no matches [[Var]] provided, this will render a placeholder
  * formatted like `${var_id}`
  */
class VariableSegment (
	var_id: String
) extends Segment {
	
	override val renderOrdering: Float = 0f
	
	override def render (using context: RenderingContext)(index: Int): RenderedVariable = {
		RenderedVariable(var_id, context.vars.get(var_id))
	}
	
	override def toString: String =
		s"var_def(id={$var_id})"
	
	override def serialize: String =
		s"{$var_id}"
	
}

object VariableSegment {
	class RenderedVariable (val var_id: String, val result: Option[String]) extends RenderedSegment {
		override def text: String =
			result.getOrElse(s"$${$var_id}")
		override def serialize: String = {
			result match
				case Some(text) => s"$${$var_id=$result}"
				case None => s"$${$var_id!!}"
		}
	}
}
