package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.dao.db.filters.SortData;
import com.andreidadushko.tomography2017.dao.db.filters.StudyFilter;
import com.andreidadushko.tomography2017.webapp.models.StudyFilterModel;

public class StudyFilterModelToEntityConverter implements Converter<StudyFilterModel, StudyFilter> {

	@Override
	public StudyFilter convert(StudyFilterModel studyFilterModel) {
		StudyFilter studyFilter = new StudyFilter();
		studyFilter.setPermitted(studyFilterModel.getPermitted());
		studyFilter.setPatientFirstName(studyFilterModel.getPatientFirstName());
		studyFilter.setPatientMiddleName(studyFilterModel.getPatientMiddleName());
		studyFilter.setPatientLastName(studyFilterModel.getPatientLastName());
		studyFilter.setDoctorFirstName(studyFilterModel.getDoctorFirstName());
		studyFilter.setDoctorMiddleName(studyFilterModel.getDoctorMiddleName());
		studyFilter.setDoctorLastName(studyFilterModel.getDoctorLastName());
		studyFilter.setFrom(
				studyFilterModel.getFrom() == null ? null : new java.sql.Timestamp(studyFilterModel.getFrom()));
		studyFilter.setTo(studyFilterModel.getTo() == null ? null : new java.sql.Timestamp(studyFilterModel.getTo()));
		if (studyFilterModel.getSort() != null) {
			SortData sortData = new SortData();
			sortData.setColumn(studyFilterModel.getSort().getColumn());
			sortData.setOrder(studyFilterModel.getSort().getOrder());
			studyFilter.setSort(sortData);
		}
		return studyFilter;
	}

}
