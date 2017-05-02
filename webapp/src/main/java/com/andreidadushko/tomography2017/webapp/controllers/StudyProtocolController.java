package com.andreidadushko.tomography2017.webapp.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.datamodel.StudyProtocol;
import com.andreidadushko.tomography2017.services.IStudyProtocolService;
import com.andreidadushko.tomography2017.services.IStudyService;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.StudyProtocolModel;
import com.andreidadushko.tomography2017.webapp.storage.UserAuthStorage;

@RestController
@RequestMapping("/protocol")
public class StudyProtocolController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudyProtocolController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private IStudyProtocolService studyProtocolService;

	@Inject
	private IStudyService studyService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Study study = studyService.get(id);
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")
				|| userAuthStorage.getId().equals(study.getPersonId())) {
			StudyProtocol studyProtocol = studyProtocolService.get(id);
			StudyProtocolModel convertedProtocol = null;
			if (studyProtocol != null) {
				convertedProtocol = protocolEntity2ProtocolModel(studyProtocol);
			}
			return new ResponseEntity<StudyProtocolModel>(convertedProtocol, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")) {
			Integer result = studyProtocolService.getCount();
			return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody StudyProtocolModel studyProtocolModel) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			StudyProtocol studyProtocol = protocolModel2ProtocolEntity(studyProtocolModel);
			try {
				studyProtocolService.insert(studyProtocol);
				return new ResponseEntity<IntegerModel>(new IntegerModel(studyProtocol.getId()), HttpStatus.CREATED);
			} catch (IllegalArgumentException e) {
				LOGGER.warn(e.getMessage());
				return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody StudyProtocolModel studyProtocolModel) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			StudyProtocol studyProtocol = protocolModel2ProtocolEntity(studyProtocolModel);
			try {
				studyProtocolService.update(studyProtocol);
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			} catch (IllegalArgumentException e) {
				LOGGER.warn(e.getMessage());
				return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			studyProtocolService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<?> massDelete(@RequestBody Integer[] idArray) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			studyProtocolService.massDelete(idArray);
			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
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
