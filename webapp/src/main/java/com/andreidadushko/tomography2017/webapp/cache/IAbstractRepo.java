package com.andreidadushko.tomography2017.webapp.cache;

import java.util.Map;

public interface IAbstractRepo<T> {

	void save(String login, T object);

	T find(String login);

	Map<Object,Object> findAll();

	void delete(String id);
	
	void cleanCache();
	
	void saveCache(String path);
	
	void loadCache(String path);

}
