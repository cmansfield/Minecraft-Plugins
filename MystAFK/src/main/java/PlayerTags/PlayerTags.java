package PlayerTags;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Color;
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
		
		char modifier = COLOR_ID_MAP.get(Color.PURPLE);
		String origCustName = player.getCustomName();
		
		if(player == null) return;
		if(origCustName.length() <= 1) return;

		if(origCustName.charAt(0) == '§') {
			
			modifier = origCustName.charAt(1);
		}
		
		String custName = "§" + modifier + tag + "§" + COLOR_ID_MAP.get(Color.WHITE) + player.getName();

		player.setPlayerListName(custName);
		player.setCustomName(custName);
		player.setCustomNameVisible(true);
		player.setDisplayName(custName);
	}

	public static boolean removeTag(Player player, String tag) {
		
		String custName = player.getCustomName();
		custName = custName.substring(("§0" + tag + "§0").length());
		
		player.setPlayerListName(custName);
		player.setCustomName(custName);
		player.setDisplayName(custName);
		
		return true;
	}
}
