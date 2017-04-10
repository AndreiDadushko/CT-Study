package com.andreidadushko.tomography2017.dao.impl.db;

import java.util.List;

import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StaffForList;
import com.andreidadushko.tomography2017.datamodel.Staff;

public interface IStaffDao extends IAbstractDao<Staff>{
	
	List<StaffForList> getAllStaffForList();
	
	List<String> getPositionsByLogin(String login);
}
