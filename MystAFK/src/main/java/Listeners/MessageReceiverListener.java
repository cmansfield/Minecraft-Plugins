
package Listeners;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
		final String msg = event.getMessage();
		final String cmd = "/msg";
		
		if(msg.length() < cmd.length()) return;
		
		if(msg.substring(0, cmd.length()).contains(cmd)) {
			
			plugin.getLogger().info(event.getMessage());
		}
	}
}
