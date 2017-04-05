package flashCardServerDAO;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import flashCardServerModel.Card;

@RegisterMapper(CardMapper.class)
public interface CardDAO {

    @SqlQuery("select * from " + CardPeer.TABLE_NAME)
    List<Card> getAll();

    @SqlQuery("select * from " + CardPeer.TABLE_NAME + " where " + CardPeer.ID + " = :id")
    Card findCardById(@Bind("id") int id);
    
    @SqlQuery("select * from " + CardPeer.TABLE_NAME + " where " + CardPeer.STUDYSET_ID + " = :id")
    List<Card> findCardByStudySetId(@Bind("id") int id);
    
    @SqlQuery("SELECT * from " + CardPeer.TABLE_NAME + " ORDER BY " + CardPeer.CARD_ID + " DESC LIMIT 1")
    Card getLastCardId();
    
    @SqlUpdate("delete from  " + CardPeer.TABLE_NAME + " where  " + CardPeer.ID + "  = :id")
    int deleteCardById(@Bind("id") int id);

    @SqlUpdate("insert into " + CardPeer.TABLE_NAME + " ( " + CardPeer.CARD_ID + " ,  " + CardPeer.STUDYSET_ID + " ,  " + CardPeer.LANGUAGE + " ,  " + CardPeer.WORD + " ) values (:cardId, :studySetId, :language, :word)")
    int insertCard(@BindBean Card card);
    
    
}