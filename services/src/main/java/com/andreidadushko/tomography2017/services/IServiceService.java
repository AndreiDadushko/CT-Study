package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.datamodel.Service;

public interface IServiceService {

	Service get(Integer id);

	@Transactional
	Service insert(Service service);

	@Transactional
	void update(Service service);

	@Transactional
	void delete(Integer id);

	List<Service> getAll();

}
