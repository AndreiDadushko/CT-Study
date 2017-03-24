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

import com.andreidadushko.tomography2017.dao.impl.db.IStudyDao;
import com.andreidadushko.tomography2017.datamodel.Study;

@Repository
public class StudyDaoImpl implements IStudyDao{

	@Inject
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Study get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from study where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Study>(Study.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Study insert(Study study) {
		final String INSERT_SQL = "INSERT INTO study (appointment_date, permitted, person_id, staff_id) VALUES (?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setDate(1, new java.sql.Date(study.getAppointmentDate().getTime()));
				ps.setBoolean(2, study.getPermitted());
				ps.setInt(3, study.getPersonId());
				ps.setInt(4, study.getStaffId());
				
				return ps;
			}
		}, keyHolder);

		study.setId(keyHolder.getKey().intValue());

		return study;
	}

	@Override
	public void update(Study study) {

		final String INSERT_SQL= "UPDATE  study SET  appointment_date = ?, permitted= ?,person_id= ?,staff_id =? WHERE  id = ?";
		
		jdbcTemplate.update(INSERT_SQL,new Object[]{study.getAppointmentDate(),study.getPermitted(),study.getPersonId(),study.getStaffId(),study.getId()});
				
	}

	@Override
	public void delete(Integer id) {

		jdbcTemplate.update("delete from study where id=" + id);
		
	}

	@Override
	public List<Study> getAll() {
		
		List<Study> rs = jdbcTemplate.query("select * from study order by id", new BeanPropertyRowMapper<Study>(Study.class));
		return rs;
		
	}

}
