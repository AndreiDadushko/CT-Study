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

import com.andreidadushko.tomography2017.dao.impl.db.IServiceDao;
import com.andreidadushko.tomography2017.datamodel.Service;

@Repository
public class ServiceDaoImpl implements IServiceDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Service get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from service where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Service>(Service.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Service insert(Service service) {
		final String INSERT_SQL = "INSERT INTO service (name, price, categor_id) VALUES (?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, service.getName());
				ps.setDouble(2, service.getPrice());
				ps.setInt(3, service.getCategorId());

				return ps;
			}
		}, keyHolder);

		service.setId(keyHolder.getKey().intValue());

		return service;
	}

	@Override
	public void update(Service service) {

		final String INSERT_SQL = "UPDATE service SET name=?, price=?, categor_id=? WHERE id=?";

		jdbcTemplate.update(INSERT_SQL,
				new Object[] { service.getName(), service.getPrice(), service.getCategorId(), service.getId() });

	}

	@Override
	public void delete(Integer id) {

		jdbcTemplate.update("delete from service where id=" + id);

	}

	@Override
	public List<Service> getAll() {

		List<Service> rs = jdbcTemplate.query("select * from service order by id",
				new BeanPropertyRowMapper<Service>(Service.class));
		return rs;

	}

}
