package splatmc.splix.listeners.Chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import splatmc.splix.main.Main;
import splatmc.splix.utils.Utils;

public class Chat implements Listener {

	@SuppressWarnings("unused")
	private static Main plugin;
		
	@SuppressWarnings("static-access")
	public Chat(Main plugin) {
		this.plugin = plugin;
			
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPreCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (!(player.isOp())) {
			if (!(player.hasPermission("SplatMC.Daily"))) {
				event.setCancelled(true);
				player.sendMessage(Utils.chat("&9You cant run command while doing your Daily Challange"));
			}
		}
	}
}