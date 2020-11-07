package splatmc.splix.listeners.GUI;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import splatmc.splix.DailyChallange.PVE;
import splatmc.splix.main.Main;
import splatmc.splix.utils.Utils;

public class ClickAction implements Listener {

	private static Main plugin;
	private static long DAY = 1000 * 60 * 60 * 24;
	
	@SuppressWarnings("static-access")
	public ClickAction(Main plugin) {
		this.plugin = plugin;
	}
	
	public static void DailyGUIAction(Player p, int Slot, ItemStack clicked, Inventory inv) {
		if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&9&lPvE"))) {
			FileConfiguration Config = plugin.getConfig();
			File file = new File(plugin.getDataFolder(), "data.yml");
			YamlConfiguration Config2 = YamlConfiguration.loadConfiguration(file);
			UUID find = p.getUniqueId();
			try{
				int LastTime = Config2.getInt(find.toString());
				long ut2 = System.currentTimeMillis();
				int current = (int)ut2;
				if ((current-LastTime) > DAY) {
					Config2.set(find.toString(), current);
					plugin.saveConfig();
					PVE.ToCAll(p);
				}else {
					if (p.hasPermission("Splatmc.Staff")) {
						Config2.set(find.toString(), current);
						plugin.saveConfig();
						p.sendMessage(Utils.chat("&cStaff Member! Do not do this often. You are in your Cooldown"));
						PVE.ToCAll(p);
					}else {
						p.sendMessage(Utils.chat(Config.getString("DailyChallangeCooldown")));
					}
				}
			}catch(NullPointerException e) {
				long ut2 = System.currentTimeMillis();
				int current = (int)ut2;
				Config2.set(find.toString(), current);
				PVE.ToCAll(p);
			}
			try {
				Config2.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
