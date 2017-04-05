package flashCardServerDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import flashCardServerModel.Card;

public class CardMapper implements ResultSetMapper<Card>{

	public Card map(int index, ResultSet resultSet, StatementContext statementContext)
			throws SQLException {

		Card card = new Card();
		card.setId(resultSet.getInt(CardPeer.ID));
		card.setStudySetId(resultSet.getInt(CardPeer.STUDYSET_ID));
		card.setCardId(resultSet.getInt(CardPeer.CARD_ID));
		card.setLanguage(resultSet.getString(CardPeer.LANGUAGE));
		card.setWord(resultSet.getString(CardPeer.WORD));

		return card;
	}

}