package cc.sukazyo.hytrans
package lexis

trait LineLexis

object LineLexis {
	
	class LineLexisEmpty extends LineLexis
	
	case class LineLexisImpl (
		raw: String,
		signal: Char,
		content: String
	) extends LineLexis
	
	def apply (): LineLexisEmpty =
		LineLexisEmpty()
	
	def apply (raw: String, signal: Char, content: String): LineLexisImpl =
		LineLexisImpl(raw, signal, content)
	
	def fromLine (line: String): LineLexis =
		if (line.contains("\n") || line.contains("\r"))
			throw IllegalArgumentException("The next-line character (\\r or \\n) cannot be in one single line.")
		if line.isEmpty then
			LineLexis()
		else
			LineLexis(line, line.head, line.drop(1))
	
}
