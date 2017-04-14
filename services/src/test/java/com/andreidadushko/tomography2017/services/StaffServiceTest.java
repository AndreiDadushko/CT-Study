package com.andreidadushko.tomography2017.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StaffForList;
import com.andreidadushko.tomography2017.dao.impl.db.filters.SortData;
import com.andreidadushko.tomography2017.dao.impl.db.filters.StaffFilter;
import com.andreidadushko.tomography2017.datamodel.Person;
import com.andreidadushko.tomography2017.datamodel.Staff;

public class StaffServiceTest extends AbstractTest {

	@Inject
	private IStaffService staffService;

	@Inject
	private IPersonService personService;

	private static List<Staff> testData;

	private static String login;

	private static Person person;

	@Before
	public void initializeTestData() {
		person = new Person();
		person.setFirstName("Иван");
		person.setMiddleName("Иванович");
		person.setLastName("Иванов");
		person.setBirthDate(new java.sql.Timestamp(new Date().getTime()));
		person.setPhoneNumber("+23414124124");
		person.setAdress("Минск");
		String l = Integer.toString(new Object().hashCode());
		person.setLogin(l);
		login = l;
		person.setPassword("password");
		person = personService.insert(person);

		Person person1 = new Person();
		person1.setLogin(Integer.toString(new Object().hashCode()));
		person1.setPassword("password");
		person1 = personService.insert(person1);

		testData = new ArrayList<Staff>();
		Staff staff0 = new Staff();
		testData.add(staff0);

		Staff staff1 = new Staff();
		staff1.setDepartment("РКД");
		staff1.setPosition("Лаборант");
		staff1.setPersonId(person1.getId());
		testData.add(staff1);

		Staff staff2 = new Staff();
		staff2.setDepartment("РКД");
		staff2.setPosition("Врач-рентгенолог");
		staff2.setStartDate(new java.sql.Timestamp(new Date().getTime()));
		staff2.setEndDate(new java.sql.Timestamp(new Date().getTime() + 10000));
		staff2.setPersonId(person.getId());
		testData.add(staff2);
	}

