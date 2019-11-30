package eg.edu.alexu.csd.oop.db.cs33;

import java.io.File;

public class DTDDrop {

	private File file;
	
	public DTDDrop(File file) {
		this.file = file ;
	}

	public void Drop () {
		file.delete();
	}
}
