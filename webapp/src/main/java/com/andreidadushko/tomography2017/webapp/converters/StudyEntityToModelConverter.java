package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.webapp.models.StudyModel;

public class StudyEntityToModelConverter implements Converter<Study, StudyModel> {

	@Override
	public StudyModel convert(Study study) {
		StudyModel studyModel = new StudyModel();
		studyModel.setId(study.getId());
		studyModel.setAppointmentDate(study.getAppointmentDate() == null ? null : study.getAppointmentDate().getTime());
		studyModel.setPermitted(study.getPermitted());
		studyModel.setPersonId(study.getPersonId());
		studyModel.setStaffId(study.getStaffId());
		return studyModel;
	}

}