	@Test
	public void getTest() {
		Staff staff = staffService.insert(testData.get(2));
		Staff staffFromDB = staffService.get(staff.getId());
		Assert.assertTrue("Returned data is not correct", staff.equals(staffFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertNullTest() {
		staffService.insert(null);
		Assert.fail("Could not insert null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertEmptyStaffTest() {
		staffService.insert(testData.get(0));
		Assert.fail("Could not insert empty staff");
	}

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void insertWrongIdTest() {
		personService.delete(testData.get(2).getPersonId());
		staffService.insert(testData.get(2));
		Assert.fail("Could not insert staff without existing person");
	}

	@Test
	public void insertTest() {
		Staff staff = staffService.insert(testData.get(2));
		Staff staffFromDB = staffService.get(staff.getId());
		Assert.assertTrue("Inserted data is not correct", staff.equals(staffFromDB));
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateNullTest() {
		staffService.update(null);
		Assert.fail("Could not update null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateEmptyStaffTest() {
		staffService.update(testData.get(0));
		Assert.fail("Could not update empty staff");
	}

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void updateWrongIdTest() {
		Staff staff = staffService.insert(testData.get(2));
		personService.delete(testData.get(1).getPersonId());
		testData.get(1).setId(staff.getId());
		staffService.update(testData.get(1));
		Assert.fail("Could not update staff without existing person");
	}

	@Test
	public void updateTest() {
		Staff staff = staffService.insert(testData.get(2));
		testData.get(1).setId(staff.getId());
		staffService.update(testData.get(1));
		Staff staffFromDB = staffService.get(staff.getId());
		Assert.assertTrue("Updated data is not correct", testData.get(1).equals(staffFromDB));
	}

	@Test
	public void deleteTest() {
		Staff staff = staffService.insert(testData.get(2));
		staffService.delete(staff.getId());
		Staff staffFromDB = staffService.get(staff.getId());
		Assert.assertNull("Could not delete data", staffFromDB);
	}

	@Test
	public void getPositionsByLoginTest() {
		staffService.insert(testData.get(2));
		List<String> positionsFromDB = staffService.getPositionsByLogin(login);
		Assert.assertTrue("Number of returned positions is not correct", positionsFromDB.size() == 1);
		Assert.assertTrue("Returned position is not correct", positionsFromDB.get(0).equals("Врач-рентгенолог"));
	}

	@Test
	public void getCountTest() {
		int numberBeforeInsert = staffService.getCount();
		staffService.insert(testData.get(2));
		int numberAfterInsert = staffService.getCount();
		Assert.assertTrue("Returned count of rows is not correct", numberBeforeInsert + 1 == numberAfterInsert);
		staffService.delete(testData.get(2).getId());
		int numberAfterDelete = staffService.getCount();
		Assert.assertTrue("Returned count of rows is not correct", numberBeforeInsert == numberAfterDelete);
	}

	@Test
	public void getWithPaginationTest() {
		staffService.insert(testData.get(1));
		staffService.insert(testData.get(2));
		int number = staffService.getCount();
		List<StaffForList> listFromDB = staffService.getWithPagination(0, number);
		Assert.assertTrue("Number of returned rows is not correct", listFromDB.size() == number);
		listFromDB = staffService.getWithPagination(number, 0);
		Assert.assertTrue("Number of returned rows is not correct", listFromDB.size() == 0);
	}

	@Test
	public void getWithNullFilterTest() {
		int number = staffService.getCount();
		List<StaffForList> listFromDB = staffService.getWithPagination(0, number, null);
		Assert.assertTrue("Without filter must get all rows", number == listFromDB.size());
	}

	@Test
	public void getWithEmptyFilterTest() {
		int number = staffService.getCount();
		StaffFilter staffFilter = new StaffFilter();
		List<StaffForList> listFromDB = staffService.getWithPagination(0, number, staffFilter);
		Assert.assertTrue("With empty filter must get all rows", number == listFromDB.size());
	}

	@Test
	public void getWithFilterTest() {
		StaffFilter staffFilter = new StaffFilter();
		staffFilter.setFirstName(person.getFirstName());
		staffFilter.setMiddleName(person.getMiddleName());
		staffFilter.setLastName(person.getLastName());
		staffFilter.setDepartment(testData.get(2).getDepartment());
		staffFilter.setPosition(testData.get(2).getPosition());
		staffFilter.setStartFrom(new java.sql.Timestamp(testData.get(2).getStartDate().getTime() - 1000));
		staffFilter.setStartTo(new java.sql.Timestamp(testData.get(2).getStartDate().getTime() + 1000));
		staffFilter.setEndFrom(new java.sql.Timestamp(testData.get(2).getEndDate().getTime() - 1000));
		staffFilter.setEndTo(new java.sql.Timestamp(testData.get(2).getEndDate().getTime() + 1000));
		SortData sort = new SortData();
		sort.setColumn("department");
		sort.setOrder("ASC");
		staffFilter.setSort(sort);

		staffService.insert(testData.get(2));
		int number = staffService.getCount();
		List<StaffForList> listFromDB = staffService.getWithPagination(0, number, staffFilter);
		Assert.assertTrue("Number of returned rows is not correct", listFromDB.size() == 1);

		StaffForList testStaffForList = new StaffForList();
		testStaffForList.setId(testData.get(2).getId());
		testStaffForList.setFirstName(person.getFirstName());
		testStaffForList.setMiddleName(person.getMiddleName());
		testStaffForList.setLastName(person.getLastName());
		testStaffForList.setDepartment(testData.get(2).getDepartment());
		testStaffForList.setPosition(testData.get(2).getPosition());
		testStaffForList.setStartDate(new java.sql.Timestamp(testData.get(2).getStartDate().getTime()));
		testStaffForList.setEndDate(new java.sql.Timestamp(testData.get(2).getEndDate().getTime()));

		StaffForList staffForList = listFromDB.get(0);
		Assert.assertTrue("Work of filter is not correct", testStaffForList.equals(staffForList));
	}

}
