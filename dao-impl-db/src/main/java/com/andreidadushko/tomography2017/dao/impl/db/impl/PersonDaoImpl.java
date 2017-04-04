package com.andreidadushko.tomography2017.dao.impl.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.impl.db.IPersonDao;
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
			return jdbcTemplate.queryForObject(getSelectQuery() + " where login = ?", new Object[] { login },
					new BeanPropertyRowMapper<Person>(getClassForMapping()));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
