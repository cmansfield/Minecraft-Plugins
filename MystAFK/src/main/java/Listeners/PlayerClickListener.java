
package Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import Config.ConfigMessage;
import Config.IConfigMessage;
import Config.PlayerNameDecorator;
import io.github.cmansfield.MystAFK.MystAFK;


public class PlayerClickListener implements Listener {

	private final MystAFK plugin;
	
	
	public PlayerClickListener(MystAFK main) { this.plugin = main; }
	
	@EventHandler (priority = EventPriority.HIGH)
	public void eventHandler(PlayerInteractAtEntityEvent event) {
		
		Entity eClicked = event.getRightClicked();

		if(!(eClicked instanceof ArmorStand)) return;
		if(((ArmorStand)eClicked).getItemInHand() == null) return;
		if(((ArmorStand)eClicked).getItemInHand().getItemMeta() == null) return;
		if(((ArmorStand)eClicked).getItemInHand().getItemMeta().getDisplayName() == null) return;

		if(((ArmorStand)eClicked).getItemInHand().getItemMeta().getDisplayName().contains("AFK")) {

			event.getPlayer().sendMessage(ChatColor.RED 
				+ (new PlayerNameDecorator(
					new ConfigMessage(
						plugin, 
						"messages.ArmorStandEditErrMsg"
					), 
					event.getPlayer().getName())
				).getMessage()
			);
			
			event.setCancelled(true); 
		}
	}
}
