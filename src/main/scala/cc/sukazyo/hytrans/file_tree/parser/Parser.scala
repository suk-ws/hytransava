package cc.sukazyo.hytrans.file_tree.parser

import cc.sukazyo.hytrans.data.hylangs.LanguageTag
import cc.sukazyo.hytrans.file_tree.{File, Folder}

import java.io.InputStream

object Parser {
	
	private class FolderRoot extends FolderImpl("") with TransNode.Root
	
	private class FolderImpl (_name: String, _languageTag: Option[LanguageTag] = None) extends TransNode.Folder {
		private[Parser] var _parent: Option[FolderImpl] = None
		private[Parser] def setParent(parent: FolderImpl): this.type =
			_parent = Some(parent); this
		private[Parser] var _children: List[FolderImpl|TransNode.Document] = Nil
		
		override def children: List[TransNode] = _children
		override def parent: Option[FolderImpl|TransNode.Document] = _parent
		
		override def language: LanguageTag =
			_languageTag.getOrElse(this.parent.map(_.language).getOrElse(LanguageTag.ROOT))
		
		override def name: String = _name
		
	}
	
	private class DocumentImpl (
		_parent: FolderImpl,
		_name: String, _file: File, _languageTag: Option[LanguageTag] = None,
	) extends TransNode.Document {
		override def parent: Option[TransNode] = Some(_parent)
		override def name: String = _name
		override def language: LanguageTag =
			_languageTag.getOrElse(this.parent.map(_.language).getOrElse(LanguageTag.ROOT))
		override def readStream: InputStream = _file.readStream
	}
	private object DocumentImpl {
		def fromFile (parent: FolderImpl, file: File): DocumentImpl = {
			val nameDef = file.getName
			val (name, lang) =
				if (nameDef.startsWith("_")) {
					("_", nameDef.drop(1))
				} else {
					nameDef.splitAt(nameDef.lastIndexOf("%"))
				}
			DocumentImpl(
				parent, name, file,
				if lang.isBlank then None else Some(LanguageTag.of(lang))
			)
		}
	}
	
	def fromFileTree (tree: Folder): TransNode.Root = {
		
		val newRoot = FolderRoot()
		
		def foreachFolder (parent: FolderImpl, sourceFolder: Folder): Unit = {
			for (child <- sourceFolder.getChildren) {
				val childNode: FolderImpl|DocumentImpl = child match {
					case folder: Folder => FolderImpl(child.getName).setParent(parent)
					case file: File => DocumentImpl.fromFile(parent, file)
				}
				parent._children = parent._children.appended(childNode)
			}
		}
		
		foreachFolder(newRoot, tree)
		
		newRoot
		
	}
	
}
