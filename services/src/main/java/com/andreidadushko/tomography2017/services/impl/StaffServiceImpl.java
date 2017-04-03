package com.andreidadushko.tomography2017.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.andreidadushko.tomography2017.dao.impl.db.IStaffDao;
import com.andreidadushko.tomography2017.datamodel.Staff;
import com.andreidadushko.tomography2017.services.IStaffService;

@Service
public class StaffServiceImpl implements IStaffService {

	@Inject
	private IStaffDao staffDao;

	@Override
	public Staff get(Integer id) {
		if (id == null)
			return null;
		return staffDao.get(id);

	}

	@Override
	public Staff insert(Staff staff) {
		if (isValid(staff))
			return staffDao.insert(staff);
		else
			throw new IllegalArgumentException();
	}

	@Override
	public void update(Staff staff) {
		if (isValid(staff) && staff.getId() != null)
			staffDao.update(staff);
		else
			throw new IllegalArgumentException();
	}

	@Override
	public void delete(Integer id) {

		staffDao.delete(id);

	}

	@Override //УБРАТЬ!
	public List<Staff> getAll() {

		return staffDao.getAll();
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
