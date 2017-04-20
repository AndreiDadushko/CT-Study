package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IPersonDao;
import com.andreidadushko.tomography2017.dao.db.IStaffDao;
import com.andreidadushko.tomography2017.dao.db.custom.models.StaffForList;
import com.andreidadushko.tomography2017.dao.db.filters.StaffFilter;
import com.andreidadushko.tomography2017.dao.xml.impl.wrapper.XmlModelWrapper;
import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.datamodel.Staff;

@Repository
public class StaffDaoXmlImpl extends AbstractDaoXmlImpl<Staff> implements IStaffDao {
	
	@Inject
	private IPersonDao personDao;
	
	@Override
	public File getFile() {
		File file = new File(rootFolder + "staff.xml");
		return file;
	}

	@Override
	public boolean idEquals(Staff object, Integer id) {
		return object.getId().equals(id);
	}

	@Override
	public void setId(Staff object, int id) {
		object.setId(id);		
	}
	

	@Override
	public void updateObject(List<Staff> list, Staff object) {
		for (Staff staff : list) {
			if (staff.getId().equals(object.getId())) {
				staff.setDepartment(object.getDepartment());
				staff.setPosition(object.getPosition());
				staff.setStartDate(object.getStartDate());
				staff.setEndDate(object.getEndDate());
				staff.setPersonId(object.getPersonId());
				break;
			}
		}		
	}
	
	@Override
	public List<String> getPositionsByLogin(String login) {
		File file = getFile();
		Person person =personDao.get(login);
		XmlModelWrapper<Staff> wrapper = (XmlModelWrapper<Staff>) xstream.fromXML(file);
		List<Staff> list = wrapper.getRows();
		List<String> result = new ArrayList<String>();
		for (Staff staff : list) {
			if (staff.getPersonId().equals(person.getId())) {
				result.add(staff.getPosition());
			}
		}
		return result;
	}

	@Override
	public List<StaffForList> getWithPagination(int offset, int limit) {
		File file = getFile();
		XmlModelWrapper<Staff> wrapper = (XmlModelWrapper<Staff>) xstream.fromXML(file);
		List<Staff> list = wrapper.getRows();
		List<StaffForList> result = new ArrayList<StaffForList>();
		for (int i = 0; i < list.size(); i++) {
			if (i >= offset) {
				if (result.size() == limit)
					break;
				StaffForList staffForList = new StaffForList();
				Person person =personDao.get(list.get(i).getPersonId());
				staffForList.setId(list.get(i).getId());
				staffForList.setFirstName(person.getFirstName());
				staffForList.setLastName(person.getLastName());
				staffForList.setMiddleName(person.getMiddleName());
				staffForList.setDepartment(list.get(i).getDepartment());
				staffForList.setPosition(list.get(i).getPosition());
				staffForList.setStartDate(list.get(i).getStartDate());
				staffForList.setEndDate(list.get(i).getEndDate());
				result.add(staffForList);
			}
		}
		return result;
	}

	@Override
	public List<StaffForList> getWithPagination(int offset, int limit, StaffFilter staffFilter) {
		throw new UnsupportedOperationException();
	}

}
