package com.andreidadushko.tomography2017.dao.impl.db.impl.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyServiceCartDao;
import com.andreidadushko.tomography2017.datamodel.StudyServiceCart;

public class TestDaoStudyServiceCart {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dao-context.xml");

		IStudyServiceCartDao service = context.getBean(IStudyServiceCartDao.class);
		StudyServiceCart studyServiceCart = new StudyServiceCart();

		studyServiceCart.setStudyId(1);
		studyServiceCart.setServiceId(1);

		StudyServiceCart studyServiceCart1 = service.insert(studyServiceCart);

		System.out.println(studyServiceCart1);
		System.out.println(service.get(studyServiceCart1.getId()));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		studyServiceCart.setPaid(true);
		studyServiceCart.setStudyId(2);
		studyServiceCart.setServiceId(2);

		service.update(studyServiceCart);
		System.out.println(service.get(studyServiceCart.getId()));
		//service.delete(studyServiceCart.getId());
		List<StudyServiceCart> list = service.getAll();

		for (StudyServiceCart cart : list) {
			System.out.println(cart);
		}

		context.close();
	}

}
