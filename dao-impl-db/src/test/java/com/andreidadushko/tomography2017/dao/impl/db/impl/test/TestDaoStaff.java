package com.andreidadushko.tomography2017.dao.impl.db.impl.test;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.andreidadushko.tomography2017.dao.impl.db.IStaffDao;
import com.andreidadushko.tomography2017.datamodel.Staff;

public class TestDaoStaff {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dao-context.xml");

		IStaffDao service = context.getBean(IStaffDao.class);
		/*Staff staff = new Staff();

		staff.setDepartment("РКД");
		staff.setPosition("Санитар");
		staff.setStartDate(new Date());
		staff.setPersonId(2);
		
		Staff f = service.insert(staff);
		System.out.println(f);
		System.out.println(service.get(f.getId()));
		
		staff.setDepartment("раз");
		staff.setPosition("два");
		staff.setEndDate(new Date());
		
		service.update(staff);
		System.out.println(service.get(f.getId()));
		service.delete(f.getId());*/
		List<Staff> list = service.getAll();

		for (Staff staff2 : list) {
			System.out.println(staff2);
		}

		context.close();
	}
}
