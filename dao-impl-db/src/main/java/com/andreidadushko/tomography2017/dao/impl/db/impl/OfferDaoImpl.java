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

import com.andreidadushko.tomography2017.dao.impl.db.IOfferDao;
import com.andreidadushko.tomography2017.datamodel.Offer;

@Repository
public class OfferDaoImpl implements IOfferDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Offer get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from offer where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Offer>(Offer.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Offer insert(Offer offer) {
		final String INSERT_SQL = "INSERT INTO offer (name, price, categor_id) VALUES (?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, offer.getName());
				ps.setDouble(2, offer.getPrice());
				ps.setInt(3, offer.getCategorId());

				return ps;
			}
		}, keyHolder);

		offer.setId(keyHolder.getKey().intValue());

		return offer;
	}

	@Override
	public void update(Offer offer) {

		final String INSERT_SQL = "UPDATE offer SET name=?, price=?, categor_id=? WHERE id=?";

		jdbcTemplate.update(INSERT_SQL,
				new Object[] { offer.getName(), offer.getPrice(), offer.getCategorId(), offer.getId() });

	}

	@Override
	public void delete(Integer id) {

		jdbcTemplate.update("delete from offer where id=" + id);

	}

	@Override
	public List<Offer> getAll() {

		List<Offer> rs = jdbcTemplate.query("select * from offer",
				new BeanPropertyRowMapper<Offer>(Offer.class));
		return rs;

	}

}
