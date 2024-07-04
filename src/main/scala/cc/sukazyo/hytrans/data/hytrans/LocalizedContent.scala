package cc.sukazyo.hytrans.data.hytrans

import cc.sukazyo.hytrans.var_text.VarText

case class LocalizedContent (
	raw: String
) {
	
	lazy val text: VarText = VarText(raw)
	
}
