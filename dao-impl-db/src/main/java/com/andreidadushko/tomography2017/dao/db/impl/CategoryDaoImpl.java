package com.andreidadushko.tomography2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.ICategoryDao;
import com.andreidadushko.tomography2017.datamodel.Category;

@Repository
public class CategoryDaoImpl extends AbstractDaoImpl<Category> implements ICategoryDao {

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM category";
	}

	@Override
	public String getInsertQuery() {
		return "INSERT INTO category (name, parent_id) VALUES (?,?)";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE category SET name=?, parent_id=? WHERE id= ?";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM category WHERE id=";
	}

	@Override
	public String getCountQuery() {
		return "SELECT COUNT(*) FROM category";
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, Category category) throws SQLException {
		ps.setString(1, category.getName());
		if (category.getParentId() != null)
			ps.setInt(2, category.getParentId());
		else
			ps.setNull(2, 0);
	}

	@Override
	protected Object[] argumentsForUpdate(Category category) {
		return new Object[] { category.getName(), category.getParentId(), category.getId() };
	}

	@Override
	protected Class<Category> getClassForMapping() {
		return Category.class;
	}

	@Override
	protected void setIdAfterInsert(KeyHolder keyHolder, Category category) {
		category.setId(keyHolder.getKey().intValue());
	}

}
