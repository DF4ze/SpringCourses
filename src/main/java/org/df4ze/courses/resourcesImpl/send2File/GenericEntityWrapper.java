package org.df4ze.courses.resourcesImpl.send2File;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.df4ze.courses.models.AbstractEntity;
import org.df4ze.courses.resources.send2File.EntityWrapper;

public class GenericEntityWrapper<T extends AbstractEntity> implements EntityWrapper {

	private T entity;
	private Class<T> clazz;

	GenericEntityWrapper() {
	}

	public GenericEntityWrapper(T entity, Class<T> clazz) {
		this.entity = entity;
		this.clazz = clazz;
	}

	@Override
	public Integer getNumberOfAttributes() {
		if (clazz != null)
			return clazz.getDeclaredFields().length;
		else
			return null;
	}

	@Override
	public Object getAttribute(int number) {
		if (entity != null) {
			try {
				Field f = entity.getClass().getDeclaredFields()[number];
				f.setAccessible(true);
				return f.get(entity);
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				e.printStackTrace();
			}
			return null;
		} else
			return null;
	}

	public List<Object> getAttributesList() {
		if (entity != null) {
			List<Object> list = new ArrayList<>();
			for (Field f : entity.getClass().getDeclaredFields()) {
				try {
					f.setAccessible(true);
					list.add(f.get(entity));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return list;
		} else
			return null;

	}

	@Override
	public List<Object> getHeader() {
		if (entity != null) {
			List<Object> list = new ArrayList<>();
			for (Field f : entity.getClass().getDeclaredFields()) {
				try {
					f.setAccessible(true);
					list.add(f.getName());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
			return list;
		} else
			return null;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
}
