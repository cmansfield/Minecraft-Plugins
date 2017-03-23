package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import PlayerPlayTime.IPlayerPlayTime;


public class PlayerJoinListener implements Listener {
	
	private final IPlayerPlayTime timerHandler;
	
	public PlayerJoinListener(IPlayerPlayTime timerHandler) {
		
		this.timerHandler = timerHandler;
	}
	
	@EventHandler
	public void eventHandler(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if(player == null) return;
		
		timerHandler.addPlayer(player);
	}
}
