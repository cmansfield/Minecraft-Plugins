package PlayerPlayTime;

import org.bukkit.entity.Player;

public interface IPlayerPlayTime {

	public abstract void addPlayer(Player player);
	public abstract void removePlayer(Player player);
	public abstract void resetPlayerTimer(Player player);
	public abstract void addSecondToAllPlayersTimer();
}
