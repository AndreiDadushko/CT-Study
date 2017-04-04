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

import com.andreidadushko.tomography2017.dao.impl.db.ICategoryDao;
import com.andreidadushko.tomography2017.datamodel.Category;

@Repository
public class CategoryDaoImpl implements ICategoryDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Category get(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from category where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Category>(Category.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Category insert(Category category) {
		final String INSERT_SQL = "INSERT INTO category (name, parent_id) VALUES (?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, category.getName());
				if(category.getParentId()!=null) ps.setInt(2, category.getParentId());
				else ps.setNull(2,0);
				return ps;
			}
		}, keyHolder);

		category.setId(keyHolder.getKey().intValue());

		return category;
	}

	@Override
	public void update(Category category) {
		
		final String INSERT_SQL = "UPDATE category SET name=?, parent_id=? WHERE id= ?";

		jdbcTemplate.update(INSERT_SQL, new Object[] {category.getName(),category.getParentId(),category.getId()});

	}

	@Override
	public void delete(Integer id) {
		
		jdbcTemplate.update("delete from category where id=" + id);
		
	}

	@Override
	public List<Category> getAll() {
		
		List<Category> rs = jdbcTemplate.query("select * from category order by id", new BeanPropertyRowMapper<Category>(Category.class));
		return rs;
		
	}

}
