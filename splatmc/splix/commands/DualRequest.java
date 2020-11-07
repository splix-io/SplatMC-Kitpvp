package splatmc.splix.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import splatmc.splix.EventHandler.Events;
import splatmc.splix.main.Main;
import splatmc.splix.pvp.dual.Dual;
import splatmc.splix.utils.Utils;

public class DualRequest implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public DualRequest(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("dual").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {		
		if (!(sender instanceof Player)) {
			//sender.sendMessage(Config.getString("OnlyPlayer"));
			return true; // add message to console
		}
		Player p = (Player) sender;
		if (args.length == 0) {
			p.sendMessage(Utils.chat("&d"+p.getName()+" &9Type a player name &cCorrect usage /dual <player>"));
		}else if (args.length > 1) {
			p.sendMessage(Utils.chat("&d"+p.getName()+" &9Type a player name &cCorrect usage /dual <player>"));
		}else {
			Player p2 = Bukkit.getPlayer(args[0]);
			if (p2 == null) {
				p.sendMessage(Utils.chat("&d"+p.getName()+" &9Type a player name &cCorrect usage /dual <player>"));
			}else{
				p.sendMessage(Utils.chat("&d"+p.getName()+" &9Is Requesting for a dual! Type &aaccept &9To dual them in 10s!"));
				Events.ReadFor(p2);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ArrayList<AsyncPlayerChatEvent> List = Events.ReadRecvFor();
				boolean bv = false;
				for (int i = 0; i < List.size(); i++) {
					AsyncPlayerChatEvent x = List.remove(i);
					if (x.getPlayer().getName().equals(p2.getName())) {
						if (x.getMessage().equalsIgnoreCase("accept")) {
							bv = true;
						}
					}
				}
				if (bv == true) {
					Dual.Start(p, p2);
				}else {
					p.sendMessage(Utils.chat("&d"+p.getName()+" &9The Requested player did not accept the dual"));
				}
			}
		}
		return false;
	}
}