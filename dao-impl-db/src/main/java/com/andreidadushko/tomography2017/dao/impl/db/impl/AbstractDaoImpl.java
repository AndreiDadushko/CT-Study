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

import com.andreidadushko.tomography2017.dao.impl.db.IAbstractDao;

@Repository
public abstract class AbstractDaoImpl<T> implements IAbstractDao<T> {

	@Inject
	private JdbcTemplate jdbcTemplate;

	/**
	 * Возвращает sql запрос для получения всех записей.
	 * <p/>
	 * SELECT * FROM [Table]
	 */
	public abstract String getSelectQuery();

	/**
	 * Возвращает sql запрос для вставки новой записи в базу данных.
	 * <p/>
	 * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
	 */
	public abstract String getInsertQuery();

	/**
	 * Возвращает sql запрос для обновления записи.
	 * <p/>
	 * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
	 */
	public abstract String getUpdateQuery();

	/**
	 * Возвращает sql запрос для удаления записи из базы данных.
	 * <p/>
	 * DELETE FROM [Table] WHERE id= ?;
	 */
	public abstract String getDeleteQuery();

	/**
	 * Устанавливает аргументы insert запроса в соответствии со значением полей
	 * объекта object.
	 */
	protected abstract void prepareStatementForInsert(PreparedStatement statement, T object);

	/**
	 * Устанавливает аргументы update запроса в соответствии со значением полей
	 * объекта object.
	 */
	protected abstract Object[] argumentsForUpdate(T object);

	protected abstract Class<T> getClassForMapping();

	protected abstract void setIdAfterInsert(KeyHolder keyHolder, T object);

	@Override
	public T get(Integer id) {
		String sql = getSelectQuery();
		try {
			return jdbcTemplate.queryForObject(sql + "where id = ? ", new Object[] { id },
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
	public List<T> getAll() {

		String sql = getSelectQuery();
		List<T> rs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(getClassForMapping()));
		return rs;

	}

}
