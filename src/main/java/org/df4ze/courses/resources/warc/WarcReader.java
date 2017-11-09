package org.df4ze.courses.resources.warc;

import java.io.ByteArrayOutputStream;
//import org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.archive.io.ArchiveRecord;
import org.archive.io.ArchiveRecordHeader;
import org.archive.io.warc.WARCReaderFactory;
import org.df4ze.courses.resources.Logger;
import org.springframework.stereotype.Component;

@Component
public class WarcReader {

	private File arcDir = null;
	public void setArcDir(File arcDir) throws FileNotFoundException {
		if( !arcDir.exists() )
			throw new FileNotFoundException();
		this.arcDir = arcDir;
	}
	
	private WarcVisitor visitor= null;
	public void setVisitor(WarcVisitor visitor) {
		this.visitor = visitor;
	}
	
	private long entriesNB = 0; 
	private boolean skipValidation = false;


	WarcReader() {
	}
	
	protected ArrayList<File> getArcFiles(){
		File arcFiles[] = arcDir.listFiles(new FilenameFilter() 
		{
			public boolean accept(File dir, String name) 
			{
				return name.endsWith(".warc.gz") /*|| name.endsWith(".warc")*/;
			}
		});
		
		if( arcFiles == null )
			throw new RuntimeException("Folder seems empty or maybe you insert a fileName instead of a folderName?");
		
		return new ArrayList<File>(  Arrays.asList(arcFiles) );
	}
	
	public HashMap<String, String> startVisit(  ){
		HashMap<String, String> URLS = new HashMap<String, String>();
		
		ArrayList<File> arcFiles = getArcFiles();
		for (File arcFile : arcFiles){
			System.out.println("Reading " + arcFile.getName());
			Logger.write("\n\nReading " + arcFile.getName());
			
			try {
				if( !skipValidation ){
					System.out.println("Calculating size....");
					entriesNB = WARCReaderFactory.get(arcFile).validate().size();
					
					System.out.println("Done!");
				}
				
				long place = 0;
				Iterator<ArchiveRecord> archIt = WARCReaderFactory.get(arcFile).iterator();
				while (archIt.hasNext()) {
					ArchiveRecord rec = archIt.next();
					
					if (rec != null){
						ArchiveRecordHeader meta = rec.getHeader();
						if (meta.getMimetype().trim().indexOf("request") != -1 && meta.getMimetype().trim().startsWith("application/http")) {


						}else if(meta.getMimetype().trim().indexOf("msgtype=response"/*request"*/) != -1 && meta.getMimetype().trim().startsWith("application/http")){
							place++;
							visitRecord(rec, place);
							
						}
							
					}
				}
		
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return URLS;
	}
	

	
	protected void visitRecord( ArchiveRecord rec, long place ){
		ByteArrayOutputStream baosRequest = null;
		try{
			baosRequest = new ByteArrayOutputStream();
			rec.dump(baosRequest);
			ArchiveTransformer at = new ArchiveTransformer(baosRequest, null);

			// prepa des var
			String url = rec.getHeader().getUrl();
			
			String txt = "page : "+place+"/"+entriesNB;
			if(skipValidation)
				txt = "page : "+place+"";
			visitor.indexify(url, at.ArchiveBody, txt);

		} catch (IOException e) {
			System.err.println("Error reading Record : "+e.getMessage());
			
		}finally {
			if (baosRequest != null) {
				try {
					baosRequest.close();
				} catch (IOException e) {
				}
			}
			
		}
	}

	public void setSkipValidation(boolean skipValidation) {
		this.skipValidation = skipValidation;
		
	}
	
}
