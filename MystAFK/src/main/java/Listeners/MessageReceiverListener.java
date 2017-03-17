
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
import io.github.cmansfield.MystAFK.MystAFK;


public class MessageReceiverListener implements Listener {

	private final MystAFK plugin;
	
	public MessageReceiverListener(MystAFK instance) {
		
		this.plugin = instance;
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void eventHandler(AsyncPlayerChatEvent event) {
		
		if(event.isCancelled()) return;
		
		// If it is a server sent message then let
		// the AFK players receive it
		if(event.getPlayer() == null) return;
		
		// Get all of the players who are meant to 
		// receive this chat message
		Set<Player> recipients = event.getRecipients();
		
		for(Player player : recipients) {
			
			// Remove any player who is AFK from receiving
			// that chat message
			if(plugin.isAFK(player) && recipients.contains(player)) event.getRecipients().remove(player);
		}
	}
	
	@EventHandler
	public void eventHandler(PlayerCommandPreprocessEvent event) {
		
		final List<String> cmdPatterns = Arrays.asList("^\\/[Mm][Ss][Gg]", "^\\/[Tt][Ee][Ll]{2}", "^\\/[Rr]", "^\\/[Ww][Hh][Ii][Ss][Pp][Ee][Rr]");
		final String receiverPattern = "^\\/[A-Za-z]{1,7}\\s((?:(?!\\s).)*)";
		final Player player = event.getPlayer();
		final String msg = event.getMessage();
		Player recPlayer = null;
		String recName;
		Pattern regex;
		Matcher match;
		
		
		if(player == null) return;
		
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
					
					player.sendMessage(ChatColor.RED + "You cannot send private messages to AFK players");
					
					event.setCancelled(true);
				}
				
				return;
			}
		}
	}
}
