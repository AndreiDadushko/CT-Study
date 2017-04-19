package com.andreidadushko.tomography2017.dao.impl.db.impl.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.andreidadushko.tomography2017.dao.db.IStudyProtocolDao;
import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

public class TestDaoStudyProtocol {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dao-context.xml");

		IStudyProtocolDao service = context.getBean(IStudyProtocolDao.class);
		StudyProtocol studyProtocol = new StudyProtocol();

		studyProtocol.setId(8);
		studyProtocol.setProtocol("protokol s java");		
		
		service.insert(studyProtocol);
		
		System.out.println(studyProtocol);
		System.out.println(service.get(studyProtocol.getId()));
		
		studyProtocol.setProtocol("protocol s java 2222");
		
		service.update(studyProtocol);
		System.out.println(service.get(studyProtocol.getId()));
		service.delete(studyProtocol.getId());
		List<StudyProtocol> list = service.getAll();

		for (StudyProtocol st : list) {
			System.out.println(st);
		}

		context.close();

	}

}
