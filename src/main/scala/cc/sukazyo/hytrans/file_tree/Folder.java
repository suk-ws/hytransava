package cc.sukazyo.hytrans.file_tree;

public interface Folder extends Node {
	
	String getName();
	
	Node[] getChildren();
	
}
