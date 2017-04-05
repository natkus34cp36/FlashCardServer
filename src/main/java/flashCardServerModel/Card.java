package flashCardServerModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("studySetId")
	private int studySetId;
	
	@JsonProperty("cardId")
	private int cardId;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("word")
	private String word;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudySetId() {
		return studySetId;
	}

	public void setStudySetId(int studySetId) {
		this.studySetId = studySetId;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
}
