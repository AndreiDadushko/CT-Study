package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
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

import com.andreidadushko.tomography2017.dao.db.custom.models.StaffForList;
import com.andreidadushko.tomography2017.dao.db.filters.StaffFilter;
import com.andreidadushko.tomography2017.datamodel.Staff;
import com.andreidadushko.tomography2017.services.IStaffService;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.StaffFilterModel;
import com.andreidadushko.tomography2017.webapp.models.StaffForListModel;
import com.andreidadushko.tomography2017.webapp.models.StaffModel;
import com.andreidadushko.tomography2017.webapp.storage.CurrentUserData;

@RestController
@RequestMapping("/staff")
public class StaffController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaffController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private IStaffService staffService;

	@Inject
	private ConversionService conversionService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Staff staff = staffService.get(id);
		StaffModel convertedStuff = null;
		if (staff != null) {
			convertedStuff = conversionService.convert(staff, StaffModel.class);
		}
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		LOGGER.info("{} request staff with id = {}", userAuthStorage, id);
		return new ResponseEntity<StaffModel>(convertedStuff, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody StaffModel staffModel) {
		Staff staff = conversionService.convert(staffModel, Staff.class);
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		try {
			staffService.insert(staff);
			LOGGER.info("{} insert staff with id = {}", userAuthStorage, staff.getId());
			return new ResponseEntity<IntegerModel>(new IntegerModel(staff.getId()), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			LOGGER.info("{} has entered incorrect data : {}", userAuthStorage, e.getMessage());
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody StaffModel staffModel) {
		Staff staff = conversionService.convert(staffModel, Staff.class);
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		try {
			staffService.update(staff);
			LOGGER.info("{} update staff with id = {}", userAuthStorage, staff.getId());
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			LOGGER.info("{} has entered incorrect data : {}", userAuthStorage, e.getMessage());
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		try {
			staffService.delete(id);
			CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
			LOGGER.info("{} delete staff with id = {}", userAuthStorage, id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		Integer result = staffService.getCount();
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		LOGGER.info("{} request count of staff", userAuthStorage);
		return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
	}

	@RequestMapping(value = "/positions", method = RequestMethod.GET)
	public ResponseEntity<?> getPositionsByLogin(@RequestParam(required = true) String login) {
		List<String> allPositions = staffService.getPositionsByLogin(login);
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		LOGGER.info("{} request positions of staff with login = {}", userAuthStorage, login);
		return new ResponseEntity<List<String>>(allPositions, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getWithPagination(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit) {
		List<StaffForList> allStaff = staffService.getWithPagination(offset, limit);
		List<StaffForListModel> convertedStaffForList = new ArrayList<StaffForListModel>();
		for (StaffForList staffForList : allStaff) {
			convertedStaffForList.add(conversionService.convert(staffForList, StaffForListModel.class));
		}
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		LOGGER.info("{} request staff with offset = {}, limit = {}", userAuthStorage, offset, limit);
		return new ResponseEntity<List<StaffForListModel>>(convertedStaffForList, HttpStatus.OK);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.PATCH)
	public ResponseEntity<?> getWithPaginationAndFilter(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit, @RequestBody StaffFilterModel staffFilterModel) {
		try {
			StaffFilter staffFilter = conversionService.convert(staffFilterModel, StaffFilter.class);
			List<StaffForList> allStaff = staffService.getWithPagination(offset, limit, staffFilter);
			List<StaffForListModel> convertedStaffForList = new ArrayList<StaffForListModel>();
			for (StaffForList staffForList : allStaff) {
				convertedStaffForList.add(conversionService.convert(staffForList, StaffForListModel.class));
			}
			CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
			LOGGER.info("{} request staff with offset = {}, limit = {}, filter = {}", userAuthStorage, offset, limit,
					staffFilter);
			return new ResponseEntity<List<StaffForListModel>>(convertedStaffForList, HttpStatus.OK);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
