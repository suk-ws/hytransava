package cc.sukazyo.hytrans.var_text

import cc.sukazyo.hytrans.var_text.VarText.RenderingContext

/** A node/segment of a [[VarText]].
  *
  * @since 0.1.0
  */
trait Segment {
	
	/** Defines in which render loop this segment is rendered.
	  *
	  * The smaller this number is, the earlier this segment is rendered. You can set this value
	  * bigger that the segment that this is depended, to ensure the depended on segment is
	  * rendered when this segment is rendering.
	  *
	  * For example, [[LiteralSegment]] is `-100`. [[VariableSegment]] is `0`.
	  * 
	  * @since 0.1.0
	  */
	val renderOrdering: Float
	
	/** Render this node under a render context.
	  * 
	  * This method will be called when the [[VarText]] need to be rendered. It should render to
	  * a [[RenderedSegment]], which can be directly translate to a [[String]] text segment to
	  * merge to the result, and should also contain necessary information for other segments
	  * to run a conditional render.
	  * 
	  * @param context The render context that runs this render.
	  * @param index In which place that this segment is in the VarText. Starts with `0` like
	  *              normal list. `context.sequence(index)` should always get this segment.
	  * @return The rendered [[RenderedSegment]].
	  *
	  * @since 0.1.0
	  */
	def render (using context: RenderingContext)(index: Int): RenderedSegment
	
	/** The serialized segment in serialized VarText.
	  *
	  * No guarantee to the connected string of serialized segment of a VarText can parse to
	  * the same VarText, due to there are many variants and settings can be configured for
	  * parsing.
	  * 
	  * @since 0.1.0
	  */
	def serialize: String
	
	/** The string serializer, but for better inspection for debugging.
	  *
	  * Under this serializer, every segment will always have one (or more, if the text have
	  * multilines) line. And there are always the type name of the segment.
	  *
	  * The implementation of this method should take accuracy as first consideration.
	  * 
	  * @since 0.1.0
	  */
	override def toString: String =
		super.toString
	
}
