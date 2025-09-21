package cc.sukazyo.hytrans.var_text

import cc.sukazyo.hytrans.var_text.Var.isLegalId
import cc.sukazyo.hytrans.var_text.VarText.RenderingContext

import scala.collection.mutable.ListBuffer

/** A text/string template that may contain some named replaceable variables, or some more
  * complex conditional-rendering segments. It's concept may be similar with scala's
  * `StringContext` or `GString` in groovy, aiming to support the rich text translation for
  * Hyper Translations.
  *
  * A [[VarText]] can contains a stream of [[Segment]]s. Depending on the enabled extensions,
  * the available segment type is variable. But there are two basic type: [[LiteralSegment]]
  * that is just plain literal text; and [[VariableSegment]] that will be replaced by the matched
  * vars.
  *
  * This can be rendered to a native [[String]] by calling [[render]] method with a set of [[Var]]
  * variables.
  * 
  * @since 0.1.0
  */
trait VarText {
	
	val nodes: List[Segment]
	
	/** Render this VarText with the given `(var-key -> value)` map.
	  * @since 0.1.0
	  */
	def render (vars: Map[String, String]): String =
		this.postRender(
			this.preRender(vars)
		)
	
	/** Render this VarText with the given [[Var]]s seq.
	  * @since 0.1.0
	  */
	def render (vars: Var*): String =
		render(Map.from(vars.toList.map(_.unpackKV)))
	
	private def preRender (vars: Map[String, String]): List[RenderedSegment] = {
		
		val renderingOrderingList = nodes.map(_.renderOrdering).distinct.sorted
		given context: RenderingContext = RenderingContext(
			vars = vars,
			sequence = ListBuffer.from(nodes)
		)
		
		for (currOrderId <- renderingOrderingList) {
			for (currNodeIndex <- context.sequence.indices) {
				val i = context.sequence(currNodeIndex)
				i match {
					case node: Segment =>
						context.sequence.update(
							currNodeIndex,
							node.render(currNodeIndex)
						)
					case _ =>
				}
			}
		}
		
		context.sequence.filter(_.isInstanceOf[RenderedSegment]).toList.asInstanceOf[List[RenderedSegment]]
		
	}
	
	private def postRender (sequence: List[RenderedSegment]): String =
		sequence.map(_.text).mkString
	
	/** Inspect the nodes of this VarText.
	  * 
	  * Each node will be rendered to a line with the node types prefix.
	  * 
	  * @since 0.1.0
	  */
	override def toString: String =
		nodes.map(_.toString).mkString("\n")
	
	/** Serialize this VarText to a template string.
	  *
	  * The return template string will just seem like the original template string.
	  *
	  * Bot it's no guarantee to the template can parse to the same VarText, due to there are
	  * many variants and settings can be configured for parsing.
	  *
	  * @since 0.1.0
	  */
	def serialize: String =
		nodes.map(_.serialize).mkString
	
}

/** Collections about [[VarText]] utils. Contains:
  *
  *  - [[VarText]] constructor
  *  - [[VarText]] parser
  *  - [[VarText]] to [[String]] convertor using the simple [[VarText.preRender]] renderer
  */
object VarText {
	
	type RenderingSequence = ListBuffer[RenderedSegment|Segment]
	
	class RenderingContext (
		val vars: Map[String, String],
		val sequence: RenderingSequence
	)
	
	/** Most basic [[VarText]] constructor, convert a series [[Segment]] to [[VarText]]
	  */
	def apply (_nodes: Segment*): VarText = new VarText:
		override val nodes: List[Segment] = _nodes.toList
	
	/** Get the simplist [[String]] rendered result of this [[VarText]].
	  * 
	  * Provides a simple way to use a [[VarText]] as a [[String]] when there's no need to pass
	  * [[Var]]s to render.
	  */
	implicit def VarText_is_String (varText: VarText): String =
		varText.render()
	
	private val symbol_escape = '/'
	private val symbol_var_start = '{'
	private val symbol_var_end = '}'
	
	/** Parse a serialized VarText template string to a [[VarText]] object.
	  * 
	  * In the current standard, the `{<param>}` will be parsed to a [[VariableSegment]], unless it
	  * is escaped by the escape char `/`; And the escape char can and can only escape [[VariableSegment]]
	  * starter `{` or escape char `/` itself, any other chars following the escape char will
	  * be treated both escape char itself and the following char as a normal char; And all others
	  * will be parsed to [[LiteralSegment]].
	  * 
	  * @since 2.0.0
	  */
	def apply (template: String): VarText = {
		
		val _nodes = collection.mutable.ListBuffer[Segment]()
		
		def newBuffer = StringBuilder()
		var buffer: StringBuilder = newBuffer
		def pushc (c: Char): Unit =
			buffer += c
		def buffer2literal(): Unit =
			_nodes += LiteralSegment(buffer.toString)
			buffer = newBuffer
		def buffer2var(): Unit =
			_nodes += VariableSegment(buffer.toString drop 1)
			buffer = newBuffer
		sealed trait State
		case class in_escape(it: Char) extends State
		case object literal extends State
		case object in_var_def extends State
		var state: State = literal
		
		template.foreach { i =>
			
			def push(): Unit =
				buffer += i
			
			state match
				case in_escape(e) =>
					i match
						case f if f == symbol_var_start =>
							state = literal
							push()
						case f if f == symbol_escape =>
							state = literal
							push()
						case _ =>
							state = literal
							pushc(e)
							push()
				case _: in_var_def.type =>
					i match
						case f if f == symbol_var_end =>
							buffer2var()
							state = literal
						case _ if isLegalId(i).nonEmpty =>
							push()
						case _ =>
							state = literal
							push()
				case _: literal.type =>
					i match
						case f if f == symbol_escape =>
							state = in_escape(i)
						case f if f == symbol_var_start =>
							buffer2literal()
							state = in_var_def
							push()
						case _ =>
							push()
			
		}
		
		state match
			case in_escape(e) =>
				pushc(e)
			case _ =>
		buffer2literal()
		
		new VarText:
			override val nodes: List[Segment] =
				_nodes.toList
					.filterNot {
						case LiteralSegment(text) if text.isEmpty =>
							true
						case _ => false
					}
		
	}
	
}
