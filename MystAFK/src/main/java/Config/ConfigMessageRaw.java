
package Config;

import io.github.cmansfield.MystAFK.MystAFK;


public class ConfigMessageRaw implements IConfigMessage {

	private boolean isFormatted = true;
	private final MystAFK plugin;
	private String msg = "";
	
	
	public ConfigMessageRaw(MystAFK main, String msg) {
		
		this.plugin = main;
		this.msg = msg;
	}
	
	
	public ConfigMessageRaw(MystAFK main, String msg, boolean isFormatted) {
		
		this.plugin = main;
		this.msg = msg;
		this.isFormatted = isFormatted;
	}
	
	
	@Override
	public String getMessage() { return msg; }

	
	@Override
	public boolean isFormatted() { return isFormatted; }
}
