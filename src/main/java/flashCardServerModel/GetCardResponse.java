package flashCardServerModel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetCardResponse {
	
	@JsonProperty("cardId")
	private int cardId;
	
	@JsonProperty("contents")
	private List<Content> content;

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public List<Content> getContent() {
		return content;
	}

	public void setContent(List<Content> content) {
		this.content = content;
	}
	
}
