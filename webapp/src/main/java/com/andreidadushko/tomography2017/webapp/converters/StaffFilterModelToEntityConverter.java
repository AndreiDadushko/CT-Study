package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.dao.db.filters.SortData;
import com.andreidadushko.tomography2017.dao.db.filters.StaffFilter;
import com.andreidadushko.tomography2017.webapp.models.StaffFilterModel;

public class StaffFilterModelToEntityConverter implements Converter<StaffFilterModel, StaffFilter> {

	@Override
	public StaffFilter convert(StaffFilterModel staffFilterModel) {
		StaffFilter staffFilter = new StaffFilter();
		staffFilter.setFirstName(staffFilterModel.getFirstName());
		staffFilter.setMiddleName(staffFilterModel.getMiddleName());
		staffFilter.setLastName(staffFilterModel.getLastName());
		staffFilter.setDepartment(staffFilterModel.getDepartment());
		staffFilter.setStartFrom(staffFilterModel.getStartFrom() == null ? null
				: new java.sql.Timestamp(staffFilterModel.getStartFrom()));
		staffFilter.setStartTo(
				staffFilterModel.getStartTo() == null ? null : new java.sql.Timestamp(staffFilterModel.getStartTo()));
		staffFilter.setEndFrom(
				staffFilterModel.getEndFrom() == null ? null : new java.sql.Timestamp(staffFilterModel.getEndFrom()));
		staffFilter.setEndTo(
				staffFilterModel.getEndTo() == null ? null : new java.sql.Timestamp(staffFilterModel.getEndTo()));
		if (staffFilterModel.getSort() != null) {
			SortData sortData = new SortData();
			sortData.setColumn(staffFilterModel.getSort().getColumn());
			sortData.setOrder(staffFilterModel.getSort().getOrder());
			staffFilter.setSort(sortData);
		}
		return staffFilter;
	}

}
