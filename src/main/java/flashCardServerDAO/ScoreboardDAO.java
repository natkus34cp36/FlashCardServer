package flashCardServerDAO;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import flashCardServerModel.Scoreboard;

@RegisterMapper(ScoreboardMapper.class)
public interface ScoreboardDAO {

	@SqlQuery("select * from " + ScoreboardPeer.TABLE_NAME + " where " + ScoreboardPeer.STUDYSET + " = :studyset_id and " + ScoreboardPeer.LANGUAGE1 + " = :lang1 and " + ScoreboardPeer.LANGUAGE2 + " = :lang2 order by " + ScoreboardPeer.SCORE + " desc limit 20" )
    List<Scoreboard> findScoreboardByStudySetId(@Bind("studyset_id") int studyset_id, @Bind("lang1") String lang1, @Bind("lang2") String lang2);
	
    @SqlQuery("select * from " + ScoreboardPeer.TABLE_NAME + " where " + ScoreboardPeer.ANDROID_ID + " = :id and " + ScoreboardPeer.STUDYSET + " = :studyset_id and " + ScoreboardPeer.LANGUAGE1 + " = :lang1 and " + ScoreboardPeer.LANGUAGE2 + " = :lang2 order by " + ScoreboardPeer.SCORE + " desc limit 100" )
    List<Scoreboard> findScoreboardById(@Bind("id") String android_id, @Bind("studyset_id") int studyset_id, @Bind("lang1") String lang1, @Bind("lang2") String lang2);
    
    @SqlUpdate("delete from  " + ScoreboardPeer.TABLE_NAME + " where  " + ScoreboardPeer.ID + "  = :id")
    int deleteScoreboardById(@Bind("id") int id);

    @SqlUpdate("insert into " + ScoreboardPeer.TABLE_NAME + " ( " + ScoreboardPeer.NAME + " ,  " + ScoreboardPeer.ANDROID_ID + " ,  " + ScoreboardPeer.STUDYSET + " ,  " + ScoreboardPeer.MODE + " ,  " + ScoreboardPeer.OPTION + " ,  " + ScoreboardPeer.LANGUAGE1 + " ,  " + ScoreboardPeer.LANGUAGE2 + " ,  " + ScoreboardPeer.SCORE + " ) values (:name, :androidId, :studySetId, :mode, :option, :language1, :language2, :score)")
    int insertScoreboard(@BindBean Scoreboard scoreboard);
}
