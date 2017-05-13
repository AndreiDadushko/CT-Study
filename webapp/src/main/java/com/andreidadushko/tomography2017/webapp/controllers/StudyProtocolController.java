package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.Arrays;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
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
import com.andreidadushko.tomography2017.webapp.storage.CurrentUserData;

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

	@Inject
	private ConversionService conversionService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Study study = studyService.get(id);
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")
				|| userAuthStorage.getId().equals(study.getPersonId())) {
			StudyProtocol studyProtocol = studyProtocolService.get(id);
			StudyProtocolModel convertedProtocol = null;
			if (studyProtocol != null) {
				convertedProtocol = conversionService.convert(studyProtocol, StudyProtocolModel.class);
			}
			LOGGER.info("{} request study protocol with id = {}", userAuthStorage, id);
			return new ResponseEntity<StudyProtocolModel>(convertedProtocol, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")) {
			Integer result = studyProtocolService.getCount();
			LOGGER.info("{} request count of study protocols", userAuthStorage);
			return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody StudyProtocolModel studyProtocolModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			StudyProtocol studyProtocol = conversionService.convert(studyProtocolModel, StudyProtocol.class);
			try {
				studyProtocolService.insert(studyProtocol);
				LOGGER.info("{} insert study protocol with id = {}", userAuthStorage, studyProtocol.getId());
				return new ResponseEntity<IntegerModel>(new IntegerModel(studyProtocol.getId()), HttpStatus.CREATED);
			} catch (IllegalArgumentException e) {
				LOGGER.info("{} has entered incorrect data : {}", userAuthStorage, e.getMessage());
				return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody StudyProtocolModel studyProtocolModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			StudyProtocol studyProtocol = conversionService.convert(studyProtocolModel, StudyProtocol.class);
			try {
				studyProtocolService.update(studyProtocol);
				LOGGER.info("{} update study protocol with id = {}", userAuthStorage, studyProtocol.getId());
				return new ResponseEntity<>(HttpStatus.CREATED);
			} catch (IllegalArgumentException e) {
				LOGGER.info("{} has entered incorrect data : {}", userAuthStorage, e.getMessage());
				return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			studyProtocolService.delete(id);
			LOGGER.info("{} delete study protocol with id = {}", userAuthStorage, id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.PATCH)
	public ResponseEntity<?> massDelete(@RequestBody Integer[] idArray) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			studyProtocolService.massDelete(idArray);
			LOGGER.info("{} delete study protocols with id = {}", userAuthStorage, Arrays.asList(idArray));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

}
