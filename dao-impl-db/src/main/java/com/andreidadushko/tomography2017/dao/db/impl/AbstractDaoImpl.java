package com.andreidadushko.tomography2017.dao.db.impl;

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

import com.andreidadushko.tomography2017.dao.db.IAbstractDao;
import com.andreidadushko.tomography2017.dao.db.cache.QueryCache;

public abstract class AbstractDaoImpl<T> implements IAbstractDao<T> {

	@Inject
	protected JdbcTemplate jdbcTemplate;

	@Inject
	protected QueryCache queryCache;
	
	/**
	 * @return "SELECT * FROM table"
	 */
	public abstract String getSelectQuery();

	/**
	 * @return INSERT INTO table (...) VALUES (?,?..)"
	 */
	public abstract String getInsertQuery();
	
	/**
	 * @return "UPDATE table SET field = ?.... WHERE id = ?"
	 */
	public abstract String getUpdateQuery();

	/**
	 * @return "DELETE FROM table WHERE id="
	 */
	public abstract String getDeleteQuery();

	/**
	 * @return "SELECT COUNT(*) FROM table"
	 */
	public abstract String getCountQuery();

	protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws SQLException;

	protected abstract Object[] argumentsForUpdate(T object);

	protected abstract Class<T> getClassForMapping();

	protected abstract void setIdAfterInsert(KeyHolder keyHolder, T object);

	@Override
	public T get(Integer id) {
		String sql = getSelectQuery();
		try {
			return jdbcTemplate.queryForObject(sql + " where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<T>(getClassForMapping()));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public T insert(T object) {
		String sql = getInsertQuery();
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
				prepareStatementForInsert(ps, object);

				return ps;
			}
		}, keyHolder);
		setIdAfterInsert(keyHolder, object);

		return object;
	}

	@Override
	public void update(T object) {
		String sql = getUpdateQuery();
		jdbcTemplate.update(sql, argumentsForUpdate(object));
	}

	@Override
	public void delete(Integer id) {
		String sql = getDeleteQuery();
		jdbcTemplate.update(sql + id);
	}

	@Override
	public Integer getCount() {
		String sql = getCountQuery();
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}

	@Override
	public List<T> getAll() {
		String sql = getSelectQuery();
		List<T> rs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(getClassForMapping()));
		return rs;
	}

}
