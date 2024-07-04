package cc.sukazyo.hytrans.var_text

import cc.sukazyo.hytrans.var_text.Var.{isLegalId, IllegalVarIdException}

/** A Var is a key-value pair, where the key is a string, and the value is also a string.
  *
  * This class is used to represent a variable in the [[VarText]].
  *
  * You can just call the [[Var]] constructor to create a new Var, or use the implicit
  * conversion to create a var from a tuple of ([[String]], [[String]]), or use the extension
  * method [[Var.String_As_Var.asVar]] to convert a [[String]] to a var.
  * 
  * @since 0.1.0
  *
  * @param id The key of the variable, also known as var-id.
  *
  *           The var-id have some limitation on the characters that can be used in it.
  *           For details, see [[Var.isLegalId]]. An illegal id will cause the constructor
  *           throws [[IllegalVarIdException]].
  *
  * @param text The text content of the variable.
  */
case class Var (
	id: String,
	text: String
) {
	
	{ /// Check the var-id is legal.
		isLegalId(id) match
			case withChar if withChar.nonEmpty =>
				throw IllegalVarIdException(withChar.head) // TODO: support multiple illegals
			case _ =>
	}
	
	/** Create a new Var with the same text but different id.
	  * @since 0.1.0
	  */
	def asId (id: String): Var =
		Var(id, this.text)
	
	/** Create a new Var with the same id but different text.
	  * @since 0.1.0
	  */
	def asText (text: String): Var =
		Var(this.id, text)
	
	/** Unpack this Var into a ([[String]], [[String]]) tuple.
	  * @since 0.1.0
	  */
	def unpackKV: (String, String) =
		(id, text)
	
}

object Var {
	
	private val ID_AVAILABLE_SYMBOLS: Set[Char] =
		"_-.*/\\|:#@%&?;,~"
			.toCharArray.toSet
	
	/** The id of a var contains characters that are not allowed to contains.
	  * 
	  * When building a [[Var]] with an illegal id, this exception will be thrown.
	  * 
	  * @see [[isLegalId]] - The method to check if a character is legal in a var-id.
	  * @since 0.1.0
	  * 
	  * @param illegalChar The character found that is not allowed to be in a var-id.
	  */
	class IllegalVarIdException (illegalChar: Char)
		extends IllegalArgumentException (s"Character $illegalChar (${illegalChar.toInt}) is not allowed in a var id!")
	
	/** Is this character is a legal var-id character.
	  * 
	  * In other words, if this character is a ENGLISH  letter, a digit, or one of the following
	  * symbols (`_` `-` `.` `*` `/` `\` `|` `:` `#` `@` `%` `&` `?` `;` `,` `~`), this
	  * character is allowed to shows in the [[Var.id]], we said this character is a
	  * legal var-id character.
	  *
	  * @since 0.1.0
	  * 
	  * @return If this character is illegal, it will be packed to a [[Some]] and returned. Or
	  *         if this character is legal, the [[None]] will be returned.
	  */
	def isLegalId (c: Char): Option[Char] =
		if (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c.isDigit || ID_AVAILABLE_SYMBOLS.contains(c) then None
		else Some(c)
	
	/** Is this string a legal var-id.
	  * 
	  * If and only if the string is not empty (and not blank), and all characters in the
	  * string are legal var-id character, this string will be a legal var-id.
	  * 
	  * @see [[isLegalId(Char)]] per-character checks
	  * 
	  * @return An [[IndexedSeq]] of illegal characters in the string. If the string is legal,
	  *         This seq will be empty.
	  */
	def isLegalId (id: String): IndexedSeq[Char] =
		if id.isBlank then " ".toIndexedSeq
		else id.map(isLegalId).filter(_.isDefined).map(_.get)
	
	/** Convert a tuple of ([[String]], [[String]]) to a Var.
	  * 
	  * The first string of the tuple will be the [[Var.id]], and the second string
	  * will be the [[Var.text]]
	  *
	  * @since 0.1.0
	  */
	implicit def StrStrTuple_as_Var (tuple: (String, Any)): Var =
		Var(tuple._1, tuple._2.toString)
	
	/** @see [[asVar]] */
	implicit class String_As_Var (text: String):
		/** Convert this string text to a [[Var]].
		  * @since 0.1.0
		  * @param id the var-id.
		  * @return a [[Var]] that the [[Var.text text]] is this string, and the [[Var.id id]]
		  *         is the given id.
		  */
		def asVar (id: String): Var =
			Var(id, text)
	
}
