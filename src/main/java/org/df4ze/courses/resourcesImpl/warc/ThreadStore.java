package org.df4ze.courses.resourcesImpl.warc;

import org.df4ze.courses.models.EntitiesList;
import org.df4ze.courses.services.RepositoryService;


public class ThreadStore implements Runnable{
	
	
	RepositoryService repository;
	EntitiesList bl;
	
	 ThreadStore() {
	}
	

	
	public ThreadStore(EntitiesList bl, RepositoryService repository) {
		this.bl = bl;
		this.repository = repository;
		
	}

	@Override
	public void run() {
		if( bl != null )
			repository.addAll(bl);
	}



	public void setRepository(RepositoryService repository) {
		this.repository = repository;
	}



	public void setBl(EntitiesList bl) {
		this.bl = bl;
	}

	

}
