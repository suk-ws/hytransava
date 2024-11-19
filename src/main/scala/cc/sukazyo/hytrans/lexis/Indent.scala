package cc.sukazyo.hytrans.lexis

sealed trait Indent {
	val size: Int
	def render: String
}

object Indent {
	
	object None extends Indent {
		override val size: Int = 0
		override def render: String = ""
	}
	
	sealed trait CharacterIndent extends Indent {
		val indenting: String
		override def render: String = indenting * size
	}
	
	sealed case class Spaces (size: Int) extends CharacterIndent {
		override val indenting = " "
	}
	
	sealed case class Tabs (size: Int) extends CharacterIndent {
		override val indenting = "\t"
	}
	
	def fromChar (char: Char, size: Int): Indent = char match {
		case ' ' => Spaces(size)
		case '\t' => Tabs(size)
		case _ =>
//			throw IllegalArgumentException(s"Indent character 0x${char.toInt.toHexString} unsupported.")
			val _size = size
			val _char = char
			new CharacterIndent:
				override val size: Int = _size
				override val indenting: String = _char.toString
	}
	
}
