

// TODO: Make sure permissions are working correctly
// TODO: Make sure added player tags don't mess up previous tags
// TODO: Make sure player state is restored when the plugin is disabled
// TODO: Use Regex to remove the player tag
// TODO: Fix custom tags being added twice to players
// TODO: Include blocking /msg to and from AFK players


package io.github.cmansfield.MystAFK;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Listeners.MessageReceiverListener;
import Listeners.MessageSenderListener;
import PlayerTags.PlayerTags;


public final class MystAFK extends JavaPlugin {

	private final List<Listener> myListeners = new ArrayList<Listener>();
	private final List<Player> afkPlayers = new ArrayList<Player>();
	public boolean isEnabled = false;
	
    @Override
    public void onEnable() {

        PluginManager pm = getServer().getPluginManager();
        
        // Add each of our plugin's listeners heres
        myListeners.add(new MessageSenderListener(this));
        myListeners.add(new MessageReceiverListener(this));
        
        // Register all event listeners with the 
        // Bukkit plugin manager
        for(Listener listener : myListeners) {
        	
        	pm.registerEvents(listener, this);
        }

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

		// Check to see if the sender is a player or not
    	if(!(sender instanceof Player)) {
    		
    		sender.sendMessage(ChatColor.RED + "Only a player can use the command " + commandLabel);

    		return true;
    	}

		Player plr = (Player)sender;
    	
    	if(commandLabel.equalsIgnoreCase("afk")) {

    		// Toggle whether AFK is enabled or not
    		if(isAFK(plr)) {

    			afkPlayers.remove(plr);
		
    			// Update the global chat message
    			msg = sender.getName() + " is no longer AFK";
    			
    			// Remove the AFK player tag
    			PlayerTags.removeTag(plr, "[AFK]");
    		}
    		else {

    			afkPlayers.add(plr);

    			// Update the global chat message
    			msg = sender.getName() + " is now AFK";
    			
    			// Add AFK tag to player
    			PlayerTags.addTag(plr, "[AFK]");
    		}

    		// Send the message to all online players
    		for(Player player : Bukkit.getOnlinePlayers()) {
    			
    			player.sendMessage(msg);
    		}
    	}
    	else if(commandLabel.equalsIgnoreCase("bypassAFK")) {
    		
    		// Check to make sure the player has the
    		// right permissions to use this command
    		if(!plr.hasPermission("mystafk.bypassAFK")) {
    			
    			plr.sendMessage(ChatColor.RED + "You do not have permission to use this command");
    		
    			return true;
    		}
    		
    		// Is the player using the command
    		// currently AFK?
    		if(isAFK(plr)) {
    			
    			plr.sendMessage(ChatColor.RED + "You can only use /bypassAFK when you are no longer AFK");
    			
    			return true;
    		}
    		
    		plr.sendMessage("You are now using bypassAFK");
    	}
    	
    	return true;
    }
    
    public boolean isAFK(Player player) {

		if(afkPlayers.contains(player)) return true;
		
		return false;
    }
}
