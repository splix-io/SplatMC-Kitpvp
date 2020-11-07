package splatmc.splix.listeners.GUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import splatmc.splix.GUI.DailyGUI;
import splatmc.splix.main.Main;;

public class Clicked implements Listener {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public Clicked(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e == null) {
			return;
		}
		String title = e.getView().getTitle();
		if (title.equals(DailyGUI.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}else {
				ClickAction.DailyGUIAction((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory());
			}
		}
	}
}
