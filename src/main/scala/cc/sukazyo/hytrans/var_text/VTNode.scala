package cc.sukazyo.hytrans.var_text

import cc.sukazyo.hytrans.var_text.VarText.RenderingSequence

/** A node of a [[VarText]].
  */
trait VTNode {
	
	val renderOrdering: Float
	
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
	def render (using sequence: RenderingSequence, vars: Map[String, String])(index: Int)
	: RenderedSegment
	
	def serialize: String
	
}
