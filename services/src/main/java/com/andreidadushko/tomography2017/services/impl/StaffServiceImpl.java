package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStaffDao;
import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StaffForList;
import com.andreidadushko.tomography2017.dao.impl.db.filters.StaffFilter;
import com.andreidadushko.tomography2017.datamodel.Staff;
import com.andreidadushko.tomography2017.services.IStaffService;

@Service
public class StaffServiceImpl implements IStaffService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaffServiceImpl.class);

	@Inject
	private IStaffDao staffDao;

	@Override
	public Staff get(Integer id) {
		
		LOGGER.info("Get staff with id = " + id);
		if (id == null)
			return null;
		return staffDao.get(id);
	}

	@Override
	public Integer getCount() {

		return staffDao.getCount();
	}

	@Override
	public Staff insert(Staff staff) {
		isValid(staff);
		staffDao.insert(staff);
		LOGGER.info("Insert staff with id = " + staff.getId());
		return staff;
	}

	@Override
	public void update(Staff staff) {
		if (isValid(staff) && staff.getId() != null) {
			staffDao.update(staff);
			LOGGER.info("Update staff with id = " + staff.getId());
		} else
			throw new IllegalArgumentException("Could not update staff without id");
	}

	@Override
	public void delete(Integer id) {

		staffDao.delete(id);
		LOGGER.info("Delete staff with id = " + id);
	}

	@Override
	public List<StaffForList> getWithPagination(int offset, int limit) {
		List<StaffForList> list = staffDao.getWithPagination(offset, limit);
		LOGGER.info("Get list of staff with offset = {}, limit = {}", offset, limit);
		return list;
	}

	@Override
	public List<StaffForList> getWithPagination(int offset, int limit, StaffFilter staffFilter) {
		List<StaffForList> list = staffDao.getWithPagination(offset, limit, staffFilter);
		LOGGER.info("Get list of staff with offset = {}, limit = {} and filter = ", offset, limit, staffFilter);
		return list;
	}

	@Override
	public List<String> getPositionsByLogin(String login) {
		List<String> list = staffDao.getPositionsByLogin(login);
		LOGGER.info("Get list of positions by login");
		return list;
	}

	private boolean isValid(Staff staff) {
		if (staff == null)
			throw new IllegalArgumentException("Could not insert/update null");
		if (staff.getDepartment() == null || staff.getPosition() == null || staff.getPersonId() == null)
			throw new IllegalArgumentException("Staff must have department, position and person id");
		return true;
	}
}
