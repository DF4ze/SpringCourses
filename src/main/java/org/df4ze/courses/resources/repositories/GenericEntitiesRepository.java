package org.df4ze.courses.resources.repositories;

import java.io.Serializable;

import javax.persistence.EntityNotFoundException;

public interface GenericEntitiesRepository {

	public <T> T get(Serializable id, Class<T> cl) throws EntityNotFoundException;

	public <T> Iterable<T> findAll( Class<T> cl);
	
	<T> T save(T entity, Class<T> cl);

	<T> Iterable<T> save(Iterable<T> entities, Class<T> cl);

	<T> void delete(T entity, Class<T> cl);

	<T> void delete(Iterable<T> entities, Class<T> cl);

}