

// TODO: Make sure permissions are working correctly
// TODO: Make sure added player tags don't mess up previous tags
// TODO: Use Regex to remove the player tag


package io.github.cmansfield.MystAFK;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import AFKplayers.AFKplayer;
import AFKplayers.AFKplayers;
import AFKplayers.IAFKplayers;
import Listeners.MessageReceiverListener;
import Listeners.MessageSenderListener;
import Listeners.PlayerClickListener;
import Listeners.PlayerJoinListener;
import Listeners.PlayerLeaveListener;
import Listeners.PlayerMoveListener;
import PlayerPlayTime.IPlayerPlayTime;
import PlayerPlayTime.PlayerPlayTime;
import PlayerTags.PlayerTags;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;


public final class MystAFK extends JavaPlugin {

	private final List<Listener> myListeners = new ArrayList<Listener>();
	private final IAFKplayers afkPlayers = new AFKplayers();
	private final long KEY_MAX = 999999999999999L;
	private final long KEY_MIN = 100000000000000L;
	private IPlayerPlayTime playerTimer;
	private final int TICKS_PER_SEC = 20;
	private long noAFKkey;
	
	
    @Override
    public void onEnable() {

        PluginManager pm = getServer().getPluginManager();
        
        // Create a default config file if there isn't one
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        noAFKkey = (long)(Math.random() * KEY_MAX + KEY_MIN);
        playerTimer = new PlayerPlayTime(this);
        
        // Add each of our plugin's listeners heres
        myListeners.add(new MessageSenderListener(this));
        myListeners.add(new MessageReceiverListener(this));
        myListeners.add(new PlayerLeaveListener(this));
        myListeners.add(new PlayerClickListener(this));
        myListeners.add(new PlayerJoinListener(playerTimer));
        if(this.getConfig().getBoolean("MoveResetTimer", false)) { myListeners.add(new PlayerMoveListener(playerTimer)); }
        
        // Register all event listeners with the 
        // Bukkit plugin manager
        for(Listener listener : myListeners) {
        	
        	pm.registerEvents(listener, this);
        }

        // Register our plugin's commands
        getCommand("afk").setExecutor(this);
        getCommand("NoAFK").setExecutor(this);
       
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	
        	public void run() {
        		
        		playerTimer.addSecondToAllPlayersTimer();
        	}
        }, TICKS_PER_SEC, TICKS_PER_SEC);

        getLogger().info(this.getName() + " Plugin Enabled");
    }

    
    @Override
    public void onDisable() {
    
    	// Remove all players from an AFK state
    	// before shutting down the plugin
    	final IAFKplayers afkPlayersRemaining = afkPlayers.copy();
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
    	
    	if(commandLabel.equalsIgnoreCase("noafk")) {
    		
    		Player player = (Player)sender;
    		
    		if(args.length != 1 || !args[0].equalsIgnoreCase(String.valueOf(noAFKkey))) { 
    			
    			player.sendMessage(ChatColor.RED + "You are not permitted to use this command");
    			
    			return true;
    		}
    		
    		if(isAFK(player)) { return true; }
    		
    		playerTimer.resetPlayerTimer(player);
    		
    		player.sendMessage(ChatColor.RED + "Good to know you're not AFK");
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
    
    
    public void toggleAFK(Player player) {
    	
    	toggleAFK(player, true);
    }
    
    
    public void toggleAFK(Player player, boolean broadcast) {
    	
    	String msg;

    	
    	if(!player.isOnline()) {
    		
    		afkPlayers.remove(player);
    		playerTimer.removePlayer(player);
    		
    		return;
    	}
    	
		// Toggle whether AFK is enabled or not
		if(isAFK(player)) {

			afkPlayers.remove(player);
			playerTimer.addPlayer(player);
	
			// Update the global chat message
			msg = player.getName() + " is no longer AFK";

			// Remove the AFK player tag
			PlayerTags.removeTag(player, "[AFK]");
		}
		else {

			afkPlayers.add(player);
			playerTimer.removePlayer(player);

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
    
    
    public void sendPlayerActionbar(Player player) {
    	
    	final byte ACTIONBAR_INDENTIFIER = 2;
    	
    	
	    IChatBaseComponent barmsg = ChatSerializer.a("{\"text\":\"WARNING! You are about to be set to AFK!\"}");
	    
	    PacketPlayOutChat bar = new PacketPlayOutChat(barmsg, ACTIONBAR_INDENTIFIER);
	    
	    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(bar);
    }
    
    
    public void sendPlayerPrompt(Player player) {

    	player.sendMessage(ChatColor.RED + "You've been playing for awhile, are you AFK?");
    	
    	player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1, 1);

        IChatBaseComponent comp = 
        		ChatSerializer.a(
        					"{\"text\":\"I am not AFK\",\"color\":\"green\",\"underlined\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/noafk "
        					+ String.valueOf(noAFKkey) 
        					+ "\"}}"
        				);
        PacketPlayOutChat packet = new PacketPlayOutChat(comp);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}
