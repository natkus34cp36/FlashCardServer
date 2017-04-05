package flashCardServerModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {

	@JsonProperty("language")
	private String language;
	
	@JsonProperty("word")
	private String word;

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
