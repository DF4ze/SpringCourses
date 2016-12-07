package org.df4ze.courses.resources.send2File;

import java.util.List;

public interface EntityWrapper {
	
	public Integer getNumberOfAttributes();
	public Object getAttribute(int number);
	public List<Object> getAttributesList();
	public List<Object>  getHeader();
}
