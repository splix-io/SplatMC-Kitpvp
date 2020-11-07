package splatmc.splix.listeners.GUI;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import splatmc.splix.GUI.DailyGUI;

public class Manager implements Listener {
	
	public static boolean StartDailyGUI(Player p) {
		p.openInventory(DailyGUI.Update(p));
		return false;
	}
}
