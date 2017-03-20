
package AFKplayers;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class AFKplayer implements IAFKplayer {

	public Entity specEntity;
	private GameMode playerGameMode;
	private Player player;
	
	public AFKplayer(Player player, GameMode mode) { 
		
		this.player = player;
		this.playerGameMode = mode;
		
		specEntity = player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
		specEntity.setInvulnerable(true);
		specEntity.setGravity(false);
		specEntity.setCustomName(player.getCustomName());
		
		player.setGameMode(GameMode.SPECTATOR);
		player.setSpectatorTarget(specEntity);
	}
	
	@Override
	public final GameMode getGameMode() { return playerGameMode; }
	
	@Override
	public final Player getPlayer() { return player; }
	
	@Override
	public final Entity getEntity() { return specEntity; }
}
