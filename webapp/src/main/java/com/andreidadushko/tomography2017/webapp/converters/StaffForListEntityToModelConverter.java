package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.dao.db.custom.models.StaffForList;
import com.andreidadushko.tomography2017.webapp.models.StaffForListModel;

public class StaffForListEntityToModelConverter implements Converter<StaffForList, StaffForListModel> {

	@Override
	public StaffForListModel convert(StaffForList staffForList) {
		StaffForListModel staffForListModel = new StaffForListModel();
		staffForListModel.setId(staffForList.getId());
		staffForListModel.setFirstName(staffForList.getFirstName());
		staffForListModel.setMiddleName(staffForList.getMiddleName());
		staffForListModel.setLastName(staffForList.getLastName());
		staffForListModel.setDepartment(staffForList.getDepartment());
		staffForListModel.setPosition(staffForList.getPosition());
		staffForListModel
				.setStartDate(staffForList.getStartDate() == null ? null : staffForList.getStartDate().getTime());
		staffForListModel.setEndDate(staffForList.getEndDate() == null ? null : staffForList.getEndDate().getTime());
		return staffForListModel;
	}

}
