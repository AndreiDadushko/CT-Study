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

import com.andreidadushko.tomography2017.dao.impl.db.IStudyServiceCartDao;
import com.andreidadushko.tomography2017.datamodel.StudyServiceCart;

@Repository
public class StudyServiceCartDaoImpl implements IStudyServiceCartDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public StudyServiceCart get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from study_service_cart where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<StudyServiceCart>(StudyServiceCart.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public StudyServiceCart insert(StudyServiceCart studyServiceCart) {
		final String INSERT_SQL = "INSERT INTO study_service_cart (paid, pay_date, study_id, service_id) VALUES (?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setBoolean(1, studyServiceCart.getPaid() != null ? studyServiceCart.getPaid() : false);
				ps.setTimestamp(2, studyServiceCart.getPayDate());
				ps.setInt(3, studyServiceCart.getStudyId());
				ps.setInt(4, studyServiceCart.getServiceId());

				return ps;
			}
		}, keyHolder);

		studyServiceCart.setId(keyHolder.getKey().intValue());

		return studyServiceCart;
	}

	@Override
	public void update(StudyServiceCart studyServiceCart) {

		final String INSERT_SQL = " UPDATE study_service_cart SET paid=?, pay_date=?, study_id=?,service_id=? WHERE id=?";

		jdbcTemplate.update(INSERT_SQL, new Object[] { studyServiceCart.getPaid(), studyServiceCart.getPayDate(),
				studyServiceCart.getStudyId(), studyServiceCart.getServiceId(), studyServiceCart.getId() });

	}

	@Override
	public void delete(Integer id) {

		jdbcTemplate.update("delete from study_service_cart where id=" + id);

	}

	@Override
	public List<StudyServiceCart> getAll() {

		List<StudyServiceCart> rs = jdbcTemplate.query("select * from study_service_cart order by id",
				new BeanPropertyRowMapper<StudyServiceCart>(StudyServiceCart.class));
		return rs;

	}

}
