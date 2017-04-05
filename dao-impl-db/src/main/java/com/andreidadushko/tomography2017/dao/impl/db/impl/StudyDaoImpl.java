package com.andreidadushko.tomography2017.dao.impl.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.impl.db.IStudyDao;
import com.andreidadushko.tomography2017.dao.impl.db.custom.models.StudyForList;
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
	
	public String getQueryStudyForList() {
		return "SELECT sy.id, sy.appointment_date, sy.permitted, p.last_name patient_last_name, p.first_name patient_first_name, p.middle_name patient_middle_name, pdoc.last_name doctor_last_name, pdoc.first_name doctor_first_name, pdoc.middle_name doctor_middle_name FROM study sy LEFT JOIN person p ON sy.person_id = p.id LEFT JOIN staff sa ON sy.staff_id = sa.id LEFT JOIN person pdoc ON sa.person_id = pdoc.id";
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

	@Override
	public List<StudyForList> getAllStudyForList() {
		
		String sql = getQueryStudyForList();
		List<StudyForList> rs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<StudyForList>(StudyForList.class));
		return rs;
	}
	
	@Override
	public List<StudyForList> getStudyForListByPersonId(Integer personId) {
		
		String sql = getQueryStudyForList();
		List<StudyForList> rs = jdbcTemplate.query(sql+ " WHERE sy.person_id = "+personId, new BeanPropertyRowMapper<StudyForList>(StudyForList.class));
		return rs;
	}

}
