package eu.winwinit.bcc.model;

import java.util.List;

public class DocumentFile {
	
	private String filename;
    private List<Byte> filebytes;
	
    public DocumentFile() {}

	public DocumentFile(String filename, List<Byte> filebytes) {
		super();
		this.filename = filename;
		this.filebytes = filebytes;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<Byte> getFilebytes() {
		return filebytes;
	}

	public void setFilebytes(List<Byte> filebytes) {
		this.filebytes = filebytes;
	}
  
}
