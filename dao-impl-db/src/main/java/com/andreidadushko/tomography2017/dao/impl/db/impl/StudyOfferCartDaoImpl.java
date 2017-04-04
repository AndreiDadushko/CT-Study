package com.andreidadushko.tomography2017.dao.impl.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyOfferCartDao;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;

@Repository
public class StudyOfferCartDaoImpl extends AbstractDaoImpl<StudyOfferCart> implements IStudyOfferCartDao {

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM study_offer_cart";
	}

	@Override
	public String getInsertQuery() {
		return "INSERT INTO study_offer_cart (paid, pay_date, study_id, offer_id) VALUES (?,?,?,?)";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE study_offer_cart SET paid=?, pay_date=?, study_id=?, offer_id=? WHERE id=?";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM study_offer_cart WHERE id=";
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, StudyOfferCart studyOfferCart) throws SQLException {
		ps.setBoolean(1, studyOfferCart.getPaid() != null ? studyOfferCart.getPaid() : false);
		ps.setTimestamp(2, studyOfferCart.getPayDate());
		ps.setInt(3, studyOfferCart.getStudyId());
		ps.setInt(4, studyOfferCart.getOfferId());
	}

	@Override
	protected Object[] argumentsForUpdate(StudyOfferCart studyOfferCart) {
		return new Object[] { studyOfferCart.getPaid(), studyOfferCart.getPayDate(), studyOfferCart.getStudyId(),
				studyOfferCart.getOfferId(), studyOfferCart.getId() };
	}

	@Override
	protected Class<StudyOfferCart> getClassForMapping() {
		return StudyOfferCart.class;
	}

	@Override
	protected void setIdAfterInsert(KeyHolder keyHolder, StudyOfferCart studyOfferCart) {
		studyOfferCart.setId(keyHolder.getKey().intValue());
	}

}
