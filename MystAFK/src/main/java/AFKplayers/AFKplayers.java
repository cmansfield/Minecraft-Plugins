
package AFKplayers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class AFKplayers implements IAFKplayers {

	private List<AFKplayer> players;
	
	public AFKplayers() { this.players = new ArrayList<AFKplayer>(); }
	
	@Override
	public boolean contains(Player player) { 
		
		for(AFKplayer plr : players) {
			
			if(plr.getPlayer() == player) return true;
		}
		
		return false;
	}

	@Override
	public void add(Player player) { 
		
		players.add(new AFKplayer(player, player.getGameMode())); 
	}

	@Override
	public void remove(Player player) { 
		
		players.remove(player);
		for(AFKplayer plr : players) {
			
			if(plr.getPlayer() == player) {
				
		    	player.setGameMode(plr.getGameMode());
		    	plr.getEntity().remove();
				
				players.remove(plr);
				return;
			}
		}
	}

	@Override
	public IAFKplayers copy() {
		
    	IAFKplayers afkplrs = new AFKplayers();
    	
    	for(AFKplayer player : players) afkplrs.add(player);
    	
		return afkplrs;
	}

	@Override
	public List<AFKplayer> getPlayers() { return players; }

	@Override
	public void add(AFKplayer player) { players.add(player); }

	@Override
	public void reattachPlayerToEntity(Player player) {
		
		for(AFKplayer plr : players) {
			
			if(plr.getPlayer() == player) {
				
		    	player.setSpectatorTarget(plr.getEntity());
				return;
			}
		}
	}
}
