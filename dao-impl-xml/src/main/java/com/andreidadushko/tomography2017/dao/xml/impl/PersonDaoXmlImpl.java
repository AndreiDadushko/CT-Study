package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IPersonDao;
import com.andreidadushko.tomography2017.dao.db.filters.PersonFilter;
import com.andreidadushko.tomography2017.dao.xml.impl.wrapper.XmlModelWrapper;
import com.andreidadushko.tomography2017.datamodel.Person;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Repository
public class PersonDaoXmlImpl implements IPersonDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public Person get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Person> wrapper = (XmlModelWrapper<Person>) xstream.fromXML(file);
		if (wrapper != null) {
			List<Person> list = wrapper.getRows();
			for (Person person : list) {
				if (person.getId().equals(id)) {
					return person;
				}
			}
		}
		return null;
	}

	@Override
	public Person insert(Person object) {
		File file = getFile();
		XmlModelWrapper<Person> wrapper = (XmlModelWrapper<Person>) xstream.fromXML(file);
		if (wrapper != null) {
			List<Person> list = wrapper.getRows();
			Integer lastId = wrapper.getLastId();
			int newId = lastId + 1;
			object.setId(newId);
			list.add(object);
			wrapper.setLastId(newId);
		} else {
			int newId = 1;
			object.setId(newId);
			wrapper = new XmlModelWrapper<Person>();
			wrapper.setRows(new ArrayList<Person>());
			wrapper.getRows().add(object);
			wrapper.setLastId(newId);
		}
		writeNewData(file, wrapper);
		return object;
	}

	@Override
	public void update(Person object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Person> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person get(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> getWithPagination(int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> getWithPagination(int offset, int limit, PersonFilter personFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	private void writeNewData(File file, XmlModelWrapper<Person> obj) {
		try {
			xstream.toXML(obj, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private File getFile() {
		File file = new File(rootFolder + "person.xml");
		return file;
	}
}
