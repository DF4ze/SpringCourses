package org.df4ze.courses.resources.warc;

import org.df4ze.courses.models.EntitiesList;

public interface WarcParser {
	
	
	public EntitiesList parse( String url, String body );
	
	Long parse_numCourse();
	
	EntitiesList parse_course();
	EntitiesList parse_rapport();
	EntitiesList parse_arrivee();
	EntitiesList parse_cote();
	EntitiesList parse_partant();

}
