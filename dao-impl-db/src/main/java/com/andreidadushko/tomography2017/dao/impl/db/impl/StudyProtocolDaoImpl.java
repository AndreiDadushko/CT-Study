package com.andreidadushko.tomography2017.dao.impl.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyProtocolDao;
import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

@Repository
public class StudyProtocolDaoImpl extends AbstractDaoImpl<StudyProtocol> implements IStudyProtocolDao {

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM study_protocol";
	}

	@Override
	public String getInsertQuery() {
		return "INSERT INTO study_protocol (id, protocol, creation_date) VALUES (?,?,NOW())";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE study_protocol SET protocol=?,creation_date=NOW() WHERE id=?";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM study_protocol WHERE id=";
	}

	@Override
	public String getCountQuery() {
		return "SELECT COUNT(*) FROM study_protocol";
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, StudyProtocol studyProtocol) throws SQLException {
		ps.setInt(1, studyProtocol.getId());
		ps.setString(2, studyProtocol.getProtocol());
	}

	@Override
	protected Object[] argumentsForUpdate(StudyProtocol studyProtocol) {
		return new Object[] { studyProtocol.getProtocol(), studyProtocol.getId() };
	}

	@Override
	protected Class<StudyProtocol> getClassForMapping() {
		return StudyProtocol.class;
	}

	@Override
	protected void setIdAfterInsert(KeyHolder keyHolder, StudyProtocol studyProtocol) {
	}

	@Override
	public void massDelete(Integer[] idArray) {
		String sql = getDeleteQuery();
		StringBuilder whereCause = null;
		if (idArray != null && idArray.length != 0) {
			whereCause = new StringBuilder();
			for (int i = 0; i < idArray.length; i++) {
				if (i != 0) {
					whereCause.append(" OR ");
					whereCause.append(" id = " + idArray[i]);
				} else
					whereCause.append(idArray[i]);
			}
		}
		jdbcTemplate.update(sql + whereCause); // попробовать передать Object[]
												// и null вместо него
	}

}
