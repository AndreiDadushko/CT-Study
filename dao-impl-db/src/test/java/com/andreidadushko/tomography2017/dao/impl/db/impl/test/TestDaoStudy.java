package com.andreidadushko.tomography2017.dao.impl.db.impl.test;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyDao;
import com.andreidadushko.tomography2017.datamodel.Study;

public class TestDaoStudy {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dao-context.xml");

		IStudyDao service = context.getBean(IStudyDao.class);
		Study study = new Study();

		study.setAppointmentDate(new Date());
		study.setPermitted(false);
		study.setPersonId(1);
		study.setStaffId(2);
		
		Study study1 = service.insert(study);
		
		System.out.println(study1);
		System.out.println(service.get(study1.getId()));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		study.setAppointmentDate(new Date());
		study.setPermitted(true);
		study.setPersonId(2);
		study.setStaffId(2);
		
		service.update(study);
		System.out.println(service.get(study.getId()));
		//service.delete(study.getId());
		List<Study> list = service.getAll();

		for (Study st : list) {
			System.out.println(st);
		}

		context.close();

	}

}
