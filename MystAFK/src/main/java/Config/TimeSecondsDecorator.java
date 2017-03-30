
package Config;


public class TimeSecondsDecorator implements IConfigMessage {

	private final String MIN_PLACEHOLDER = "\\s";
	private IConfigMessage message;
	private int minutes = 0;
	
	
	public TimeSecondsDecorator(IConfigMessage message, int minutes) {
		
		this.message = message;
		this.minutes = minutes;
	}
	
	@Override
	public String getMessage() {

		if(!message.isFormatted()) { return message.getMessage(); }
		
		return message.getMessage().replace(MIN_PLACEHOLDER, String.valueOf(minutes));
	}

	@Override
	public boolean isFormatted() {
		
		return message.isFormatted();
	}

}
