package cc.sukazyo.hytrans.file_tree.parser

import cc.sukazyo.hytrans.data.hylangs.LanguageTag

import java.io.InputStream

sealed trait TransNode {
	
	def parent: Option[TransNode]
	
	def name: String
	def fqn: String = parent.map(_.fqn).getOrElse("") + "." + name
	
	def language: LanguageTag
	
}

object TransNode {
	
	trait Root extends Folder {
		def parent: None.type = None
	}
	
	trait Folder extends TransNode {
		def children: List[TransNode]
	}
	
	trait Document extends TransNode {
		def readStream: InputStream
	}
	
}
