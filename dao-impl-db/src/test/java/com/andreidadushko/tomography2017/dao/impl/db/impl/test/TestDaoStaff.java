package com.andreidadushko.tomography2017.dao.impl.db.impl.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.andreidadushko.tomography2017.dao.db.IStaffDao;
import com.andreidadushko.tomography2017.datamodel.Staff;

public class TestDaoStaff {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dao-context.xml");

		IStaffDao service = context.getBean(IStaffDao.class);
		Staff staff = new Staff();

		staff.setDepartment("РКД");
		staff.setPosition("Санитар");
		staff.setStartDate(new Timestamp(new Date().getTime()));
		staff.setPersonId(2);
		
		Staff f = service.insert(staff);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(f);
		System.out.println(service.get(f.getId()));
		
		staff.setDepartment("раз");
		staff.setPosition("два");
		staff.setEndDate(new Timestamp(new Date().getTime()));
		
		service.update(staff);
		System.out.println(service.get(f.getId()));
		//service.delete(f.getId());
		List<Staff> list = service.getAll();

		for (Staff staff2 : list) {
			System.out.println(staff2);
		}

		context.close();
	}
}
