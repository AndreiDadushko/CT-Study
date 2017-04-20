package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IPersonDao;
import com.andreidadushko.tomography2017.dao.db.filters.PersonFilter;
import com.andreidadushko.tomography2017.dao.xml.impl.wrapper.XmlModelWrapper;
import com.andreidadushko.tomography2017.datamodel.Person;

@Repository
public class PersonDaoXmlImpl extends AbstractDaoXmlImpl<Person> implements IPersonDao {

	@Override
	public File getFile() {
		File file = new File(rootFolder + "person.xml");
		return file;
	}

	@Override
	public boolean idEquals(Person object, Integer id) {
		return object.getId().equals(id);
	}

	@Override
	public void setId(Person object, int id) {
		object.setId(id);
	}

	@Override
	public void updateObject(List<Person> list, Person object) {
		for (Person person : list) {
			if (person.getId().equals(object.getId())) {
				person.setFirstName(object.getFirstName());
				person.setMiddleName(object.getMiddleName());
				person.setLastName(object.getLastName());
				person.setBirthDate(object.getBirthDate());
				person.setAdress(object.getAdress());
				person.setPhoneNumber(object.getPhoneNumber());
				person.setLogin(object.getLogin());
				person.setPassword(object.getPassword());
				break;
			}
		}
	}

	@Override
	public Person get(String login) {
		File file = getFile();
		XmlModelWrapper<Person> wrapper = (XmlModelWrapper<Person>) xstream.fromXML(file);
		List<Person> list = wrapper.getRows();
		for (Person person : list) {
			if (person.getLogin().equals(login)) {
				return person;
			}
		}
		return null;
	}

	@Override
	public List<Person> getWithPagination(int offset, int limit) {
		File file = getFile();
		XmlModelWrapper<Person> wrapper = (XmlModelWrapper<Person>) xstream.fromXML(file);
		List<Person> list = wrapper.getRows();
		List<Person> result = new ArrayList<Person>();
		for (int i = 0; i < list.size(); i++) {
			if (i >= offset) {
				if (result.size() == limit)
					break;
				result.add(list.get(i));
			}
		}
		return result;
	}

	@Override
	public List<Person> getWithPagination(int offset, int limit, PersonFilter personFilter) {
		File file = getFile();
		XmlModelWrapper<Person> wrapper = (XmlModelWrapper<Person>) xstream.fromXML(file);
		List<Person> list = wrapper.getRows();
		if (personFilter == null) {
			return list;
		}
		List<Person> filteredList = new ArrayList<Person>();
		for (Person person : list) {
			if (personFilter.getFirstName() != null) {
				if (person.getFirstName() == null || !personFilter.getFirstName().equals(person.getFirstName()))
					continue;
			}
			if (personFilter.getLastName() != null) {
				if (person.getLastName() == null || !personFilter.getLastName().equals(person.getLastName()))
					continue;
			}
			if (personFilter.getMiddleName() != null) {
				if (person.getMiddleName() == null || !personFilter.getMiddleName().equals(person.getMiddleName()))
					continue;
			}
			if (personFilter.getAdress() != null) {
				if (person.getAdress() == null || !personFilter.getAdress().equals(person.getAdress()))
					continue;
			}
			if (personFilter.getFrom() != null) {
				if (person.getBirthDate() == null || person.getBirthDate().before(personFilter.getFrom()))
					continue;
			}
			if (personFilter.getTo() != null) {
				if (person.getBirthDate() == null || person.getBirthDate().after(personFilter.getTo()))
					continue;
			}
			filteredList.add(person);
		}
		List<Person> result = new ArrayList<Person>();
		for (int i = 0; i < filteredList.size(); i++) {
			if (i >= offset) {
				if (result.size() == limit)
					break;
				result.add(filteredList.get(i));
			}
		}
		return result;
	}

}
