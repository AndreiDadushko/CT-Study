package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.StudyProtocol;
import com.andreidadushko.tomography2017.webapp.models.StudyProtocolModel;

public class ProtocolModelToEntityConverter implements Converter<StudyProtocolModel, StudyProtocol> {

	@Override
	public StudyProtocol convert(StudyProtocolModel studyProtocolModel) {
		StudyProtocol studyProtocol = new StudyProtocol();
		studyProtocol.setId(studyProtocolModel.getId());
		studyProtocol.setProtocol(studyProtocolModel.getProtocol());
		studyProtocol.setCreationDate(studyProtocolModel.getCreationDate() == null ? null
				: new java.sql.Timestamp(studyProtocolModel.getCreationDate()));
		return studyProtocol;
	}

}
