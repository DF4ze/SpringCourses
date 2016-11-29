package org.df4ze.courses.models;

public abstract class AbstractEntity implements Persistable<Long>{
	
	protected static final long serialVersionUID = 1L;

	public abstract Long getId() ;
}