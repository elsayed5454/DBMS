package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;

public class XMLDrop {

	private File file;
	
	public XMLDrop (File file) {
		this.file = file;
	}
	
	public void Drop () {
		file.delete();
	}
}
