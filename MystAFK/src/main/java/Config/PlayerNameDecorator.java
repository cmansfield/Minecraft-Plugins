
package Config;


public class PlayerNameDecorator implements IConfigMessage {

	private final String NAME_PLACEHOLDER = "\\p";
	private IConfigMessage message;
	private String pName = "";
	
	
	public PlayerNameDecorator(IConfigMessage message, String pName) {
		
		this.message = message;
		this.pName = pName;
	}
	
	@Override
	public String getMessage() {

		if(!message.isFormatted()) { return message.getMessage(); }
		
		return message.getMessage().replace(NAME_PLACEHOLDER, pName);
	}

	@Override
	public boolean isFormatted() {
		
		return message.isFormatted();
	}

}
