

// TODO: Make sure permissions are working correctly
// TODO: Make sure added player tags don't mess up previous tags
// TODO: Use Regex to remove the player tag
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

        getLogger().info(this.getName() + " Plugin Enabled");
    }

    
    @Override
    public void onDisable() {
    
    	// Remove all players from an AFK state
    	// before shutting down the plugin
    	for(Player player : afkPlayers) { toggleAFK(player, false); }
    	
    	getLogger().info(this.getName() + " Plugin Disabled");
    }
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		// Check to see if the sender is a player or not
    	if(!(sender instanceof Player)) {
    		
    		sender.sendMessage(ChatColor.RED + "Only a player can use the command " + commandLabel);

    		return true;
    	}
    	
    	if(commandLabel.equalsIgnoreCase("afk")) {
    		
    		toggleAFK((Player)sender);
    	}
    	
    	return true;
    }
    
    
    public boolean isAFK(Player player) {

		if(afkPlayers.contains(player)) return true;
		
		return false;
    }
    
    
    private void toggleAFK(Player player) {
    	
    	toggleAFK(player, true);
    }
    
    
    private void toggleAFK(Player player, boolean broadcast) {
    	
    	String msg;

		// Toggle whether AFK is enabled or not
		if(isAFK(player)) {

			afkPlayers.remove(player);
	
			// Update the global chat message
			msg = player.getName() + " is no longer AFK";
			
			// Remove the AFK player tag
			PlayerTags.removeTag(player, "[AFK]");
		}
		else {

			afkPlayers.add(player);

			// Update the global chat message
			msg = player.getName() + " is now AFK";
			
			// Add AFK tag to player
			PlayerTags.addTag(player, "[AFK]");
		}

		if(broadcast) {
			
			// Send the message to all online players
			for(Player plr : Bukkit.getOnlinePlayers()) {
				
				plr.sendMessage(msg);
			}
		}

    }
}
