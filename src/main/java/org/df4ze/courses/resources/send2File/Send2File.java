package org.df4ze.courses.resources.send2File;

import java.io.IOException;
import java.util.Collection;

public interface Send2File {

	void openFile( String filePath )throws IOException;
	
	void createFile( String filePath )throws IOException;
	
	void closeFile();
	
	void setWriteHeader(boolean writeHeader);
	
	void addRow( EntityWrapper wrapper );
	
	void addRows( Collection<EntityWrapper> wrappers );
}
