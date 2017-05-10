
package AFKplayers;

import java.util.List;
import org.bukkit.entity.Player;

public abstract interface IAFKplayers {

	public abstract boolean contains(Player player);
	public abstract void add(Player player);
	public abstract void add(AFKplayer player);
	public abstract void remove(Player player);
	public abstract IAFKplayers copy();
	public abstract List<AFKplayer> getPlayers();
	public abstract void reattachPlayerToEntity(Player player);
	public abstract void checkLocation();
}
