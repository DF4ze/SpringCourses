package org.df4ze.courses.resourcesImpl.repositories;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.df4ze.courses.resources.repositories.GenericEntitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class GenericEntitiesRepositoryImpl implements GenericEntitiesRepository {

	@Autowired
	private transient EntityManager em;

	@Override
	public <T> T get(Serializable id, Class<T> cl) throws EntityNotFoundException {
		T t = em.find(cl, id);
		if (t == null) {
			throw new EntityNotFoundException();
		}
		return t;
	}

	
	public <T> Iterable<T> findAll( Class<T> cl) {
		CrudRepository<T, ?> repo = new SimpleJpaRepository<T, Serializable>(cl, em);
		return repo.findAll();
	}

	@Override
	public <T> T save(T entity, Class<T> cl) {
		CrudRepository<T, ?> repo = new SimpleJpaRepository<T, Serializable>(cl, em);
		return repo.save(entity);
	}

	@Override
	public <T> Iterable<T> save(Iterable<T> entities, Class<T> cl) {
		CrudRepository<T, ?> repo = new SimpleJpaRepository<T, Serializable>(cl, em);
		return repo.save(entities);
	}

	@Override
	public <T> void delete(T entity, Class<T> cl) {
		CrudRepository<T, ?> repo = new SimpleJpaRepository<T, Serializable>(cl, em);
		repo.delete(entity);
	}

	@Override
	public <T> void delete(Iterable<T> entities, Class<T> cl) {
		CrudRepository<T, ?> repo = new SimpleJpaRepository<T, Serializable>(cl, em);
		repo.delete(entities);
	}



}