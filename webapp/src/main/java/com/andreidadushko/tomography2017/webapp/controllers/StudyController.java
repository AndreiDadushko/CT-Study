package com.andreidadushko.tomography2017.webapp.controllers;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.services.IStudyService;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.StudyModel;

@RestController
@RequestMapping("/study")
public class StudyController {

	@Inject
	private IStudyService studyService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Study study = studyService.get(id);
		StudyModel convertedStudy = null;
		if (study != null) {
			convertedStudy = studyEntity2StudyModel(study);
		}
		return new ResponseEntity<StudyModel>(convertedStudy, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody StudyModel studyModel) {
		Study study = studyModel2studyEntity(studyModel);
		try {
			studyService.insert(study);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<IntegerModel>(new IntegerModel(study.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody StudyModel studyModel) {
		Study study = studyModel2studyEntity(studyModel);
		try {
			studyService.update(study);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		studyService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		Integer result = studyService.getCount();
		return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
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
}
