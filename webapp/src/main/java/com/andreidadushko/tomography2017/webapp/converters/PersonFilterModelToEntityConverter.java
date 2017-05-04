package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.dao.db.filters.PersonFilter;
import com.andreidadushko.tomography2017.dao.db.filters.SortData;
import com.andreidadushko.tomography2017.webapp.models.PersonFilterModel;

public class PersonFilterModelToEntityConverter implements Converter<PersonFilterModel, PersonFilter> {

	@Override
	public PersonFilter convert(PersonFilterModel personFilterModel) {
		PersonFilter personFilter = new PersonFilter();
		personFilter.setFirstName(personFilterModel.getFirstName());
		personFilter.setMiddleName(personFilterModel.getMiddleName());
		personFilter.setLastName(personFilterModel.getLastName());
		personFilter.setAdress(personFilterModel.getAdress());
		personFilter.setFrom(
				personFilterModel.getFrom() == null ? null : new java.sql.Timestamp(personFilterModel.getFrom()));
		personFilter
				.setTo(personFilterModel.getTo() == null ? null : new java.sql.Timestamp(personFilterModel.getTo()));
		if (personFilterModel.getSort() != null) {
			SortData sortData = new SortData();
			sortData.setColumn(personFilterModel.getSort().getColumn());
			sortData.setOrder(personFilterModel.getSort().getOrder());
			personFilter.setSort(sortData);
		}
		return personFilter;
	}

}
