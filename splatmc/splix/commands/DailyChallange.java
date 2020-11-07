package splatmc.splix.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import splatmc.splix.listeners.GUI.Manager;
import splatmc.splix.main.Main;

public class DailyChallange implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public DailyChallange(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("challange").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {		
		if (!(sender instanceof Player)) {
			//sender.sendMessage(Config.getString("OnlyPlayer"));
			return true; // add message to console
		}
		Player p = (Player) sender;
		Manager.StartDailyGUI(p);
		return false;
	}
}
