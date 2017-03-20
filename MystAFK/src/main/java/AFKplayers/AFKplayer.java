
package AFKplayers;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class AFKplayer implements IAFKplayer {

	public Entity specEntity;
	private GameMode playerGameMode;
	private Player player;
	
	public AFKplayer(Player player, GameMode mode) { 
		
		this.player = player;
		this.playerGameMode = mode;
	}
	
	@Override
	public final GameMode getGameMode() { return playerGameMode; }
	
	@Override
	public final Player getPlayer() { return player; }
}
