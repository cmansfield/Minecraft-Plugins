package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import PlayerPlayTime.IPlayerPlayTime;


public class PlayerMoveListener {

	private final IPlayerPlayTime timeHandler;
	
	
	public PlayerMoveListener(IPlayerPlayTime timeHandler) { this.timeHandler = timeHandler; }
	
	@EventHandler
	public void eventHandler(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		if(player == null) return;
		
		timeHandler.resetPlayerTimer(player);
	}
}
