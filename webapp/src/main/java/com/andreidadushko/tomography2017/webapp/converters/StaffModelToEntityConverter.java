package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Staff;
import com.andreidadushko.tomography2017.webapp.models.StaffModel;

public class StaffModelToEntityConverter implements Converter<StaffModel, Staff> {

	@Override
	public Staff convert(StaffModel staffModel) {
		Staff staff = new Staff();
		staff.setId(staffModel.getId());
		staff.setDepartment(staffModel.getDepartment());
		staff.setPosition(staffModel.getPosition());
		staff.setStartDate(
				staffModel.getStartDate() == null ? null : new java.sql.Timestamp(staffModel.getStartDate()));
		staff.setEndDate(staffModel.getEndDate() == null ? null : new java.sql.Timestamp(staffModel.getEndDate()));
		staff.setPersonId(staffModel.getPersonId());
		return staff;
	}

}
