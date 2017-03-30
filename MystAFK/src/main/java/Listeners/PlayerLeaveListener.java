package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import io.github.cmansfield.MystAFK.MystAFK;


public class PlayerLeaveListener implements Listener {

	private final MystAFK plugin;
	
	public PlayerLeaveListener(MystAFK main) {
		
		this.plugin = main;
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void eventHandler(PlayerQuitEvent event) {
		
		if(event == null) return;

		Player player = event.getPlayer();
		
		if(player == null) return;
		
		if(plugin.isAFK(player)) plugin.toggleAFK(player, false);
	}
}
