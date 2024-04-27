package cc.sukazyo.hytrans
package var_text

trait VTNode {
	
	def render (vars: Map[String, String]): String
	
	def serialize: String
	
}
