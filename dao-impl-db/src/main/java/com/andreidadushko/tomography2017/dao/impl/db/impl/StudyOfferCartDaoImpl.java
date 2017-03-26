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

import com.andreidadushko.tomography2017.dao.impl.db.IStudyOfferCartDao;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;

@Repository
public class StudyOfferCartDaoImpl implements IStudyOfferCartDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public StudyOfferCart get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from study_offer_cart where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<StudyOfferCart>(StudyOfferCart.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public StudyOfferCart insert(StudyOfferCart studyOfferCart) {
		final String INSERT_SQL = "INSERT INTO study_offer_cart (paid, pay_date, study_id, offer_id) VALUES (?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setBoolean(1, studyOfferCart.getPaid() != null ? studyOfferCart.getPaid() : false);
				ps.setTimestamp(2, studyOfferCart.getPayDate());
				ps.setInt(3, studyOfferCart.getStudyId());
				ps.setInt(4, studyOfferCart.getOfferId());

				return ps;
			}
		}, keyHolder);

		studyOfferCart.setId(keyHolder.getKey().intValue());

		return studyOfferCart;
	}

	@Override
	public void update(StudyOfferCart studyOfferCart) {

		final String INSERT_SQL = " UPDATE study_offer_cart SET paid=?, pay_date=?, study_id=?, offer_id=? WHERE id=?";

		jdbcTemplate.update(INSERT_SQL, new Object[] { studyOfferCart.getPaid(), studyOfferCart.getPayDate(),
				studyOfferCart.getStudyId(), studyOfferCart.getOfferId(), studyOfferCart.getId() });

	}

	@Override
	public void delete(Integer id) {

		jdbcTemplate.update("delete from study_offer_cart where id=" + id);

	}

	@Override
	public List<StudyOfferCart> getAll() {

		List<StudyOfferCart> rs = jdbcTemplate.query("select * from study_offer_cart order by id",
				new BeanPropertyRowMapper<StudyOfferCart>(StudyOfferCart.class));
		return rs;

	}

}
