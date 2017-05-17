package com.andreidadushko.tomography2017.dao.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.IStudyDao;
import com.andreidadushko.tomography2017.dao.db.custom.models.StudyForList;
import com.andreidadushko.tomography2017.dao.db.filters.StudyFilter;
import com.andreidadushko.tomography2017.dao.db.utils.FilterUtil;
import com.andreidadushko.tomography2017.datamodel.Study;

@Repository
public class StudyDaoImpl extends AbstractDaoImpl<Study> implements IStudyDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudyDaoImpl.class);

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
		return "UPDATE  study SET  appointment_date = ?, permitted= ?,person_id= ?,staff_id =? WHERE  id = ? ";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM study WHERE id= ";
	}

	public String getQueryStudyForList() {
		return "SELECT sy.id, sy.appointment_date, sy.permitted, p.last_name patient_last_name, p.first_name patient_first_name, p.middle_name patient_middle_name, pdoc.last_name doctor_last_name, pdoc.first_name doctor_first_name, pdoc.middle_name doctor_middle_name "
				+ "FROM study sy " + "LEFT JOIN person p " + "ON sy.person_id = p.id " + "LEFT JOIN staff sa "
				+ "ON sy.staff_id = sa.id " + "LEFT JOIN person pdoc " + "ON sa.person_id = pdoc.id";
	}

	@Override
	public String getCountQuery() {
		return "SELECT COUNT(*) FROM study";
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
	@SuppressWarnings("unchecked")
	public List<StudyForList> getStudyForListByPersonId(Integer personId) {
		String sql = getQueryStudyForList() + " WHERE sy.person_id = " + personId;
		List<StudyForList> rs = (List<StudyForList>) queryCache.get(sql);		
		if(rs!=null){
			LOGGER.debug("Get data from CACHE. SQL: {}", sql);
		}
		if (rs == null) {
			rs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<StudyForList>(StudyForList.class));
			LOGGER.debug("Get data from DATA BASE. SQL: {}", sql);
			if (rs != null)
				queryCache.put(sql, rs);
		}
		return rs;
	}

	@Override
	public List<StudyForList> getWithPagination(int offset, int limit) {
		String sql = getQueryStudyForList();
		List<StudyForList> rs = jdbcTemplate.query(sql + " LIMIT ?,?", new Object[] { offset, limit },
				new BeanPropertyRowMapper<StudyForList>(StudyForList.class));
		return rs;
	}

	@Override
	public List<StudyForList> getWithPagination(int offset, int limit, StudyFilter studyFilter) {
		String sql = getQueryStudyForList();
		StringBuilder whereCause = new StringBuilder();
		List<Object> objects = new ArrayList<Object>();
		List<String> sqlParts = new ArrayList<String>();
		if (studyFilter != null) {
			if (studyFilter.getPermitted() != null) {
				sqlParts.add("permitted = ?");
				objects.add(studyFilter.getPermitted());
			}
			if (studyFilter.getDoctorFirstName() != null) {
				sqlParts.add("pdoc.first_name = ?");
				objects.add(studyFilter.getDoctorFirstName());
			}
			if (studyFilter.getDoctorLastName() != null) {
				sqlParts.add("pdoc.last_name = ?");
				objects.add(studyFilter.getDoctorLastName());
			}
			if (studyFilter.getDoctorMiddleName() != null) {
				sqlParts.add("pdoc.middle_name = ?");
				objects.add(studyFilter.getDoctorMiddleName());
			}
			if (studyFilter.getPatientFirstName() != null) {
				sqlParts.add("p.first_name = ?");
				objects.add(studyFilter.getPatientFirstName());
			}
			if (studyFilter.getPatientLastName() != null) {
				sqlParts.add("p.last_name = ?");
				objects.add(studyFilter.getPatientLastName());
			}
			if (studyFilter.getPatientMiddleName() != null) {
				sqlParts.add("p.middle_name = ?");
				objects.add(studyFilter.getPatientMiddleName());
			}
			if (studyFilter.getFrom() != null) {
				sqlParts.add("appointment_date >= ?");
				objects.add(studyFilter.getFrom());
			}
			if (studyFilter.getTo() != null) {
				sqlParts.add("appointment_date <= ?");
				objects.add(studyFilter.getTo());
			}
			FilterUtil.makeWhere(whereCause, sqlParts);
			if (studyFilter.getSort() != null && studyFilter.getSort().getColumn() != null) {
				FilterUtil.makeSort(whereCause, studyFilter.getSort());
			}
		}
		objects.add(offset);
		objects.add(limit);
		List<StudyForList> rs = jdbcTemplate.query(sql + whereCause + " LIMIT ?,?", objects.toArray(),
				new BeanPropertyRowMapper<StudyForList>(StudyForList.class));
		return rs;
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
		jdbcTemplate.update(sql + whereCause);
	}

	@Override
	public List<Study> getAll() {
		throw new UnsupportedOperationException();
	}

}
