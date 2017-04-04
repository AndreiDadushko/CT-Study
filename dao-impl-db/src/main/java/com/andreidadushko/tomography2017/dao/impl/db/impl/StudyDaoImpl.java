package com.andreidadushko.tomography2017.dao.impl.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyDao;
import com.andreidadushko.tomography2017.datamodel.Study;

@Repository
public class StudyDaoImpl extends AbstractDaoImpl<Study> implements IStudyDao {

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM study";
	}

	@Override
	public String getInsertQuery() {
		return "INSERT INTO study (appointment_date, permitted, person_id, staff_id) VALUES (?,?,?,?)";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE  study SET  appointment_date = ?, permitted= ?,person_id= ?,staff_id =? WHERE  id = ?";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM study WHERE id=";
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement ps, Study study) throws SQLException {
		ps.setTimestamp(1, study.getAppointmentDate());
		ps.setBoolean(2, study.getPermitted());
		ps.setInt(3, study.getPersonId());
		ps.setInt(4, study.getStaffId());
	}

	@Override
	protected Object[] argumentsForUpdate(Study study) {
		return new Object[] { study.getAppointmentDate(), study.getPermitted(), study.getPersonId(), study.getStaffId(),
				study.getId() };
	}

	@Override
	protected Class<Study> getClassForMapping() {
		return Study.class;
	}

	@Override
	protected void setIdAfterInsert(KeyHolder keyHolder, Study study) {
		study.setId(keyHolder.getKey().intValue());
	}

}
