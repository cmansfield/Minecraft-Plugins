package PlayerTags;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerTags {

	private final static Map<Color, Character> COLOR_ID_MAP = new HashMap<Color, Character>();
	static {
		
		COLOR_ID_MAP.put(Color.NAVY, '1');
		COLOR_ID_MAP.put(Color.GREEN, '2'); 
		COLOR_ID_MAP.put(Color.AQUA, '3');
		COLOR_ID_MAP.put(Color.RED, '4');
		COLOR_ID_MAP.put(Color.PURPLE, '5'); 
		COLOR_ID_MAP.put(Color.ORANGE, '6');
		COLOR_ID_MAP.put(Color.GRAY, '7');
		COLOR_ID_MAP.put(Color.BLUE, '9');
		COLOR_ID_MAP.put(Color.BLACK, '0');
		COLOR_ID_MAP.put(Color.WHITE, 'f');
	}
	
	
	public static void addTag(Player player, String tag) {
		
		if(player == null) return;
		
		char modifier = COLOR_ID_MAP.get(Color.PURPLE);
		String origCustName = ((player.getCustomName() == null) ? player.getName() : player.getCustomName());

		// Is there a chat modifier already on the
		// player's custom name?
		if(origCustName.charAt(0) == '§') {
			
			modifier = origCustName.charAt(1);
		}
		
		// If no tag was passed then return
		if(tag.length() == 0) return;
		if(origCustName.length() <= 1) return;
		
		// Generate a string with the new tag
		String custName = "§" + modifier + tag + "§" + COLOR_ID_MAP.get(Color.WHITE) + origCustName;

		// Add the new custom name name to the player
		player.setPlayerListName(custName);
		player.setCustomName(custName);
		player.setCustomNameVisible(true);
		player.setDisplayName(custName);
	}

	public static boolean removeTag(Player player, String tag) {
		
		if(player == null) return false;
		if(player.getCustomName() == null) return false;
		
		String custName = player.getCustomName();
		String tagToRemove = "§0" + tag + "§0";
		
		if(custName.contains(tag)) {
			
			custName = custName.substring((tagToRemove).length());	
		}
		
		player.setPlayerListName(custName);
		player.setCustomName(custName);
		player.setDisplayName(custName);
		
		return true;
	}
}
