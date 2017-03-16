

package io.github.cmansfield.MystAFK;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MystAFK extends JavaPlugin {

	private final TestListener testListiner = new TestListener(this);
	private final String PLUGIN_NAME = "MystAFK";
	public boolean isEnabled = false;
	
    @Override
    public void onEnable() {

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(testListiner, this);
        
        getCommand("afk").setExecutor(this);
        getCommand("bypassAFK").setExecutor(this);
        
        getLogger().info(PLUGIN_NAME + " Plugin Enabled");
    }

    @Override
    public void onDisable() {
    
    	getLogger().info(PLUGIN_NAME + " Plugin Disabled");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	
    	if(commandLabel.equalsIgnoreCase("afk")) {
    		
    		isEnabled = !isEnabled;
    	}
    	else if(commandLabel.equalsIgnoreCase("bypassAFK")) {
    		
    		//isEnabled = false;
    	}
    	
    	return true;
    }
}
