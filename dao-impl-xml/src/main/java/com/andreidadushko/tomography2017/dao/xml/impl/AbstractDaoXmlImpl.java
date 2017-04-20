package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;

import com.andreidadushko.tomography2017.dao.db.IAbstractDao;
import com.andreidadushko.tomography2017.dao.xml.impl.wrapper.XmlModelWrapper;
import com.thoughtworks.xstream.XStream;

public abstract class AbstractDaoXmlImpl<T> implements IAbstractDao<T> {

	@Inject
	protected final XStream xstream=null;

	@Value("${root.folder}")
	protected String rootFolder;

	@Override
	public T get(Integer id) {
		File file = getFile();
		XmlModelWrapper<T> wrapper = (XmlModelWrapper<T>) xstream.fromXML(file);
		List<T> list = wrapper.getRows();
		for (T object : list) {
			if (idEquals(object, id)) {
				return object;
			}
		}
		return null;
	}

	@Override
	public T insert(T object) {
		File file = getFile();
		XmlModelWrapper<T> wrapper = (XmlModelWrapper<T>) xstream.fromXML(file);
		List<T> list = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;
		setId(object, newId);
		list.add(object);
		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return object;
	}

	@Override
	public void update(T object) {
		File file = getFile();
		XmlModelWrapper<T> wrapper = (XmlModelWrapper<T>) xstream.fromXML(file);
		List<T> list = wrapper.getRows();
		updateObject(list, object);
		writeNewData(file, wrapper);
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();
		XmlModelWrapper<T> wrapper = (XmlModelWrapper<T>) xstream.fromXML(file);
		List<T> list = wrapper.getRows();
		for (int i = 0; i < list.size(); i++) {
			T object = list.get(i);
			if (idEquals(object, id)) {
				list.remove(i);
				break;
			}
		}
		writeNewData(file, wrapper);

	}

	@Override
	public Integer getCount() {
		File file = getFile();
		XmlModelWrapper<T> wrapper = (XmlModelWrapper<T>) xstream.fromXML(file);
		List<T> list = wrapper.getRows();
		return list.size();
	}

	@Override
	public List<T> getAll() {
		File file = getFile();
		XmlModelWrapper<T> wrapper = (XmlModelWrapper<T>) xstream.fromXML(file);
		List<T> list = wrapper.getRows();
		return list;
	}

	protected void writeNewData(File file, XmlModelWrapper<T> obj) {
		try {
			xstream.toXML(obj, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public abstract File getFile();

	public abstract boolean idEquals(T object, Integer id);

	public abstract void setId(T object, int id);

	public abstract void updateObject(List<T> list, T object);
}
