package com.andreidadushko.tomography2017.dao.impl.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.impl.db.IStaffDao;
import com.andreidadushko.tomography2017.datamodel.Staff;

@Repository
public class StaffDaoImpl extends AbstractDaoImpl<Staff> implements IStaffDao {

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM staff";
	}

	@Override
	public String getInsertQuery() {
		return "INSERT INTO staff (department, position, start_date, end_date, person_id) VALUES (?,?,?,?,?)";
	}

	@Override
	public String getUpdateQuery() {
		return " UPDATE  staff SET department = ?, position = ?, start_date = ?, end_date = ?, person_id = ? WHERE id = ?";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM staff WHERE id=";
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, Staff staff) throws SQLException {
		ps.setString(1, staff.getDepartment());
		ps.setString(2, staff.getPosition());
		ps.setTimestamp(3, staff.getStartDate());
		ps.setTimestamp(4, staff.getEndDate());
		ps.setInt(5, staff.getPersonId());
	}

	@Override
	protected Object[] argumentsForUpdate(Staff staff) {
		return new Object[] { staff.getDepartment(), staff.getPosition(), staff.getStartDate(), staff.getEndDate(),
				staff.getPersonId(), staff.getId() };
	}

	@Override
	protected Class<Staff> getClassForMapping() {
		return Staff.class;
	}

	@Override
	protected void setIdAfterInsert(KeyHolder keyHolder, Staff staff) {
		staff.setId(keyHolder.getKey().intValue());
	}

}
