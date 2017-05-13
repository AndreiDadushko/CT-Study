package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andreidadushko.tomography2017.dao.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.dao.db.filters.StudyFilter;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.services.IStudyService;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.StudyFilterModel;
import com.andreidadushko.tomography2017.webapp.models.StudyForListModel;
import com.andreidadushko.tomography2017.webapp.models.StudyModel;
import com.andreidadushko.tomography2017.webapp.storage.CurrentUserData;

@RestController
@RequestMapping("/study")
public class StudyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudyController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private IStudyService studyService;

	@Inject
	private ConversionService conversionService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			Study study = studyService.get(id);
			StudyModel convertedStudy = null;
			if (study != null) {
				convertedStudy = conversionService.convert(study, StudyModel.class);
			}
			LOGGER.info("{} request study with id = {}", userAuthStorage, id);
			return new ResponseEntity<StudyModel>(convertedStudy, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			Integer result = studyService.getCount();
			LOGGER.info("{} request count of studies", userAuthStorage);
			return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody StudyModel studyModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			Study study = conversionService.convert(studyModel, Study.class);
			try {
				studyService.insert(study);
				LOGGER.info("{} insert study with id = {}", userAuthStorage, study.getId());
				return new ResponseEntity<IntegerModel>(new IntegerModel(study.getId()), HttpStatus.CREATED);
			} catch (IllegalArgumentException e) {
				LOGGER.info("{} has entered incorrect data : {}", userAuthStorage, e.getMessage());
				return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody StudyModel studyModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			Study study = conversionService.convert(studyModel, Study.class);
			try {
				studyService.update(study);
				LOGGER.info("{} update study with id = {}", userAuthStorage, study.getId());
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
			studyService.delete(id);
			LOGGER.info("{} delete study with id = {}", userAuthStorage, id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.PATCH)
	public ResponseEntity<?> massDelete(@RequestBody Integer[] idArray) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			studyService.massDelete(idArray);
			LOGGER.info("{} delete studies with id = {}", userAuthStorage, Arrays.asList(idArray));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/person/{personId}", method = RequestMethod.GET)
	public ResponseEntity<?> getStudyForListByPersonId(@PathVariable Integer personId) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")
				|| userAuthStorage.getId().equals(personId)) {
			try {
				List<StudyForList> list = studyService.getStudyForListByPersonId(personId);
				List<StudyForListModel> convertedStudyForList = new ArrayList<StudyForListModel>();
				for (StudyForList studyForList : list) {
					convertedStudyForList.add(conversionService.convert(studyForList, StudyForListModel.class));
				}
				LOGGER.info("{} request studies with person id = {}", userAuthStorage, personId);
				return new ResponseEntity<List<StudyForListModel>>(convertedStudyForList, HttpStatus.OK);
			} catch (UnsupportedOperationException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getWithPagination(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			try {
				List<StudyForList> list = studyService.getWithPagination(offset, limit);
				List<StudyForListModel> convertedStudyForList = new ArrayList<StudyForListModel>();
				for (StudyForList studyForList : list) {
					convertedStudyForList.add(conversionService.convert(studyForList, StudyForListModel.class));
				}
				LOGGER.info("{} request studies with offset = {}, limit = {}", userAuthStorage, offset, limit);
				return new ResponseEntity<List<StudyForListModel>>(convertedStudyForList, HttpStatus.OK);
			} catch (UnsupportedOperationException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.PATCH)
	public ResponseEntity<?> getWithPaginationAndFilter(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit, @RequestBody StudyFilterModel studyFilterModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			try {
				StudyFilter studyFilter = conversionService.convert(studyFilterModel, StudyFilter.class);
				List<StudyForList> list = studyService.getWithPagination(offset, limit, studyFilter);
				List<StudyForListModel> convertedStudyForList = new ArrayList<StudyForListModel>();
				for (StudyForList studyForList : list) {
					convertedStudyForList.add(conversionService.convert(studyForList, StudyForListModel.class));
				}
				LOGGER.info("{} request studies with offset = {}, limit = {}, filter = {}", userAuthStorage, offset,
						limit, studyFilter);
				return new ResponseEntity<List<StudyForListModel>>(convertedStudyForList, HttpStatus.OK);
			} catch (UnsupportedOperationException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

}
