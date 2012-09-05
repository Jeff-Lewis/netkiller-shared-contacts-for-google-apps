package com.netkiller.dataupload;

import java.io.InputStream;
import java.util.ArrayList;

public interface DataUploadReader {
	
	public void init(InputStream stream);
	public ArrayList readData(int entityType);

}
