package com.andreidadushko.tomography2017.dao.impl.db.impl.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.andreidadushko.tomography2017.dao.impl.db.IOfferDao;
import com.andreidadushko.tomography2017.datamodel.Offer;

public class TestDaoService {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dao-context.xml");

		IOfferDao service = context.getBean(IOfferDao.class);
		Offer ser = new Offer();

		ser.setName("кт-языка");
		ser.setPrice(563.511111);
		ser.setCategorId(1);
		
		Offer service1  = service.insert(ser);
		
		System.out.println(service1);
		System.out.println(service.get(service1.getId()));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		ser.setName("кт-языка UPDATE");
		ser.setPrice(00.000001);
		ser.setCategorId(2);
		
		service.update(ser);
		System.out.println(service.get(ser.getId()));
		service.delete(ser.getId());
		List<Offer> list = service.getAll();

		for (Offer sv : list) {
			System.out.println(sv);
		}

		context.close();
	}

}
