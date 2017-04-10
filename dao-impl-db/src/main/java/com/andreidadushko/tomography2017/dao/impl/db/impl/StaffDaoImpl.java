package com.andreidadushko.tomography2017.dao.impl.db.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.impl.db.IStaffDao;
import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StaffForList;
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
	public List<StaffForList> getAllStaffForList() {
		String sql = getQueryStaffForList();
		List<StaffForList> rs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<StaffForList>(StaffForList.class));
		return rs;
	}

	@Override
	public List<String> getPositionsByLogin(String login) {
		String sql = getQueryPositionsByLogin();
		List<String> rs = jdbcTemplate.query(sql, new Object[] { login }, new PositionsByLoginMapper());
		//List<String> rss = jdbcTemplate.queryForList(sql, String.class); ПРОВЕРИТЬ!!!
		return rs;
	}

	private final class PositionsByLoginMapper implements RowMapper<String> {
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {

			return rs.getString("position");
		}
	}

}
