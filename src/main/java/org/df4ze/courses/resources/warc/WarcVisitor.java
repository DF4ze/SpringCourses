package org.df4ze.courses.resources.warc;

public interface WarcVisitor {
	void indexify( String url, String archiveBody, String place );
}
