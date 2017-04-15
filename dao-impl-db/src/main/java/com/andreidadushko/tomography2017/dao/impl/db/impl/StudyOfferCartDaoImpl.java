package com.andreidadushko.tomography2017.dao.impl.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyOfferCartDao;
import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyOfferCartForList;
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

	public String getQueryStudyOfferCartForList() {
		return "SELECT cart.id, c.name category, o.name offer, o.price, cart.paid, cart.pay_date FROM study_offer_cart cart LEFT JOIN offer o ON cart.offer_id = o.id LEFT JOIN category c ON o.categor_id = c.id LEFT JOIN study s ON cart.study_id = s.id WHERE s.id = ?";
	}

	@Override
	public String getCountQuery() {
		return null;
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

	@Override
	public List<StudyOfferCartForList> getStudyOfferCartByStudyId(Integer studyId) {
		String sql = getQueryStudyOfferCartForList();
		List<StudyOfferCartForList> rs = jdbcTemplate.query(sql, new Object[] { studyId },
				new BeanPropertyRowMapper<StudyOfferCartForList>(StudyOfferCartForList.class));
		return rs;
	}

	@Override
	public void massDelete(Integer[] studyIdArray) {
		String sql = "DELETE FROM study_offer_cart WHERE study_id = ";
		StringBuilder whereCause = null;
		if (studyIdArray != null && studyIdArray.length != 0) {
			whereCause = new StringBuilder();
			for (int i = 0; i < studyIdArray.length; i++) {
				if (i != 0) {
					whereCause.append(" OR ");
					whereCause.append(" study_id = " + studyIdArray[i]);
				} else
					whereCause.append(studyIdArray[i]);
			}
		}
		jdbcTemplate.update(sql + whereCause);
	}

	@Override
	public Integer getCount() {
		throw new UnsupportedOperationException();
	}

}
