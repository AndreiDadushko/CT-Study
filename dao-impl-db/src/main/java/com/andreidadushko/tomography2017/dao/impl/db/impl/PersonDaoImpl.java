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

import com.andreidadushko.tomography2017.dao.impl.db.IPersonDao;
import com.andreidadushko.tomography2017.datamodel.Person;

@Repository
public class PersonDaoImpl implements IPersonDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Person get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from person where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Person>(Person.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Person insert(Person person) {
		final String INSERT_SQL = "INSERT INTO person (first_name, middle_name, last_name, birth_date, phone_number, adress, login, password) VALUES (?,?,?,?,?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, person.getFirstName());
				ps.setString(2, person.getMiddleName());
				ps.setString(3, person.getLastName());
				ps.setDate(4, new java.sql.Date(person.getBirthDate().getTime()));
				ps.setString(5, person.getPhoneNumber());
				ps.setString(6, person.getAdress());
				ps.setString(7, person.getLogin());
				ps.setString(8, person.getPassword());
				return ps;
			}
		}, keyHolder);

		person.setId(keyHolder.getKey().intValue());

		return person;
	}

	@Override
	public void update(Person person) {
		
		final String INSERT_SQL= " UPDATE  person SET first_name = ?, middle_name = ?, last_name = ?, birth_date = ?, phone_number = ?, adress = ?, login = ?, password = ? WHERE `id` = ?";
		
		jdbcTemplate.update(INSERT_SQL,new Object[]{person.getFirstName(),person.getMiddleName(),person.getLastName(),person.getBirthDate(),person.getPhoneNumber(),person.getAdress(),person.getLogin(),person.getPassword(),person.getId()});
	
	}

	@Override
	public void delete(Integer id) {

		jdbcTemplate.update("delete from person where id=" + id);

	}

	@Override
	public List<Person> getAll() {
		List<Person> rs = jdbcTemplate.query("select * from person group by id", new BeanPropertyRowMapper<Person>(Person.class));
		return rs;
	}

}
