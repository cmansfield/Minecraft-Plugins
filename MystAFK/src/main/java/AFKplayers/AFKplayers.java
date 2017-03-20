
package AFKplayers;

import java.util.ArrayList;
import java.util.List;
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
				
				players.remove(plr);
				return;
			}
		}
	}

	@Override
	public IAFKplayers copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AFKplayer> getPlayers() { return players; }

	@Override
	public void add(AFKplayer player) { players.add(player); }
}
