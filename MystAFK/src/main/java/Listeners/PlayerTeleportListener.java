
package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import AFKplayers.IAFKplayers;


public class PlayerTeleportListener implements Listener {

	private final IAFKplayers afkPlayers;
	
	
	public PlayerTeleportListener(IAFKplayers afkPlayers) { 

		this.afkPlayers = afkPlayers;
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void eventHandler(PlayerTeleportEvent event) {
		
		Player player = event.getPlayer();
		
		if(player == null) return;
		
		if(afkPlayers.contains(player)) {
			
			event.setCancelled(true);
			
			afkPlayers.reattachPlayerToEntity(player);
		}
	}
}
