package cc.sukazyo.hytrans.var_text

/** A node of a [[VarText]].
  */
trait VTNode {
	
	/** Render this node with the given `(var-key -> value)` map.
	  * 
	  * This method will be called when the [[VarText]] need to be rendered. It should renders
	  * the [[String]] text with the given variables for this node. The rendered result will be
	  * connected to the other nodes and results the whole [[VarText]] rendered text.
	  * 
	  * @param vars A series of [[Var]]s, serialized to a (var-key -> value) map for convenience
	  *             searching.
	  * @return The rendered [[String]] text of this node.
	  */
	def render (vars: Map[String, String]): String
	
	def serialize: String
	
}
