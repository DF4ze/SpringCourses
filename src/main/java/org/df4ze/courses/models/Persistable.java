package org.df4ze.courses.models;

import java.io.Serializable;

public interface Persistable <T extends Serializable> {
	public T getId();
}
