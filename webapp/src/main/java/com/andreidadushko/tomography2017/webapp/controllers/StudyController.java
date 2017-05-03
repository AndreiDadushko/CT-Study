package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andreidadushko.tomography2017.dao.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.dao.db.filters.SortData;
import com.andreidadushko.tomography2017.dao.db.filters.StudyFilter;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.services.IStudyService;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.StudyFilterModel;
import com.andreidadushko.tomography2017.webapp.models.StudyForListModel;
import com.andreidadushko.tomography2017.webapp.models.StudyModel;
import com.andreidadushko.tomography2017.webapp.storage.UserAuthStorage;

@RestController
@RequestMapping("/study")
public class StudyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudyController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private IStudyService studyService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			Study study = studyService.get(id);
			StudyModel convertedStudy = null;
			if (study != null) {
				convertedStudy = studyEntity2StudyModel(study);
			}
			LOGGER.info("{} request study with id = {}", userAuthStorage, id);
			return new ResponseEntity<StudyModel>(convertedStudy, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
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
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			Study study = studyModel2studyEntity(studyModel);
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
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			Study study = studyModel2studyEntity(studyModel);
			try {
				studyService.update(study);
				LOGGER.info("{} update study with id = {}", userAuthStorage, study.getId());
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			} catch (IllegalArgumentException e) {
				LOGGER.info("{} has entered incorrect data : {}", userAuthStorage, e.getMessage());
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
			studyService.delete(id);
			LOGGER.info("{} delete study with id = {}", userAuthStorage, id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<?> massDelete(@RequestBody Integer[] idArray) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			studyService.massDelete(idArray);
			LOGGER.info("{} delete studies with id = {}", userAuthStorage, Arrays.asList(idArray));
			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/person/{personId}", method = RequestMethod.GET)
	public ResponseEntity<?> getStudyForListByPersonId(@PathVariable Integer personId) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")
				|| userAuthStorage.getId().equals(personId)) {
			try {
				List<StudyForList> list = studyService.getStudyForListByPersonId(personId);
				List<StudyForListModel> convertedStudyForList = new ArrayList<StudyForListModel>();
				for (StudyForList studyForList : list) {
					convertedStudyForList.add(studyEntity2StudyModel(studyForList));
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
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			try {
				List<StudyForList> list = studyService.getWithPagination(offset, limit);
				List<StudyForListModel> convertedStudyForList = new ArrayList<StudyForListModel>();
				for (StudyForList studyForList : list) {
					convertedStudyForList.add(studyEntity2StudyModel(studyForList));
				}
				LOGGER.info("{} request studies with offset = {}, limit = {}", userAuthStorage, offset, limit);
				return new ResponseEntity<List<StudyForListModel>>(convertedStudyForList, HttpStatus.OK);
			} catch (UnsupportedOperationException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public ResponseEntity<?> getWithPaginationAndFilter(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit, @RequestBody StudyFilterModel studyFilterModel) {
		UserAuthStorage userAuthStorage = context.getBean(UserAuthStorage.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			try {
				StudyFilter studyFilter = studyFilterModel2StudyFilter(studyFilterModel);
				List<StudyForList> list = studyService.getWithPagination(offset, limit, studyFilter);
				List<StudyForListModel> convertedStudyForList = new ArrayList<StudyForListModel>();
				for (StudyForList studyForList : list) {
					convertedStudyForList.add(studyEntity2StudyModel(studyForList));
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

	private StudyModel studyEntity2StudyModel(Study study) {
		StudyModel studyModel = new StudyModel();
		studyModel.setId(study.getId());
		studyModel.setAppointmentDate(study.getAppointmentDate() == null ? null : study.getAppointmentDate().getTime());
		studyModel.setPermitted(study.getPermitted());
		studyModel.setPersonId(study.getPersonId());
		studyModel.setStaffId(study.getStaffId());
		return studyModel;
	}

	private Study studyModel2studyEntity(StudyModel studyModel) {
		Study study = new Study();
		study.setId(studyModel.getId());
		study.setAppointmentDate(studyModel.getAppointmentDate() == null ? null
				: new java.sql.Timestamp(studyModel.getAppointmentDate()));
		study.setPermitted(studyModel.getPermitted());
		study.setPersonId(studyModel.getPersonId());
		study.setStaffId(studyModel.getStaffId());
		return study;
	}

	private StudyForListModel studyEntity2StudyModel(StudyForList studyForList) {
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

	private StudyFilter studyFilterModel2StudyFilter(StudyFilterModel studyFilterModel) {
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