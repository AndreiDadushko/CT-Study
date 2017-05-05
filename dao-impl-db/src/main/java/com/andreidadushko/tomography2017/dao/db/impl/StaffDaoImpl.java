package com.andreidadushko.tomography2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IStaffDao;
import com.andreidadushko.tomography2017.dao.db.custom.models.StaffForList;
import com.andreidadushko.tomography2017.dao.db.filters.StaffFilter;
import com.andreidadushko.tomography2017.dao.db.utils.FilterUtil;
import com.andreidadushko.tomography2017.datamodel.Staff;

@Repository
public class StaffDaoImpl extends AbstractDaoImpl<Staff> implements IStaffDao {

	@Override
	public String getSelectQuery() {
		return " SELECT * FROM staff";
	}

	@Override
	public String getInsertQuery() {
		return " INSERT INTO staff (department, position, start_date, end_date, person_id) VALUES (?,?,?,?,?)";
	}

	@Override
	public String getUpdateQuery() {
		return " UPDATE  staff SET department = ?, position = ?, start_date = ?, end_date = ?, person_id = ? WHERE id = ?";
	}

	@Override
	public String getDeleteQuery() {
		return " DELETE FROM staff WHERE id=";
	}

	public String getQueryStaffForList() {
		return " SELECT s.id, p.last_name, p.first_name, p.middle_name, s.department, s.position, s.start_date, s.end_date FROM staff s LEFT JOIN person p ON s.person_id = p.id ";
	}

	public String getQueryPositionsByLogin() {
		return " SELECT s.position FROM staff s LEFT JOIN person p ON s.person_id = p.id WHERE p.login = ?";
	}

	@Override
	public String getCountQuery() {
		return "SELECT COUNT(*) FROM staff";
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

	@Override
	public List<String> getPositionsByLogin(String login) {
		String sql = getQueryPositionsByLogin();
		List<String> rs = jdbcTemplate.queryForList(sql, new Object[] { login }, String.class);
		return rs;
	}

	@Override
	public List<StaffForList> getWithPagination(int offset, int limit) {
		String sql = getQueryStaffForList();
		List<StaffForList> rs = jdbcTemplate.query(sql + " LIMIT ?,?", new Object[] { offset, limit },
				new BeanPropertyRowMapper<StaffForList>(StaffForList.class));
		return rs;
	}

	@Override
	public List<StaffForList> getWithPagination(int offset, int limit, StaffFilter staffFilter) {
		String sql = getQueryStaffForList();
		StringBuilder whereCause = new StringBuilder();
		List<Object> objects = new ArrayList<Object>();
		List<String> sqlParts = new ArrayList<String>();
		if (staffFilter != null) {
			if (staffFilter.getFirstName() != null) {
				sqlParts.add("first_name = ?");
				objects.add(staffFilter.getFirstName());
			}
			if (staffFilter.getLastName() != null) {
				sqlParts.add("last_name = ?");
				objects.add(staffFilter.getLastName());
			}
			if (staffFilter.getMiddleName() != null) {
				sqlParts.add("middle_name = ?");
				objects.add(staffFilter.getMiddleName());
			}
			if (staffFilter.getDepartment() != null) {
				sqlParts.add("department = ?");
				objects.add(staffFilter.getDepartment());
			}
			if (staffFilter.getPosition() != null) {
				sqlParts.add("position = ?");
				objects.add(staffFilter.getPosition());
			}
			if (staffFilter.getStartFrom() != null) {
				sqlParts.add("start_date >= ?");
				objects.add(staffFilter.getStartFrom());
			}
			if (staffFilter.getStartTo() != null) {
				sqlParts.add("start_date <= ?");
				objects.add(staffFilter.getStartTo());
			}
			if (staffFilter.getEndFrom() != null) {
				sqlParts.add("end_date >= ?");
				objects.add(staffFilter.getEndFrom());
			}
			if (staffFilter.getEndTo() != null) {
				sqlParts.add("end_date <= ?");
				objects.add(staffFilter.getEndTo());
			}
			FilterUtil.makeWhere(whereCause, sqlParts);
			if (staffFilter.getSort() != null && staffFilter.getSort().getColumn() != null) {
				FilterUtil.makeSort(whereCause, staffFilter.getSort());
			}
		}
		objects.add(offset);
		objects.add(limit);
		List<StaffForList> rs = jdbcTemplate.query(sql + whereCause + " LIMIT ?,?", objects.toArray(),
				new BeanPropertyRowMapper<StaffForList>(StaffForList.class));
		return rs;
	}

	@Override
	public List<Staff> getAll() {
		throw new UnsupportedOperationException();
	}

}
