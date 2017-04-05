package flashCardServerDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import flashCardServerModel.StudySet;

public class StudySetMapper implements ResultSetMapper<StudySet>{

	public StudySet map(int index, ResultSet resultSet, StatementContext statementContext)
			throws SQLException {

		StudySet studySet = new StudySet();
		studySet.setId(resultSet.getInt(StudySetPeer.ID));
		studySet.setName(resultSet.getString(StudySetPeer.NAME));
		studySet.setDescription(resultSet.getString(StudySetPeer.DESCRIPTION));
		studySet.setSupportedLanguages(resultSet.getString(StudySetPeer.SUPPORTED_LANGUAGES));
		studySet.setCreated(resultSet.getDate(StudySetPeer.CREATED));	
		studySet.setUpdated(resultSet.getDate(StudySetPeer.UPDATED));	

		return studySet;
	}

}