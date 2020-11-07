package splatmc.splix.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import splatmc.splix.main.Main;
import splatmc.splix.utils.Utils;

public class SetLocationDual1 implements CommandExecutor {
	
	private Main plugin;
	public SetLocationDual1(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("LocationDual1").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		FileConfiguration Config = plugin.getConfig();
		
		if (!(sender instanceof Player)) {
			//sender.sendMessage(Config.getString("OnlyPlayer"));
			return true; // add message to console
		}
		
		Player p = (Player) sender;
		if (p.hasPermission("Splatmc.Staff")) {
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			Config.set("Dual.Dual1.x1", (int) x);
			Config.set("Dual.Dual1.y1", (int) y);
			Config.set("Dual.Dual1.z1", (int) z);
			p.sendMessage(Utils.chat("&dDual Spawn &9Set!"));
		}else {
			p.sendMessage(Utils.chat("&cInsufficient permission!"));
		}
		return false;
	}
}
