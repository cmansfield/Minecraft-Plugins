
package Listeners;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import io.github.cmansfield.MystAFK.MystAFK;


public class MessageReceiverListener implements Listener{

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
		
//		for(Player player : recipients) {
//			
//			// Remove any player who is AFK from receiving
//			// that chat message
//			if(plugin.isAFK(player)) event.getRecipients().remove(player);
//		}
	}
}
