package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

public interface IAbstractDao<T> {

	T get(Integer id);

	T insert(T object);

	void update(T object);

	void delete(Integer id);

	List<T> getAll();

}
