

// TODO: Make sure permissions are working correctly
// TODO: Make sure added player tags don't mess up previous tags
// TODO: Use Regex to remove the player tag
// TODO: Save player gamemode to be restored after AFK


package io.github.cmansfield.MystAFK;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import AFKplayers.AFKplayer;
import AFKplayers.AFKplayers;
import AFKplayers.IAFKplayers;
import Listeners.MessageReceiverListener;
import Listeners.MessageSenderListener;
import PlayerTags.PlayerTags;


public final class MystAFK extends JavaPlugin {

	private final List<Listener> myListeners = new ArrayList<Listener>();
	private final IAFKplayers afkPlayers = new AFKplayers();
	
	
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
    	final IAFKplayers afkPlayersRemaining = new AFKplayers();
    	for(AFKplayer player : afkPlayers.getPlayers()) afkPlayersRemaining.add(player);
    	for(AFKplayer player : afkPlayersRemaining.getPlayers()) { toggleAFK(player.getPlayer(), false); }
    	
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
    	
    	// Delete this, this is a temp command
    	if(commandLabel.equalsIgnoreCase("clearcust")) {
    		
    		for(Player player : Bukkit.getOnlinePlayers()) {
    			
    			player.setCustomName(ChatColor.DARK_GREEN + "[Member]" + player.getName());
    		}
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

			//player.setInvulnerable(false);
			//player.setGravity(true);
			
			//player.setGameMode(GameMode.SURVIVAL);
			
			//player.setCanPickupItems(true);
			//player.setCollidable(true);
			
			// Remove the AFK player tag
			PlayerTags.removeTag(player, "[AFK]");
		}
		else {

			afkPlayers.add(player);

			// Update the global chat message
			msg = player.getName() + " is now AFK";

			//player.setInvulnerable(true);
			//player.setGravity(false);
			
			//World w = player.getWorld();
			//Entity e = w.spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
			//player.setGameMode(GameMode.SPECTATOR);
			//player.setSpectatorTarget(e);
			
			//player.setCanPickupItems(false);
			//player.setCollidable(false);
			
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
