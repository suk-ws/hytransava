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
		new LanguageTag(ensuring(name))
	
	class IllegalLanguageTagException (message: String, val original: String)
		extends IllegalArgumentException(message)
	
	/**
	  * Normalize a language tag name.
	  *
	  * This will
	  *  - convert all characters to lower case
	  *  - replace all spaces with underscore
	  *  - replace all dashes with underscore
	  *
	  * So that the Lang Tag can be used as a valid, strict formatted identifier.
	  *
	  * @param name the name of the lang tag
	  * @return the normalized name
	  */
	def normalizedName (name: String): String =
		name.toLowerCase
			.replaceAll(" ", "_")
			.replaceAll("-", "_")
	
	/**
	  * Ensure the name of a language tag is valid.
	  *
	  * This will ensure that the name contains only English lowercased letters, digits, and
	  * underscores.
	  * Any of the other characters will be considered as illegal and will throw an exception.
	  *
	  * @param name the name that may be a lang tag, but may also not in a strict formatted
	  * @return the name itself
	  * @throws IllegalLanguageTagException when the name is not in the strict formatted.
	  */
	@throws[IllegalLanguageTagException]
	def ensuring (name: String): String =
		name.foreach {
			case c if 'a' to 'z' contains c =>
			case c if '0' to '9' contains c =>
			case c if 'A' to 'Z' contains c =>
				throw IllegalLanguageTagException("Lang Tag must be in lower cased", name)
			case c if c.isLetter =>
				throw IllegalLanguageTagException("Lang Tag must be in English characters", name)
			case '_' =>
			case '-' =>
				throw IllegalLanguageTagException("dashes in Lang Tag must be converted to underscores", name)
			case ' ' | '\t' | '\r' | '\n' =>
				throw IllegalLanguageTagException("Lang Tag cannot contains space", name)
			case ill =>
				throw IllegalLanguageTagException(s"Illegal character '$ill' in Lang Tag \"$name\"", name)
		}
		name
	
	@throws[IllegalLanguageTagException]
	def of (name: String): LanguageTag =
		LanguageTag(normalizedName(name))
	
}
