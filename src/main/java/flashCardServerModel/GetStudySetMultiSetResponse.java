package flashCardServerModel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetStudySetMultiSetResponse {

	public GetStudySetMultiSetResponse(String name, List<GetCardResponse> cardReponse) {
		this.name = name;
		this.cardResponse = cardReponse;
	}
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("cards")
	private List<GetCardResponse> cardResponse;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GetCardResponse> getCardResponse() {
		return cardResponse;
	}

	public void setCardResponse(List<GetCardResponse> cardResponse) {
		this.cardResponse = cardResponse;
	}
	
	
}
