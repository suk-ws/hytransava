package cc.sukazyo.hytrans.data.hylangs

import scala.collection.mutable
import scala.util.boundary

class LanguageTree {
	
	case class LanguageItem (
		tag: LanguageTag,
		priority: Int
	) {
		
		infix def isOf (tag: LanguageTag): Boolean =
			this.tag == tag
		
		override def toString: String =
			s"$tag, $priority"
		
	}
	
	object LanguageItem {
		object ROOT extends LanguageItem(LanguageTag("ROOT"), 0)
		def apply (languageName: String, priority: Int): LanguageItem =
			LanguageItem(LanguageTag(languageName), priority)
		object OrderingByPriority extends Ordering[LanguageItem] {
			override def compare (x: LanguageItem, y: LanguageItem): Int =
				val priorities = x.priority `compareTo` y.priority
				if priorities != 0 then priorities
				else x.tag.hashCode `compareTo` y.tag.hashCode
		}
	}
	
	class Node (
		val item: LanguageItem
	) extends Comparable[Node] {
		
		override def compareTo (o: Node): Int =
			LanguageItem.OrderingByPriority.compare(item, o.item)
		
		private[hytrans] var _parent: Option[Node] = None
		private[hytrans] val _children: mutable.SortedSet[Node] =
			mutable.SortedSet.empty(using Ordering[Node].reverse)
		
		def parent: Option[Node] = _parent
		def children: List[Node] = _children.toList
		
		private[hytrans] def addChildren (children: Node*): Unit =
			_children ++= children
			_children.foreach(_._parent = Some(this))
		private[hytrans] def withChildren (children: Node*): this.type =
			addChildren(children*)
			this
		
		def traverseParent [T] (f: Node=>T): List[T] = {
			_parent.map { parent =>
				f(parent) :: parent.traverseParent(f)
			}.getOrElse(Nil)
		}
		
		private def traversingTree (using f: Node=>Unit, visited: mutable.Set[Node]): Unit = {
			if !(visited contains this) then
				f(this)
				visited += this
			_children.foreach { child =>
				if !(visited contains child) then
					child.traversingTree
			}
			_parent.foreach { parent =>
				if !(visited contains parent) then
					parent.traversingTree
			}
		}
		
		def traverseTree (f: Node=>Unit): Unit =
			val visited = mutable.Set.empty[Node]
			traversingTree(using f, visited)
		
		private def traversingChildren [T] (depth: Int)(using f: (Node, Int)=>T): List[T] =
			f(this, depth) ::
			_children.toList.flatMap { child =>
				child.traversingChildren(depth + 1)
			}
		
		def traverseChildren [T] (f: (Node, Int)=>T): List[T] =
			this.traversingChildren(0)(using f)
		
		def printTree: String =
			this.traverseChildren { (node, depth) =>
				s"${"  " * depth}${node.item.toString}"
			}.mkString("\n")
		
		override def toString: String =
			printTree
		
	}
	
	object Node {
		
		def newRoot: Node = Node(LanguageItem.ROOT)
		
		def apply (item: LanguageItem): Node =
			new Node(item)
		def apply (languageName: String, priority: Int): Node =
			Node(LanguageItem(LanguageTag(languageName), priority))
		
	}
	
	val root: this.Node = Node.newRoot
	
	def search (languageTag: LanguageTag): Option[Node] =
		boundary {
			root.traverseTree { node =>
				if node.item.isOf(languageTag) then
					boundary `break` Some(node)
			}
			None
		}
	
}
