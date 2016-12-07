package org.df4ze.courses.resourcesImpl.warc;

import org.df4ze.courses.models.EntitiesList;
import org.df4ze.courses.resources.warc.WarcParser;
import org.df4ze.courses.resources.warc.WarcVisitor;
import org.df4ze.courses.services.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParseAndStore implements WarcVisitor {

	@Autowired
	RepositoryService repository;
	
	@Autowired
	WarcParser parser;
	
	
	
	ParseAndStore() {
	}

	@Override
	public void indexify(String url, String archiveBody, String place) {
		System.out.println(place);
		
		EntitiesList bl = parser.parse(url, archiveBody);

		if( bl != null )
			repository.addAll(bl);
//		try {
//			sema.acquire();
//			ThreadStore ts = new ThreadStore(bl, repository);
//			Thread t = new Thread(ts);
//			t.start();
//			sema.release();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		
	}

}
