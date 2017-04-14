
package AFKplayers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.Material;

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
		
		for(AFKplayer plr : players) {
			
			if(plr.getPlayer() == player) {
				
		    	player.setGameMode(plr.getGameMode());
		    	plr.getEntity().remove();
				
				players.remove(plr);
				
				if(!isSafe(player.getLocation())) {
					
					Location spyglass = player.getWorld().getHighestBlockAt(player.getLocation()).getLocation();
					
					while(!isSafe(spyglass)) {
						
						// Add 1 to x pos
						spyglass.add(1, 0, 0);
						spyglass = player.getWorld().getHighestBlockAt(spyglass).getLocation();
					}
					
					player.teleport(spyglass);
				}
				
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
				
		    	if(player.getGameMode() == GameMode.SPECTATOR) player.setSpectatorTarget(plr.getEntity());
				
		    	return;
			}
		}
	}
	
    public boolean isSafe(Location location) {
    	
        Block feet = location.getBlock();
        
        if (!feet.getType().isTransparent()) { return false; }
        
        Block head = feet.getRelative(BlockFace.UP);
        
        if (!head.getType().isTransparent()) { return false; }
        
        Block ground = feet.getRelative(BlockFace.DOWN);
        
        if (!ground.getType().isSolid()) { return false; }
        
        
        Location spyglass = feet.getLocation();
        int height = 0;
        
        while(spyglass.getBlock().getType().isTransparent() && spyglass.getY() != 0) {
        	
        	spyglass.add(0, -1, 0);
        	++height;
        }
        
        if(height > 5) { return false; }
        
        if(spyglass.getBlock().getType() == org.bukkit.Material.LAVA) { return false; }
        
        return true;
    }
}
