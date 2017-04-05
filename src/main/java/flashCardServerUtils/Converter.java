package flashCardServerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import flashCardServerModel.*;

public class Converter {
	
	public static List<GetCardResponse> getCardResponses(List<Card> cards){
		List<GetCardResponse> cardResponse = new ArrayList<GetCardResponse>();
		
		HashMap<Integer, List<Content>> cardMap = new HashMap<Integer, List<Content>>();
		for(Card card : cards) {
			Content content = new Content();
			content.setLanguage(card.getLanguage());
			content.setWord(card.getWord());
			if(cardMap.containsKey(card.getCardId())){
				cardMap.get(card.getCardId()).add(content);
			} else {
				List<Content> contentList = new ArrayList<Content>();
				contentList.add(content);
				cardMap.put(card.getCardId(), contentList);
			}
		}
		
		for(Integer key : cardMap.keySet()){
			GetCardResponse getCardResponse = new GetCardResponse();
			getCardResponse.setCardId(key);
			getCardResponse.setContent(cardMap.get(key));
			cardResponse.add(getCardResponse);
		}
		return cardResponse;
	}

}
