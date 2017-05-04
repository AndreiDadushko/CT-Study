package com.andreidadushko.tomography2017.webapp.converters;

import org.springframework.core.convert.converter.Converter;

import com.andreidadushko.tomography2017.datamodel.StudyProtocol;
import com.andreidadushko.tomography2017.webapp.models.StudyProtocolModel;

public class ProtocolEntityToModelConverter implements Converter<StudyProtocol, StudyProtocolModel> {

	@Override
	public StudyProtocolModel convert(StudyProtocol studyProtocol) {
		StudyProtocolModel studyProtocolModel = new StudyProtocolModel();
		studyProtocolModel.setId(studyProtocol.getId());
		studyProtocolModel.setProtocol(studyProtocol.getProtocol());
		studyProtocolModel.setCreationDate(
				studyProtocol.getCreationDate() == null ? null : studyProtocol.getCreationDate().getTime());
		return studyProtocolModel;
	}

}
