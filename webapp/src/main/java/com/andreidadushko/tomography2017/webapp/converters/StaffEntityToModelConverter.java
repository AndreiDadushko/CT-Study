package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Staff;
import com.andreidadushko.tomography2017.webapp.models.StaffModel;

public class StaffEntityToModelConverter implements Converter<Staff, StaffModel> {

	@Override
	public StaffModel convert(Staff staff) {
		StaffModel staffModel = new StaffModel();
		staffModel.setId(staff.getId());
		staffModel.setDepartment(staff.getDepartment());
		staffModel.setPosition(staff.getPosition());
		staffModel.setStartDate(staff.getStartDate() == null ? null : staff.getStartDate().getTime());
		staffModel.setEndDate(staff.getEndDate() == null ? null : staff.getEndDate().getTime());
		staffModel.setPersonId(staff.getPersonId());
		return staffModel;
	}

}
