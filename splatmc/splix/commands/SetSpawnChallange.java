package splatmc.splix.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import splatmc.splix.main.Main;
import splatmc.splix.utils.Utils;

public class SetSpawnChallange implements CommandExecutor {
	
	private Main plugin;
	public SetSpawnChallange(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("SetSpawnChallange").setExecutor(this);
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
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			Config.set("1Locationx", x);
			Config.set("1Locationy", y);
			Config.set("1Locationz", z);
			plugin.saveConfig();
			p.sendMessage(Utils.chat(Config.getString("ChallangeSpawnSet")));
		}else {
			p.sendMessage(Utils.chat("&cInsufficient permission!"));
		}
		return false;
	}
}
