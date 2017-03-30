package Config;

import io.github.cmansfield.MystAFK.MystAFK;

public class ConfigMessage implements IConfigMessage {

	private boolean isFormatted = true;
	private final MystAFK plugin;
	private String configID = "";
	private String msg = "";
	
	
	public ConfigMessage(MystAFK main, String configID) {
		
		this.plugin = main;
		this.configID = configID;
		
		getMessageFromConfig();
	}
	
	
	public ConfigMessage(MystAFK main, String configID, boolean isFormatted) {
		
		this.plugin = main;
		this.configID = configID;
		this.isFormatted = isFormatted;
	
		getMessageFromConfig();
	}
	
	
	private void getMessageFromConfig() {
		
		msg = plugin.getConfig().getString(configID, "Could not get " + configID + " from the config.yml file");
	}
	
	
	@Override
	public String getMessage() { return msg; }

	
	@Override
	public boolean isFormatted() { return isFormatted; }
}
