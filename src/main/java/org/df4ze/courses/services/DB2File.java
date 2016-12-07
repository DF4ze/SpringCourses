package org.df4ze.courses.services;

import java.io.IOException;

import org.df4ze.courses.models.AbstractEntity;
import org.df4ze.courses.resources.repositories.GenericEntitiesRepository;
import org.df4ze.courses.resources.send2File.Send2File;
import org.df4ze.courses.resourcesImpl.send2File.GenericEntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DB2File<T extends AbstractEntity> {

	private String path;
	private boolean writeHeader;
	private Class<T> clazz;
	
	@Autowired
	private Send2File send2File;
	
	@Autowired
	private GenericEntitiesRepository repository;
	
	DB2File() {
	}

	public DB2File( String path, Class<T> clazz ) {
		this.path = path;
		this.clazz = clazz;
	}
	
	public void send() throws IOException{
		send2File.setWriteHeader(writeHeader);
		send2File.createFile(path);
		
		
		
		
		for (T element : repository.findAll(clazz)) {
			GenericEntityWrapper<T> wrapper = new GenericEntityWrapper<>(element, clazz);
			
			send2File.addRow(wrapper);
		}
		
		send2File.closeFile();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void setWriteHeader(boolean writeHeader) {
		this.writeHeader = writeHeader;
	}

}
