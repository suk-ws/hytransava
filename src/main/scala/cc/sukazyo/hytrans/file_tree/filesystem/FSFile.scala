package cc.sukazyo.hytrans.file_tree.filesystem

import cc.sukazyo.hytrans.file_tree.File

import java.io.{FileInputStream, InputStream}
import java.nio.file.{Files, Path}

class FSFile private[filesystem] (file: Path) extends File {
	
	private lazy val name = file.toAbsolutePath.normalize.getFileName.toString
	override def getName: String = name
	
	override def readStream (): InputStream = {
		Files.newInputStream(file)
	}
	
}
