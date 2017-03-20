
package AFKplayers;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public abstract interface IAFKplayer {
	
	public abstract GameMode getGameMode();
	public abstract Player getPlayer();
}
