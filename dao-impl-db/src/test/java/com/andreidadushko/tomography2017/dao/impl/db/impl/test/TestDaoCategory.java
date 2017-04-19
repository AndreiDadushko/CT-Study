package com.andreidadushko.tomography2017.dao.impl.db.impl.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.andreidadushko.tomography2017.dao.db.ICategoryDao;
import com.andreidadushko.tomography2017.datamodel.Category;

public class TestDaoCategory {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dao-context.xml");

		ICategoryDao service = context.getBean(ICategoryDao.class);
		Category category = new Category();

		category.setName("челюсть");
		category.setParentId(1);
		
		Category category1  = service.insert(category);
		
		System.out.println(category1);
		System.out.println(service.get(category1.getId()));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		category.setName("челюсть2");
		category.setParentId(2);
		
		service.update(category);
		System.out.println(service.get(category.getId()));
		//service.delete(category.getId());
		List<Category> list = service.getAll();

		for (Category ct : list) {
			System.out.println(ct);
		}

		context.close();

	}

}
