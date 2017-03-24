package com.andreidadushko.tomography2017.dao.impl.db.impl.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.andreidadushko.tomography2017.dao.impl.db.IServiceDao;
import com.andreidadushko.tomography2017.datamodel.Service;

public class TestDaoService {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dao-context.xml");

		IServiceDao service = context.getBean(IServiceDao.class);
		Service ser = new Service();

		ser.setName("кт-языка");
		ser.setPrice(563.511111);
		ser.setCategorId(1);
		
		Service service1  = service.insert(ser);
		
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
		List<Service> list = service.getAll();

		for (Service sv : list) {
			System.out.println(sv);
		}

		context.close();
	}

}
