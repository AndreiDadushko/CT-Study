package com.andreidadushko.tomography2017.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andreidadushko.tomography2017.dao.db.custom.models.StaffForList;
import com.andreidadushko.tomography2017.dao.db.filters.StaffFilter;
import com.andreidadushko.tomography2017.datamodel.Staff;

public interface IStaffService {

	Staff get(Integer id);

	@Transactional
	Staff insert(Staff staff);

	@Transactional
	void update(Staff staff);

	@Transactional
	void delete(Integer id);
		
	List<String> getPositionsByLogin(String login);
	
	Integer getCount();
	
	List<StaffForList> getWithPagination(int offset, int limit);

	List<StaffForList> getWithPagination(int offset, int limit, StaffFilter staffFilter);
}
