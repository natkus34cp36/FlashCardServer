package flashCardServerDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import flashCardServerModel.Scoreboard;

public class ScoreboardMapper implements ResultSetMapper<Scoreboard>{

	public Scoreboard map(int index, ResultSet resultSet, StatementContext statementContext)
			throws SQLException {

		Scoreboard scoreboard = new Scoreboard();
		scoreboard.setId(resultSet.getInt(ScoreboardPeer.ID));
		scoreboard.setName(resultSet.getString(ScoreboardPeer.NAME));
		scoreboard.setAndroidId(resultSet.getString(ScoreboardPeer.ANDROID_ID));
		scoreboard.setStudySetId(resultSet.getInt(ScoreboardPeer.STUDYSET));
		scoreboard.setMode(resultSet.getInt(ScoreboardPeer.MODE));
		scoreboard.setOption(resultSet.getInt(ScoreboardPeer.OPTION));
		scoreboard.setLanguage1(resultSet.getString(ScoreboardPeer.LANGUAGE1));
		scoreboard.setLanguage2(resultSet.getString(ScoreboardPeer.LANGUAGE2));
		scoreboard.setScore(resultSet.getInt(ScoreboardPeer.SCORE));
		
		return scoreboard;
	}
}

