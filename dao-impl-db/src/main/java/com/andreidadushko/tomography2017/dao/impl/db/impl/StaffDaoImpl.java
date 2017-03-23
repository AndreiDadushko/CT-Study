package com.andreidadushko.tomography2017.dao.impl.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.impl.db.IStaffDao;
import com.andreidadushko.tomography2017.datamodel.Staff;

@Repository
public class StaffDaoImpl implements IStaffDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Staff get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from staff where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Staff>(Staff.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Staff insert(Staff staff) {
		final String INSERT_SQL = "INSERT INTO staff (department, position, start_date, end_date, person_id) VALUES (?,?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, staff.getDepartment());
				ps.setString(2, staff.getPosition());
				ps.setDate(3, staff.getStartDate() != null ? new java.sql.Date(staff.getStartDate().getTime()) : null);
				ps.setDate(4, staff.getEndDate() != null ? new java.sql.Date(staff.getEndDate().getTime()) : null);
				ps.setInt(5, staff.getPersonId());

				return ps;
			}
		}, keyHolder);

		staff.setId(keyHolder.getKey().intValue());

		return staff;
	}

	@Override
	public void update(Staff staff) {

		final String INSERT_SQL = " UPDATE  staff SET department = ?, position = ?, start_date = ?, end_date = ?, person_id = ? WHERE id = ?";

		jdbcTemplate.update(INSERT_SQL, new Object[] { staff.getDepartment(), staff.getPosition(), staff.getStartDate(),
				staff.getEndDate(), staff.getPersonId(), staff.getId() });

	}

	@Override
	public void delete(Integer id) {

		jdbcTemplate.update("delete from staff where id=" + id);

	}

	@Override
	public List<Staff> getAll() {
		List<Staff> rs = jdbcTemplate.query("select * from staff group by id",
				new BeanPropertyRowMapper<Staff>(Staff.class));
		return rs;
	}
}
