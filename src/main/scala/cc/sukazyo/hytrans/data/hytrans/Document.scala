package cc.sukazyo.hytrans.data.hytrans

import scala.collection.immutable.TreeMap

case class Document (
	
	items: TreeMap[String, String] = TreeMap.empty
	
)
