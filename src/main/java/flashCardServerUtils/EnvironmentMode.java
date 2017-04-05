package flashCardServerUtils;

public enum EnvironmentMode {

	QA("qa.yml"),
	DEV("dev.yml"),
	PROD("prod.yml"),
	STG("stg.yml"),
	LOCAL("local.yml"),
	DEFAULT("config.yml");
	
	private final String configFile;
	
	private EnvironmentMode(String fileName) {
		this.configFile = fileName;
	}
	
	public String getConfigFileName() {
		return configFile;
	}
	
}
