
package Listeners;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		final List<String> cmdPatterns = Arrays.asList("^/[Mm][Ss][Gg]", "^/[Tt][Ee][Ll]{2}", "^/[Rr]", "^/[Ww][Hh][Ii][Ss][Pp][Ee][Rr]");
		final Player player = event.getPlayer();
		final String msg = event.getMessage();
		Pattern regex;
		Matcher match;
		
		
		if(player == null) return;
		
		for(String pattern : cmdPatterns) {
			
			regex = Pattern.compile(pattern);
			match = regex.matcher(msg);
			
			if(match.find()) {
				
				if(plugin.isAFK(player)) {
					
					player.sendMessage(ChatColor.RED + "You cannot send private messages while AFK");
					
					event.setCancelled(true);
				}
				
				return;
			}
		}
	}
}
