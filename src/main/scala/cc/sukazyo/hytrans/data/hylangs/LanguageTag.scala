package cc.sukazyo.hytrans.data.hylangs

import cc.sukazyo.hytrans.data.hylangs.LanguageTag.normalizedName

case class LanguageTag private (
	id: String
) {
	
	infix def isOf (other: String): Boolean =
		id == normalizedName(other)
	
}

object LanguageTag {
	
	object ROOT extends LanguageTag("root")
	
	@throws[IllegalLanguageTagException]
	def apply (name: String): LanguageTag =
		new LanguageTag(ensuring(normalizedName(name)))
	
	class IllegalLanguageTagException (message: String, val original: String)
		extends IllegalArgumentException(message)
	
	def normalizedName (name: String): String =
		name.toLowerCase
			.replaceAll(" ", "_")
			.replaceAll("-", "_")
	
	@throws[IllegalLanguageTagException]
	def ensuring (name: String): String =
		name.foreach {
			case c if c.isLetterOrDigit =>
			case '_' =>
			case ' ' | '\t' | '\r' | '\n' =>
				throw IllegalLanguageTagException("Lang Tag cannot contains space", name)
			case ill =>
				throw IllegalLanguageTagException(s"Illegal character '$ill' in Lang Tag \"$name\"", name)
		}
		name
	
	def of (name: String): LanguageTag =
		LanguageTag(name)
	
}
