
package Listeners;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import Config.ConfigMessage;
import Config.PlayerNameDecorator;
import io.github.cmansfield.MystAFK.MystAFK;


public class MessageReceiverListener implements Listener {

	private final MystAFK plugin;
	
	public MessageReceiverListener(MystAFK instance) {
		
		this.plugin = instance;
	}

	@EventHandler (priority = EventPriority.HIGHEST)
	public void eventHandler(PlayerCommandPreprocessEvent event) {
		
		final List<String> cmdPatterns = Arrays.asList("^\\/[Mm][Ss][Gg]", "^\\/[Tt][Ee][Ll]{2}", "^\\/[Rr]", "^\\/[Ww][Hh][Ii][Ss][Pp][Ee][Rr]");
		final String receiverPattern = "^\\/[A-Za-z]{1,7}\\s+((?:(?!\\s).)*)";
		final String AFK_PATTERN = "^\\/[Aa][Ff][Kk]$";
		final Player player = event.getPlayer();
		final String msg = event.getMessage();
		Player recPlayer = null;
		String recName;
		Pattern regex;
		Matcher match;
		
		
		if(player == null) return;
		
		// Block all commands from any AFK player
		regex = Pattern.compile(AFK_PATTERN);
		match = regex.matcher(msg);
		
		if(!match.find() && plugin.isAFK(player)) {
			
			player.sendMessage(
				ChatColor.RED 
				+ (new PlayerNameDecorator(
					new ConfigMessage(plugin, "messages.NoCommandMsg"), 
					player.getName()
				)).getMessage()
			);

			event.setCancelled(true);
			return;
		}
		
		// Grab the second parameter of the issued command
		regex = Pattern.compile(receiverPattern);
		match = regex.matcher(msg);

		// Check to see if there was a match
		if(match.find() && match.groupCount() > 0) {

			recName = match.group(1);
			
			// Search for the player who matches the second parameter
			for(Player plr : Bukkit.getOnlinePlayers()) {
				
				if(plr.getName().equalsIgnoreCase(recName)) {
					
					// We found an online player who matches
					// the name given
					recPlayer = plr;
					
					break;
				}
			}
		}
		else return;
		
		// If no player was found the we can just exit
		if(recPlayer == null) return;
		
		for(String pattern : cmdPatterns) {
			
			// Check to see if any of the player commands issued
			// are any of the private message commands
			regex = Pattern.compile(pattern);
			match = regex.matcher(msg);
			
			if(match.find()) {
				
				// Check to see if either the sender or the receiver
				// are AFK players, if they are then cancel the event
				if(plugin.isAFK(player)) {
					
					player.sendMessage(ChatColor.RED + "You cannot send private messages while AFK");
					
					event.setCancelled(true);
				}
				else if(plugin.isAFK(recPlayer)) {
					
					player.sendMessage(
						ChatColor.RED 
						+ (new PlayerNameDecorator(
							new ConfigMessage(plugin, "messages.PvtMsgErr"), 
							player.getName()
						)).getMessage()
					);

					event.setCancelled(true);
				}
				
				return;
			}
		}
	}
}
