package cc.sukazyo.hytrans.var_text

import cc.sukazyo.hytrans.var_text.SingularPluralSegment.{RenderedSingularPluralSegment, SearchDirection, SingularPluralResultType}
import cc.sukazyo.hytrans.var_text.VarText.RenderingContext
import cc.sukazyo.hytrans.var_text.VariableSegment.RenderedVariable

import scala.util.boundary
import scala.util.boundary.break

class SingularPluralSegment (
	singular: String,
	plural: String,
	direction: SearchDirection
) extends Segment {
	
	override val renderOrdering: Float = 20f
	
	override def render (using context: RenderingContext)(index: Int): RenderedSegment = {
		
		val searchSequences =
			direction match
				// ":x/x:", before this segments
				case SearchDirection.Front => context.sequence.indices.dropRight(context.sequence.length - index).reverse
				// ":x/x:>", after this segments
				case SearchDirection.Back => context.sequence.indices.drop(index + 1)
		
		val variable: Option[RenderedVariable] = boundary {
			for (i <- searchSequences) {
				context.sequence(i) match
					case vari: RenderedVariable => break(Some(vari))
					case _ =>
			}
			break(None)
		}
		
		val isPlural = variable.flatMap(_.result).exists(x => x.toInt > 1)
		
		RenderedSingularPluralSegment(
			if isPlural then plural else singular,
			if isPlural then SingularPluralResultType.Plural else SingularPluralResultType.Singular,
			direction
		)
		
	}
	
	override def toString: String =
		s"singular_plural[$singular/$plural]"
	
	override def serialize: String = s":$singular/$plural:${
		direction match
			case SearchDirection.Front => ""
			case SearchDirection.Back => ">"
	}"
	
}

object SingularPluralSegment {
	
	enum SearchDirection {
		/** This type of direction search the variable in front of this segment. like `{variable} :it:` */
		case Front
		/** This type of direction search the variable after this segment. like `:it: {variable}` */
		case Back
	}
	
	enum SingularPluralResultType {
		case Singular
		case Plural
	}
	
	class RenderedSingularPluralSegment (
		result: String, val resultType: SingularPluralResultType, val searchDirection: SearchDirection
	) extends RenderedSegment {
		override def text: String = result
		override def serialize: String = s":${
			resultType match {
				case SingularPluralResultType.Singular => "⨀"
				case SingularPluralResultType.Plural => "⨁"
			}
		}$result:${
			searchDirection match
				case SearchDirection.Front => ""
				case SearchDirection.Back => ">"
		}"
	}
	
}
