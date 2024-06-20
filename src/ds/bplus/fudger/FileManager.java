package ds.bplus.fudger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

//File Manager is used for creation of files, reading and writing, serializing and deserializing of data
public class FileManager {
	private RandomAccessFile file;
	private int bufferSize;
	
	//Creating new file
	public FileManager(String fileName) {
		try {
			file = new RandomAccessFile(fileName, "rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.bufferSize = 4;
	}
	
	//Reads the page in the position indicated 
	public int readInt() {
		byte[] buffer = new byte[bufferSize];	//Creating a static buffer, byte array
		try {
			file.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return deserialize(buffer);
	}
	
	//Deserialization
	//Begins the deserialization process of the buffer
	private int deserialize(byte[] buffer) {
		java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(bufferSize);
		bb.put(buffer);
		bb.position(0);
	    return bb.getInt();
	}
}
