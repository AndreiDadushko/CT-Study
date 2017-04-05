package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StaffForList;
import com.andreidadushko.tomography2017.datamodel.Staff;

public interface IStaffService {

	Staff get(Integer id);

	@Transactional
	Staff insert(Staff staff);

	@Transactional
	void update(Staff staff);

	@Transactional
	void delete(Integer id);

	List<Staff> getAll();
	
	List<StaffForList> getAllStaffForList();
	
}
