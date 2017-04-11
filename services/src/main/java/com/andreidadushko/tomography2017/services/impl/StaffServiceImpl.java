package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStaffDao;
import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StaffForList;
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
	public Staff insert(Staff staff) {
		if (isValid(staff)) {
			staffDao.insert(staff);
			LOGGER.info("Insert staff with id = " + staff.getId());
			return staff;
		} else
			throw new IllegalArgumentException();
	}

	@Override
	public void update(Staff staff) {
		if (isValid(staff) && staff.getId() != null) {
			staffDao.update(staff);
			LOGGER.info("Update staff with id = " + staff.getId());
		} else
			throw new IllegalArgumentException();
	}

	@Override
	public void delete(Integer id) {

		staffDao.delete(id);
		LOGGER.info("Delete staff with id = " + id);
	}

	@Override
	public List<StaffForList> getAllStaffForList() {
		List<StaffForList> list = staffDao.getAllStaffForList();
		LOGGER.info("Get list of all staff for list");
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
			return false;
		if (staff.getDepartment() == null || staff.getPosition() == null || staff.getPersonId() == null)
			return false;
		if (staff.getStartDate() != null)
			staff.getStartDate().setNanos(0);
		if (staff.getEndDate() != null)
			staff.getEndDate().setNanos(0);
		return true;
	}
}
