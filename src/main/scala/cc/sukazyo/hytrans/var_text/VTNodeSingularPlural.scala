package cc.sukazyo.hytrans.var_text

import cc.sukazyo.hytrans.var_text.VarText.RenderingSequence

class VTNodeSingularPlural (
	singular: String,
	plural: String
) extends VTNode {
	
	override val renderOrdering: Float = 20f
	
	override def render (using sequence: RenderingSequence, vars: Map[String, String])(index: Int): RenderedSegment =
		???
	
	override def serialize: String = ???
	
}
