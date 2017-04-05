package flashCardServerModel;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetStudySetResponse {
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("created")
	private Date created;
	
	@JsonProperty("updated")
	private Date updated;
	
	@JsonProperty("supportedLanguages")
	private String supportedLanguages;
	
	@JsonProperty("cards")
	private List<GetCardResponse> cardResponse;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getSupportedLanguages() {
		return supportedLanguages;
	}

	public void setSupportedLanguages(String supportedLanguages) {
		this.supportedLanguages = supportedLanguages;
	}

	public List<GetCardResponse> getCardResponse() {
		return cardResponse;
	}

	public void setCardResponse(List<GetCardResponse> cardResponse) {
		this.cardResponse = cardResponse;
	}
	
}
