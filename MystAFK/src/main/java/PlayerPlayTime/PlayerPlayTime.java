
package PlayerPlayTime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import Config.ConfigMessage;
import Config.IConfigMessage;
import Config.PlayerNameDecorator;
import Config.TimeSecondsDecorator;
import io.github.cmansfield.MystAFK.MystAFK;


public class PlayerPlayTime implements IPlayerPlayTime {

	private int timeOut = 3600;
	private int sendPrompt = 3300;
	private int kickTimeOut = 5400;
	
	private final Map<Player, Integer> playerTimers = new HashMap<Player, Integer>();
	private final MystAFK plugin;
	
	
	public PlayerPlayTime(MystAFK main) {
		
		this.plugin = main;
		
		sendPrompt = this.plugin.getConfig().getInt("PromptTime", sendPrompt);
		timeOut = this.plugin.getConfig().getInt("TimeOutTime", timeOut);
		kickTimeOut = this.plugin.getConfig().getInt("KickTimeOut", kickTimeOut);
		
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
		
		if(playerTimers.get(player) == null) return;
		
		playerTimers.replace(player, 0);
	}

	@Override
	public void addSecondToAllPlayersTimer() {

    	final IConfigMessage configMsgKick = new ConfigMessage(plugin, "messages.ActionBarKickmsg");
    	final IConfigMessage configMsgAFK = new ConfigMessage(plugin, "messages.ActionBarAFKmsg");
    	final IConfigMessage configMsgYouAreAFK = new ConfigMessage(plugin, "messages.ActionBarYouAreAFK");
		final int FIVE_MIN_IN_SECONDS = 300;
    	
		
		playerTimers.replaceAll((key, value) -> ++value);

		Iterator<Entry<Player, Integer>> iter = playerTimers.entrySet().iterator();
		Entry<Player, Integer> entry;

		while (iter.hasNext()) {

		    entry = iter.next();

		    if(entry.getValue() >= kickTimeOut) {
		    	
		    	Player player = entry.getKey();
		    	
		    	iter.remove();
		    	
		    	if(plugin.isAFK(player)) plugin.toggleAFK(player, false);

		    	player.kickPlayer("Kicked for being idle too long");
		    	continue;
		    }
		    
		    // Actionbar only lasts for 1 second, we have
		    // to send the actionbar packet every second
		    if(entry.getValue() > (kickTimeOut - FIVE_MIN_IN_SECONDS) && entry.getValue() > timeOut) {
		   
		    	plugin.sendPlayerActionbar(entry.getKey(), 
	    			new TimeSecondsDecorator(
	    				new PlayerNameDecorator(
	    					configMsgKick, 
	    					entry.getKey().getName()
		    			), 
	    				kickTimeOut - entry.getValue()
	    			)
	    		);
		    }
		    else if(entry.getValue() >= timeOut) {
		    	
		    	plugin.sendPlayerActionbar(entry.getKey(), 
	    			new TimeSecondsDecorator(
	    				new PlayerNameDecorator(
	    						configMsgYouAreAFK, 
	    					entry.getKey().getName()
		    			), 
	    				entry.getValue() - timeOut
	    			)
	    		);
		    }
		    
		    if(plugin.isAFK(entry.getKey())) continue;
		    
		    if(entry.getValue() == sendPrompt) {
		    	
		    	plugin.sendPlayerPrompt(entry.getKey());
		    }
		    else if(entry.getValue() == timeOut){

		    	//iter.remove();
		    	plugin.toggleAFK(entry.getKey());
		    }

		    // Actionbar only lasts for 1 second, we have
		    // to send the actionbar packet every second
		    if(entry.getValue() >= sendPrompt) {
		    	
		    	plugin.sendPlayerActionbar(entry.getKey(), configMsgAFK);
		    
		    	plugin.sendPlayerActionbar(entry.getKey(), 
	    			new TimeSecondsDecorator(
	    				new PlayerNameDecorator(
	    					configMsgAFK, 
	    					entry.getKey().getName()
		    			), 
	    				timeOut - entry.getValue()
	    			)
		    	);
		    }
		}
	}
}
