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

		return staffDao.get(id);

	}

	@Override
	public Staff insert(Staff staff) {

		return staffDao.insert(staff);
	}

	@Override
	public void update(Staff staff) {

		staffDao.update(staff);

	}

	@Override
	public void delete(Integer id) {

		staffDao.delete(id);

	}

	@Override
	public List<Staff> getAll() {

		return staffDao.getAll();
	}

}
