package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.webapp.models.StudyModel;

public class StudyModelToEntityConverter implements Converter<StudyModel, Study> {

	@Override
	public Study convert(StudyModel studyModel) {
		Study study = new Study();
		study.setId(studyModel.getId());
		study.setAppointmentDate(studyModel.getAppointmentDate() == null ? null
				: new java.sql.Timestamp(studyModel.getAppointmentDate()));
		study.setPermitted(studyModel.getPermitted());
		study.setPersonId(studyModel.getPersonId());
		study.setStaffId(studyModel.getStaffId());
		return study;
	}

}
