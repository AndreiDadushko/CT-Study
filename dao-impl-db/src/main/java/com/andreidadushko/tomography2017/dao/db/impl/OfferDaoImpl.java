package com.andreidadushko.tomography2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IOfferDao;
import com.andreidadushko.tomography2017.datamodel.Offer;

@Repository
public class OfferDaoImpl extends AbstractDaoImpl<Offer> implements IOfferDao {

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM offer";
	}

	@Override
	public String getInsertQuery() {
		return "INSERT INTO offer (name, name_en, price, categor_id) VALUES (?,?,?,?)";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE offer SET name=?, name_en=?, price=?, categor_id=? WHERE id=?";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM offer WHERE id=";
	}

	@Override
	public String getCountQuery() {
		return "SELECT COUNT(*) FROM offer";
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, Offer offer) throws SQLException {
		ps.setString(1, offer.getName());
		ps.setString(2, offer.getNameEn());
		ps.setDouble(3, offer.getPrice());
		ps.setInt(4, offer.getCategorId());
	}

	@Override
	protected Object[] argumentsForUpdate(Offer offer) {
		return new Object[] { offer.getName(), offer.getNameEn(), offer.getPrice(), offer.getCategorId(),
				offer.getId() };
	}

	@Override
	protected Class<Offer> getClassForMapping() {
		return Offer.class;
	}

	@Override
	protected void setIdAfterInsert(KeyHolder keyHolder, Offer offer) {
		offer.setId(keyHolder.getKey().intValue());
	}

}
