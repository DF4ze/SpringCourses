package org.df4ze.courses.models;

import java.util.ArrayList;
import java.util.List;

public class EntitiesList {


	private List<AbstractEntity> entitiesList = new ArrayList<AbstractEntity>();

	public EntitiesList() {
	}
	
	
	public void add( AbstractEntity bean ){
		entitiesList.add(bean);
	}
	
	public void addAll( ArrayList<AbstractEntity> liste ){
		entitiesList.addAll(liste);
	}
	
	public void addAll( EntitiesList liste ){
		entitiesList.addAll(liste.get());
	}
	
	public AbstractEntity get( int i ){
		return entitiesList.get(i);
	}
	
	public void set( ArrayList<AbstractEntity> liste ){
		entitiesList = liste;
	}
	
	public List<AbstractEntity> get(){
		return entitiesList;
	}

}