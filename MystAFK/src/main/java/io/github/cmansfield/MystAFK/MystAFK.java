

package io.github.cmansfield.MystAFK;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MystAFK extends JavaPlugin {

	private final TestListener testListener = new TestListener(this);
	private final List<Player> afkPlayers = new ArrayList<Player>();
	public boolean isEnabled = false;
	
    @Override
    public void onEnable() {

        PluginManager pm = getServer().getPluginManager();
        
        // Register our event listener with the 
        // Bukkit plugin manager
        pm.registerEvents(testListener, this);
        
        // Register our plugin's commands
        getCommand("afk").setExecutor(this);
        getCommand("bypassAFK").setExecutor(this);
        
        getLogger().info(this.getName() + " Plugin Enabled");
    }

    @Override
    public void onDisable() {
    
    	getLogger().info(this.getName() + " Plugin Disabled");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	
    	String msg;
    	
    	// Creating a predicate (a delayed function) to 
    	// be used later on in multiple places
    	Predicate<String> isPlayer = (cmdLbl) -> { 
    		
    		// Check to see if the sender is a player or not
        	if(!(sender instanceof Player)) {
        		
        		sender.sendMessage("Only a player can use the command " + cmdLbl);
        		
        		// They weren't a player
        		return false;
        	}
    		
        	// They are a player
        	return true;
    	};

    	
    	if(commandLabel.equalsIgnoreCase("afk")) {
    		
    		// Check to see if the sender is actually a player,
    		// If not then return early from this function
    		if(!isPlayer.test(commandLabel)) return false;

    		Player plr = (Player)sender;
    		
    		// Toggle whether AFK is enabled or not
    		if(isAFK(plr)) {
    			
    			afkPlayers.remove(plr);
    			
    			// Update the global chat message
    			msg = sender.getName() + " is no longer AFK";
    			
    			String custName = plr.getCustomName();
    			custName = custName.substring("[AFK]".length());
    			
    			plr.setPlayerListName(custName);
    			plr.setCustomName(custName);
    			plr.setDisplayName(custName);
    		}
    		else {
    			
    			afkPlayers.add(plr);
    			
    			// Update the global chat message
    			msg = sender.getName() + " is now AFK";
    			
    			String custName = "[AFK]" + plr.getName();
    			
    			plr.setPlayerListName(custName);
    			plr.setCustomName(custName);
    			plr.setDisplayName(custName);
    		}

    		// Send the message to all online players
    		for(Player player : Bukkit.getOnlinePlayers()) {
    			
    			player.sendMessage(msg);
    		}
    	}
    	else if(commandLabel.equalsIgnoreCase("bypassAFK")) {
    		
    		// Check to see if the sender is actually a player,
    		// If not then return early from this function
    		if(!isPlayer.test(commandLabel)) return false;

    	}
    	
    	return true;
    }
    
    public boolean isAFK(Player player) {

		if(afkPlayers.contains(player)) return true;
		else return false;
    }
}
