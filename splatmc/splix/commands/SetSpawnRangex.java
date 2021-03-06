package splatmc.splix.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import splatmc.splix.main.Main;
import splatmc.splix.utils.Utils;

public class SetSpawnRangex implements CommandExecutor {
	
	private Main plugin;
	public SetSpawnRangex(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("Rangex").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		FileConfiguration Config = plugin.getConfig();
		//YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "data.yml"));
		
		if (!(sender instanceof Player)) {
			//sender.sendMessage(Config.getString("OnlyPlayer"));
			return true; // add message to console
		}
		
		Player p = (Player) sender;
		if (p.hasPermission("Splatmc.Staff")) {
			double x = p.getLocation().getX();
			Config.set("1Rangex", x);
			plugin.saveConfig();
			p.sendMessage(Utils.chat("&dRangex &9Set!"));
		}else {
			p.sendMessage(Utils.chat("&cInsufficient permission!"));
		}
		return false;
	}
}
