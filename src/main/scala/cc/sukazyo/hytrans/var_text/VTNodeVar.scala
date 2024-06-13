package cc.sukazyo.hytrans.var_text

/** A variable node of a [[VarText]], contains a `var_id`.
  *
  * While rendering, this will search `var_id` in the provided [[Var]]s and renders the
  * matches [[Var.text]]. If there's no matches [[Var]] provided, this will render a placeholder
  * formatted like `${var_id}`
  */
class VTNodeVar (
	var_id: String
) extends VTNode {
	
	override def render (vars: Map[String, String]): String =
		vars.getOrElse(var_id, s"$${$var_id}")
	
	override def toString: String =
		s"var_def(id={$var_id})"
	
	override def serialize: String =
		s"{$var_id}"
	
}
