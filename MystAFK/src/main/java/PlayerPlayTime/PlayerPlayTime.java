
package PlayerPlayTime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.cmansfield.MystAFK.MystAFK;


public class PlayerPlayTime implements IPlayerPlayTime {

	private int timeOut = 3600;
	private int sendPrompt = 3300;
	
	private final Map<Player, Integer> playerTimers = new HashMap<Player, Integer>();
	private final MystAFK plugin;
	
	
	public PlayerPlayTime(MystAFK main) {
		
		this.plugin = main;
		
		sendPrompt = this.plugin.getConfig().getInt("PromptTime", sendPrompt);
		timeOut = this.plugin.getConfig().getInt("TimeOutTime", timeOut);
		
		if(sendPrompt < 0) { sendPrompt = 0; }
		if(timeOut < 0) { timeOut = 10; }
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			
			addPlayer(player);
		}
	}
	
	@Override
	public void addPlayer(Player player) {
		
		if(player.hasPermission("mystafk.bypassAFK")) { return; }
		
		// Is the player already in the map?
		if(playerTimers.get(player) != null) return;
		
		if(!player.isOnline()) return;
		
		playerTimers.put(player, 0);
	}

	@Override
	public void removePlayer(Player player) {

		// Is the player really in the map?
		if(playerTimers.get(player) == null) return;

		playerTimers.remove(player);
	}

	@Override
	public void resetPlayerTimer(Player player) {
		
		// We need to make sure there actually is a this
		// player in the hash map before we try and edit it
		addPlayer(player);
		
		playerTimers.replace(player, 0);
	}

	@Override
	public void addSecondToAllPlayersTimer() {

		playerTimers.replaceAll((key, value) -> ++value);

		Iterator<Entry<Player, Integer>> iter = playerTimers.entrySet().iterator();
		Entry<Player, Integer> entry;

		while (iter.hasNext()) {

		    entry = iter.next();

		    if(entry.getValue() == sendPrompt) {
		    	
		    	plugin.sendPlayerPrompt(entry.getKey());
		    }
		    else if(entry.getValue() >= timeOut){

		    	iter.remove();
		    	plugin.toggleAFK(entry.getKey());
		    }
		    
		    if(entry.getValue() >= sendPrompt) {
		    	
		    	plugin.sendPlayerActionbar(entry.getKey());
		    }
		}
	}
}
