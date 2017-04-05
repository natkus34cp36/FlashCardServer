package flashCardServerStartup;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlashCardServerConfig extends Configuration {

	@NotNull
	@JsonProperty("version")
	private String version;
		
	@NotNull
	@JsonProperty("bearerRealm")
	private String bearerRealm;
	
	@NotNull
	@JsonProperty("database")
	private DataSourceFactory database = new DataSourceFactory();
	
	public String getVersion() {
		return version;
	}
	
	public String bearerRealm() {
		return bearerRealm;
	}

	public DataSourceFactory getDataSourceFactory() {
		return database;
	}
}
