package com.andreidadushko.tomography2017.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyProtocolDao;
import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

@Repository
public class StudyProtocolDaoImpl implements IStudyProtocolDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public StudyProtocol get(Integer id) {

		try {
			return jdbcTemplate.queryForObject("select * from study_protocol where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<StudyProtocol>(StudyProtocol.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void insert(StudyProtocol studyProtocol) {
		final String INSERT_SQL = "INSERT INTO study_protocol (id, protocol, creation_date) VALUES (?,?,NOW())";

		jdbcTemplate.update(INSERT_SQL, new Object[] { studyProtocol.getId(), studyProtocol.getProtocol() });

	}

	@Override
	public void update(StudyProtocol studyProtocol) {

		final String INSERT_SQL = "UPDATE study_protocol SET protocol=?,creation_date=NOW() WHERE id=?";

		jdbcTemplate.update(INSERT_SQL, new Object[] { studyProtocol.getProtocol(), studyProtocol.getId() });

	}

	@Override
	public void delete(Integer id) {

		jdbcTemplate.update("delete from study_protocol where id=" + id);

	}

	@Override
	public List<StudyProtocol> getAll() {

		List<StudyProtocol> rs = jdbcTemplate.query("select * from study_protocol order by id",
				new BeanPropertyRowMapper<StudyProtocol>(StudyProtocol.class));
		return rs;

	}

}
