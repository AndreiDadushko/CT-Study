package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.datamodel.Service;

public interface IServiceDao {
	Service get(Integer id);

	Service insert(Service service);

	void update(Service service);	

	void delete(Integer id);
	
	List<Service> getAll();
}
