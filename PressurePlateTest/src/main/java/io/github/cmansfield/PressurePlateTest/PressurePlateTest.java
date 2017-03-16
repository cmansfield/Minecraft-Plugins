


package io.github.cmansfield.PressurePlateTest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PressurePlateTest extends JavaPlugin {

	private final TestListener testListiner = new TestListener(this);
	public boolean isEnabled = false;
	
    @Override
    public void onEnable() {

        getLogger().info("Test Plugin Enabled");
        
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(testListiner, this);
        
        getCommand("ppEnable").setExecutor(this);
        getCommand("ppDisable").setExecutor(this);
    }

    @Override
    public void onDisable() {
    
    	getLogger().info("Test Plugin Disabled");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	
    	if(commandLabel.equalsIgnoreCase("ppEnable")) {
    		
    		isEnabled = true;
    	}
    	else if(commandLabel.equalsIgnoreCase("ppDisable")) {
    		
    		isEnabled = false;
    	}
    	
    	return true;
    }
}
