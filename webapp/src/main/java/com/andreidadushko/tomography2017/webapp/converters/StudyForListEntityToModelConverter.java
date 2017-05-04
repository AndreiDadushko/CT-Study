package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.dao.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.webapp.models.StudyForListModel;

public class StudyForListEntityToModelConverter implements Converter<StudyForList, StudyForListModel> {

	@Override
	public StudyForListModel convert(StudyForList studyForList) {
		StudyForListModel studyForListModel = new StudyForListModel();
		studyForListModel.setId(studyForList.getId());
		studyForListModel.setAppointmentDate(studyForList.getAppointmentDate().getTime());
		studyForListModel.setPermitted(studyForList.getPermitted());
		studyForListModel.setPatientFirstName(studyForList.getPatientFirstName());
		studyForListModel.setPatientMiddleName(studyForList.getPatientMiddleName());
		studyForListModel.setPatientLastName(studyForList.getPatientLastName());
		studyForListModel.setDoctorFirstName(studyForList.getDoctorFirstName());
		studyForListModel.setDoctorMiddleName(studyForList.getDoctorMiddleName());
		studyForListModel.setDoctorLastName(studyForList.getDoctorLastName());
		return studyForListModel;
	}

}
