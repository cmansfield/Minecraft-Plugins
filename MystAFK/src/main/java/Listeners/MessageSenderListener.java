
package Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import io.github.cmansfield.MystAFK.MystAFK;


public class MessageSenderListener implements Listener{

	private final MystAFK plugin;
	
	public MessageSenderListener(MystAFK instance) {
		
		this.plugin = instance;
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void eventHandler(AsyncPlayerChatEvent event) {
		
		if(event.getPlayer() == null) return;
		
		if(plugin.isAFK(event.getPlayer())) {
			
			// If the sending player is AFK then prevent 
			// them from sending the message
			event.setCancelled(true);
		}
	}
}
