package cc.sukazyo.hytrans.file_tree;

import java.io.IOException;
import java.io.InputStream;

public interface File extends Node {
	
	InputStream readStream() throws IOException;
	
}
