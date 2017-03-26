package com.andreidadushko.tomography2017.dao.impl.db.impl.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyOfferCartDao;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;

public class TestDaoStudyServiceCart {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dao-context.xml");

		IStudyOfferCartDao service = context.getBean(IStudyOfferCartDao.class);
		StudyOfferCart studyServiceCart = new StudyOfferCart();

		studyServiceCart.setStudyId(1);
		studyServiceCart.setOfferId(1);;

		StudyOfferCart studyServiceCart1 = service.insert(studyServiceCart);

		System.out.println(studyServiceCart1);
		System.out.println(service.get(studyServiceCart1.getId()));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		studyServiceCart.setPaid(true);
		studyServiceCart.setPayDate(new Timestamp(new Date().getTime()));
		studyServiceCart.setStudyId(2);
		studyServiceCart.setOfferId(2);

		service.update(studyServiceCart);
		System.out.println(service.get(studyServiceCart.getId()));
		//service.delete(studyServiceCart.getId());
		List<StudyOfferCart> list = service.getAll();

		for (StudyOfferCart cart : list) {
			System.out.println(cart);
		}

		context.close();
	}

}
