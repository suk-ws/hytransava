package cc.sukazyo.hytrans.file_tree.filesystem

import cc.sukazyo.hytrans.file_tree.{Folder, Node}

import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters.*
import scala.collection.mutable.ListBuffer

class FSFolder private (path: Path) extends Folder {
	
	if (!Files.isDirectory(path)) {
		throw Exception(s"Input path $path is not a directory")
	}
	
	private lazy val name = path.toAbsolutePath.normalize.getFileName.toString
	override def getName: String = name
	
	override def getChildren: Array[Node] = {
		Files.list(path).iterator.asScala.map { child =>
			if (Files.isDirectory(child)) {
				new FSFolder(child)
			} else {
				new FSFile(child)
			}
		}.toArray
	}
	
}

object FSFolder {
	
	def fromPath (path: Path): FSFolder = new FSFolder(path)
	
}
