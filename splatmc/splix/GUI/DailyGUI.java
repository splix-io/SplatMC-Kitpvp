package splatmc.splix.GUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import splatmc.splix.utils.Utils;

public class DailyGUI implements Listener {
	
	   public static Inventory inv;
	   public static String inventory_name;
	   public static int inv_rows= 3 * 9;
	   
	   public static void initialize() {
		   inventory_name = Utils.chat("&6&lRank Menu");
		   inv = Bukkit.createInventory(null, inv_rows);
	   }
	   
	   public static Inventory Update(Player p) {
		   Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		   Utils.Placeholder(inv, "GREEN_STAINED_GLASS_PANE", 1, 0, 12);
		   Utils.createItem(inv, "NETHERITE_SWORD", 1, 13, "&9&lPvE", "&bFight mobs as your Daily Challange!",
				   "&1Check your Karma points!","&1You get rewards for having good Karma!");
		   Utils.Placeholder(inv, "GREEN_STAINED_GLASS_PANE", 1, 14, 3);
		   Utils.Placeholder(inv, "GREEN_STAINED_GLASS_PANE", 1, 18, 3);
		   
		   Utils.createItem(inv, "PINK_DYE", 1, 22, "&cCancel", "&9Do Daily Challange another time!");
		   
		   Utils.Placeholder(inv, "GREEN_STAINED_GLASS_PANE", 1, 23, 3);
		   toReturn.setContents(inv.getContents());
		   return toReturn;
	   }

}
