package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.datamodel.Staff;

public interface IStaffDao extends IAbstractDao<Staff>{
	
	Staff get(Integer id);

	Staff insert(Staff staff);

	void update(Staff staff);

	void delete(Integer id);

	List<Staff> getAll();
	
}
