package org.df4ze.courses.controlers;

import java.io.File;
import java.io.FileNotFoundException;

import org.df4ze.courses.resources.warc.WarcReader;
import org.df4ze.courses.resources.warc.WarcVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Scraper {

	@Autowired
	private WarcVisitor visitor;
	
	@Autowired
	private WarcReader warcReader;
	
	private File arcDirectory;
	private boolean skipValidation = false;
	
	Scraper() {

	}

	public void start() throws FileNotFoundException{
		warcReader.setArcDir(arcDirectory);
		warcReader.setVisitor(visitor);
		warcReader.setSkipValidation(skipValidation);
		
		warcReader.startVisit();
	}


	public void setArcDirectory(File arcDirectory) {
		this.arcDirectory = arcDirectory;
	}

	public void setSkipValidation(boolean b) {
		skipValidation = b;
		
	}

}
