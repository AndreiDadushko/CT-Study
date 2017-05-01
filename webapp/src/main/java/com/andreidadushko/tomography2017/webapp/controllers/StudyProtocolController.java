package com.andreidadushko.tomography2017.webapp.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andreidadushko.tomography2017.datamodel.StudyProtocol;
import com.andreidadushko.tomography2017.services.IStudyProtocolService;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.StudyProtocolModel;

@RestController
@RequestMapping("/protocol")
public class StudyProtocolController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudyProtocolController.class);
	
	@Inject
	private IStudyProtocolService studyProtocolService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		StudyProtocol studyProtocol = studyProtocolService.get(id);
		StudyProtocolModel convertedProtocol = null;
		if (studyProtocol != null) {
			convertedProtocol = protocolEntity2ProtocolModel(studyProtocol);
		}
		return new ResponseEntity<StudyProtocolModel>(convertedProtocol, HttpStatus.OK);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		Integer result = studyProtocolService.getCount();
		return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody StudyProtocolModel studyProtocolModel) {
		StudyProtocol studyProtocol = protocolModel2ProtocolEntity(studyProtocolModel);
		try {
			studyProtocolService.insert(studyProtocol);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<IntegerModel>(new IntegerModel(studyProtocol.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody StudyProtocolModel studyProtocolModel) {
		StudyProtocol studyProtocol = protocolModel2ProtocolEntity(studyProtocolModel);
		try {
			studyProtocolService.update(studyProtocol);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		studyProtocolService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<?> massDelete(@RequestBody Integer[] idArray) {
		studyProtocolService.massDelete(idArray);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private StudyProtocolModel protocolEntity2ProtocolModel(StudyProtocol studyProtocol) {
		StudyProtocolModel studyProtocolModel = new StudyProtocolModel();
		studyProtocolModel.setId(studyProtocol.getId());
		studyProtocolModel.setProtocol(studyProtocol.getProtocol());
		studyProtocolModel.setCreationDate(
				studyProtocol.getCreationDate() == null ? null : studyProtocol.getCreationDate().getTime());
		return studyProtocolModel;
	}

	private StudyProtocol protocolModel2ProtocolEntity(StudyProtocolModel studyProtocolModel) {
		StudyProtocol studyProtocol = new StudyProtocol();
		studyProtocol.setId(studyProtocolModel.getId());
		studyProtocol.setProtocol(studyProtocolModel.getProtocol());
		studyProtocol.setCreationDate(studyProtocolModel.getCreationDate() == null ? null
				: new java.sql.Timestamp(studyProtocolModel.getCreationDate()));
		return studyProtocol;
	}

}
