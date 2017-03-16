
package io.github.cmansfield.MystAFK;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class TestListener implements Listener{

	private final MystAFK plugin;
	
	public TestListener(MystAFK instance) {
		
		this.plugin = instance;
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onBlockInteract(PlayerInteractEvent event) {
		
		if(event.getPlayer() == null) { return; }
		
		if(!plugin.isEnabled) return;
		
		// Check to see if a physical action occurs, if there
		// is one then the player could have stepped on a pressure
		// plate, if not then return from this function
		if(!event.getAction().equals(Action.PHYSICAL)) return;
	
		switch(event.getClickedBlock().getType()) {
		case STONE_PLATE:
		case WOOD_PLATE:
		case IRON_PLATE:
		case GOLD_PLATE:
			break;
		default:
			return;
		}

		event.setCancelled(true);
	}
}
