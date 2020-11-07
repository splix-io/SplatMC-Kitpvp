package splatmc.splix.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
	public static String chat (String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	@SuppressWarnings("rawtypes")
	public static ItemStack createItem(Inventory inv, String materialid, int amount, int invSlot, String displayName, String... loreString) {
		ItemStack item = null;
		@SuppressWarnings("unchecked")
		List<String> lore = new ArrayList();
		
		Material Item = Material.matchMaterial(materialid);
		if (Item == null) {
			Bukkit.broadcastMessage("FAIL AYUDGUGSA");
			if (materialid == "NETHERITE_AXE") {
				Item = Material.NETHERITE_SWORD;
			}else if (materialid == "NETHERITE_INGOT") {
				Item = Material.NETHERITE_INGOT;
			}else if (materialid == "PINK_DYE") {
				Item = Material.PINK_DYE;
			}else if (materialid == "DIAMOND_AXE") {
				Item = Material.DIAMOND_AXE;
			}else if (materialid == "GOLDEN_AXE") {
				Item = Material.GOLDEN_AXE;
			}else if (materialid == "ENDER_EYE") {
				Item = Material.ENDER_EYE;
			}
			item = new ItemStack(Item	, amount);
		}else {
			item = new ItemStack(Item, amount);
		}
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.chat(displayName));
		for (String s: loreString) {
			lore.add(Utils.chat(s));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(invSlot, item);

		return item;
	}

	public static ItemStack Placeholder(Inventory inv, String material, int ammount, int Slot, int Untill) {
		ItemStack item = null;
		Material Item = Material.matchMaterial(material);
		item = new ItemStack(Item, ammount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("");
		item.setItemMeta(meta);
		for (int i = Slot; i <= Untill+Slot; ++i) {
			inv.setItem(i, item);
		}
		return item;
	}
}
