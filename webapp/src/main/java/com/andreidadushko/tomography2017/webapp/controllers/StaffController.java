package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
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

import com.andreidadushko.tomography2017.dao.db.custom.models.StaffForList;
import com.andreidadushko.tomography2017.dao.db.filters.SortData;
import com.andreidadushko.tomography2017.dao.db.filters.StaffFilter;
import com.andreidadushko.tomography2017.datamodel.Staff;
import com.andreidadushko.tomography2017.services.IStaffService;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.StaffFilterModel;
import com.andreidadushko.tomography2017.webapp.models.StaffForListModel;
import com.andreidadushko.tomography2017.webapp.models.StaffModel;

@RestController
@RequestMapping("/staff")
public class StaffController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaffController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private IStaffService staffService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Staff staff = staffService.get(id);
		StaffModel convertedStuff = null;
		if (staff != null) {
			convertedStuff = staffEntity2StaffModel(staff);
		}
		return new ResponseEntity<StaffModel>(convertedStuff, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody StaffModel staffModel) {
		Staff staff = personModel2PersonEntity(staffModel);
		try {
			staffService.insert(staff);
			return new ResponseEntity<IntegerModel>(new IntegerModel(staff.getId()), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody StaffModel staffModel) {
		Staff staff = personModel2PersonEntity(staffModel);
		try {
			staffService.update(staff);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		try {
			staffService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		Integer result = staffService.getCount();
		return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
	}

	@RequestMapping(value = "/positions", method = RequestMethod.GET)
	public ResponseEntity<?> getPositionsByLogin(@RequestParam(required = true) String login) {
		List<String> allPositions = staffService.getPositionsByLogin(login);
		return new ResponseEntity<List<String>>(allPositions, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getWithPagination(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit) {
		List<StaffForList> allStaff = staffService.getWithPagination(offset, limit);
		List<StaffForListModel> convertedStaffForList = new ArrayList<StaffForListModel>();
		for (StaffForList staffForList : allStaff) {
			convertedStaffForList.add(staffForListEntity2StaffForListModel(staffForList));
		}
		return new ResponseEntity<List<StaffForListModel>>(convertedStaffForList, HttpStatus.OK);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public ResponseEntity<?> getWithPaginationAndFilter(@RequestParam(required = true) Integer offset,
			@RequestParam(required = true) Integer limit, @RequestBody StaffFilterModel staffFilterModel) {
		try {
			StaffFilter staffFilter = staffFilterModel2StaffFilter(staffFilterModel);
			List<StaffForList> allStaff = staffService.getWithPagination(offset, limit, staffFilter);
			List<StaffForListModel> convertedStaffForList = new ArrayList<StaffForListModel>();
			for (StaffForList staffForList : allStaff) {
				convertedStaffForList.add(staffForListEntity2StaffForListModel(staffForList));
			}
			return new ResponseEntity<List<StaffForListModel>>(convertedStaffForList, HttpStatus.OK);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	private StaffModel staffEntity2StaffModel(Staff staff) {
		StaffModel staffModel = new StaffModel();
		staffModel.setId(staff.getId());
		staffModel.setDepartment(staff.getDepartment());
		staffModel.setPosition(staff.getPosition());
		staffModel.setStartDate(staff.getStartDate() == null ? null : staff.getStartDate().getTime());
		staffModel.setEndDate(staff.getEndDate() == null ? null : staff.getEndDate().getTime());
		staffModel.setPersonId(staff.getPersonId());
		return staffModel;
	}

	private Staff personModel2PersonEntity(StaffModel staffModel) {
		Staff staff = new Staff();
		staff.setId(staffModel.getId());
		staff.setDepartment(staffModel.getDepartment());
		staff.setPosition(staffModel.getPosition());
		staff.setStartDate(
				staffModel.getStartDate() == null ? null : new java.sql.Timestamp(staffModel.getStartDate()));
		staff.setEndDate(staffModel.getEndDate() == null ? null : new java.sql.Timestamp(staffModel.getEndDate()));
		staff.setPersonId(staffModel.getPersonId());
		return staff;
	}

	private StaffForListModel staffForListEntity2StaffForListModel(StaffForList staffForList) {
		StaffForListModel staffForListModel = new StaffForListModel();
		staffForListModel.setId(staffForList.getId());
		staffForListModel.setFirstName(staffForList.getFirstName());
		staffForListModel.setMiddleName(staffForList.getMiddleName());
		staffForListModel.setLastName(staffForList.getLastName());
		staffForListModel.setDepartment(staffForList.getDepartment());
		staffForListModel.setPosition(staffForList.getPosition());
		staffForListModel
				.setStartDate(staffForList.getStartDate() == null ? null : staffForList.getStartDate().getTime());
		staffForListModel.setEndDate(staffForList.getEndDate() == null ? null : staffForList.getEndDate().getTime());
		return staffForListModel;
	}

	private StaffFilter staffFilterModel2StaffFilter(StaffFilterModel staffFilterModel) {
		StaffFilter staffFilter = new StaffFilter();
		staffFilter.setFirstName(staffFilterModel.getFirstName());
		staffFilter.setMiddleName(staffFilterModel.getMiddleName());
		staffFilter.setLastName(staffFilterModel.getLastName());
		staffFilter.setDepartment(staffFilterModel.getDepartment());
		staffFilter.setStartFrom(staffFilterModel.getStartFrom() == null ? null
				: new java.sql.Timestamp(staffFilterModel.getStartFrom()));
		staffFilter.setStartTo(
				staffFilterModel.getStartTo() == null ? null : new java.sql.Timestamp(staffFilterModel.getStartTo()));
		staffFilter.setEndFrom(
				staffFilterModel.getEndFrom() == null ? null : new java.sql.Timestamp(staffFilterModel.getEndFrom()));
		staffFilter.setEndTo(
				staffFilterModel.getEndTo() == null ? null : new java.sql.Timestamp(staffFilterModel.getEndTo()));
		if (staffFilterModel.getSort() != null) {
			SortData sortData = new SortData();
			sortData.setColumn(staffFilterModel.getSort().getColumn());
			sortData.setOrder(staffFilterModel.getSort().getOrder());
			staffFilter.setSort(sortData);
		}
		return staffFilter;
	}
}
