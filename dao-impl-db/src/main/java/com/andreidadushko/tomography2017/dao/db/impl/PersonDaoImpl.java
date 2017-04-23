package com.andreidadushko.tomography2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IPersonDao;
import com.andreidadushko.tomography2017.dao.db.filters.PersonFilter;
import com.andreidadushko.tomography2017.datamodel.Person;

@Repository
public class PersonDaoImpl extends AbstractDaoImpl<Person> implements IPersonDao {

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM person";
	}

	@Override
	public String getInsertQuery() {
		return "INSERT INTO person (first_name, middle_name, last_name, birth_date, phone_number, adress, login, password) VALUES (?,?,?,?,?,?,?,?)";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE  person SET first_name = ?, middle_name = ?, last_name = ?, birth_date = ?, phone_number = ?, adress = ?, login = ?, password = ? WHERE id = ?";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM person WHERE id=";
	}

	@Override
	public String getCountQuery() {
		return "SELECT COUNT(*) FROM person";
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, Person person) throws SQLException {
		ps.setString(1, person.getFirstName());
		ps.setString(2, person.getMiddleName());
		ps.setString(3, person.getLastName());
		ps.setTimestamp(4, person.getBirthDate());
		ps.setString(5, person.getPhoneNumber());
		ps.setString(6, person.getAdress());
		ps.setString(7, person.getLogin());
		ps.setString(8, person.getPassword());
	}

	@Override
	protected Object[] argumentsForUpdate(Person person) {
		return new Object[] { person.getFirstName(), person.getMiddleName(), person.getLastName(),
				person.getBirthDate(), person.getPhoneNumber(), person.getAdress(), person.getLogin(),
				person.getPassword(), person.getId() };
	}

	@Override
	protected Class<Person> getClassForMapping() {
		return Person.class;
	}

	@Override
	protected void setIdAfterInsert(KeyHolder keyHolder, Person person) {
		person.setId(keyHolder.getKey().intValue());
	}

	@Override
	public Person get(String login) {
		try {
			return jdbcTemplate.queryForObject(getSelectQuery() + " WHERE login = ?", new Object[] { login },
					new BeanPropertyRowMapper<Person>(getClassForMapping()));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Person> getWithPagination(int offset, int limit) {
		String sql = getSelectQuery();
		List<Person> rs = jdbcTemplate.query(sql + " LIMIT ?,?", new Object[] { offset, limit },
				new BeanPropertyRowMapper<Person>(Person.class));
		return rs;
	}

	@Override
	public List<Person> getWithPagination(int offset, int limit, PersonFilter personFilter) {
		String sql = getSelectQuery();
		StringBuilder whereCause = new StringBuilder();
		List<Object> objects = new ArrayList<Object>();
		List<String> sqlParts = new ArrayList<String>();
		if (personFilter != null) {
			if (personFilter.getFirstName() != null) {
				sqlParts.add("first_name = ?");
				objects.add(personFilter.getFirstName());
			}
			if (personFilter.getLastName() != null) {
				sqlParts.add("last_name = ?");
				objects.add(personFilter.getLastName());
			}
			if (personFilter.getMiddleName() != null) {
				sqlParts.add("middle_name = ?");
				objects.add(personFilter.getMiddleName());
			}
			if (personFilter.getAdress() != null) {
				sqlParts.add("adress = ?");
				objects.add(personFilter.getAdress());
			}
			if (personFilter.getFrom() != null) {
				sqlParts.add("birth_date >= ?");
				objects.add(personFilter.getFrom());
			}
			if (personFilter.getTo() != null) {
				sqlParts.add("birth_date <= ?");
				objects.add(personFilter.getTo());
			}
		}
		if (!sqlParts.isEmpty()) {
			whereCause.append(" WHERE ");
			for (int i = 0; i < sqlParts.size(); i++) {
				if (i != 0)
					whereCause.append(" AND ");
				whereCause.append(sqlParts.get(i));
			}
		}
		if (personFilter != null && personFilter.getSort() != null && personFilter.getSort().getColumn() != null) {
			whereCause.append(" ORDER BY " + personFilter.getSort().getColumn());
			if (personFilter.getSort().getOrder() != null) {
				whereCause.append(" " + personFilter.getSort().getOrder());
			}
		}
		objects.add(offset);
		objects.add(limit);
		List<Person> rs = jdbcTemplate.query(sql + whereCause + " LIMIT ?,?", objects.toArray(),
				new BeanPropertyRowMapper<Person>(Person.class));
		return rs;
	}

	@Override
	public List<Person> getAll() {
		throw new UnsupportedOperationException();
	}

}
