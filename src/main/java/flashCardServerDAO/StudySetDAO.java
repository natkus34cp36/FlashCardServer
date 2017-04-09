package flashCardServerDAO;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import flashCardServerModel.StudySet;

@RegisterMapper(StudySetMapper.class)
public interface StudySetDAO {

    @SqlQuery("select * from " + StudySetPeer.TABLE_NAME)
    List<StudySet> getAll();

    @SqlQuery("select * from " + StudySetPeer.TABLE_NAME + " where " + StudySetPeer.ID + " = :id")
    StudySet findStudySetById(@Bind("id") int id);
    
    @SqlUpdate("delete from  " + StudySetPeer.TABLE_NAME + " where  " + StudySetPeer.ID + "  = :id")
    int deleteStudySetById(@Bind("id") int id);

    @SqlQuery("insert into " + StudySetPeer.TABLE_NAME + " ( " + StudySetPeer.NAME + " ,  " + StudySetPeer.DESCRIPTION + " ,  " + StudySetPeer.CREATED + " ,  " + StudySetPeer.UPDATED  + " ,  " + StudySetPeer.SUPPORTED_LANGUAGES + " ) values (:name, :description, :created, :updated, :supportedLanguages) returning *")
    @GetGeneratedKeys
    StudySet insertStudySet(@BindBean StudySet studySet);
}