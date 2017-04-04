package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

public interface IAbstractDao<T> {

	public T get(Integer id);

	public T insert(T object);

	public void update(T object);

	public void delete(Integer id);

	public List<T> getAll();

}
