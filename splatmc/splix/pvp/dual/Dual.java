package splatmc.splix.pvp.dual;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import splatmc.splix.main.Main;
import splatmc.splix.utils.Utils;

public class Dual implements Listener {
	
	private static Main plugin;
	private static FileConfiguration Config = plugin.getConfig();
	private int ToEnd = 0;
	private static BossBar bar1 = plugin.getServer().createBossBar(Utils.chat("&c&lDual!"),BarColor.WHITE,BarStyle.SOLID);
	private static BossBar bar2 = plugin.getServer().createBossBar(Utils.chat("&c&lDual!"),BarColor.WHITE,BarStyle.SOLID);
	private static String Tag = "Dual";
	private static Player player1;
	private static Player player2;
	
	@SuppressWarnings("static-access")
	public Dual(Main plugin) {
		this.plugin = plugin;
	}
	
	public static void SpawnPlayer1(Player p) {
		int x = Config.getInt("Dual.Dual1.x1");
		int y = Config.getInt("Dual.Dual1.y1");
		int z = Config.getInt("Dual.Dual1.z1");
		Location l = new Location(p.getWorld(), x, y, z);;
		p.teleport(l);
	}
	
	public static void SpawnPlayer2(Player p) {
		int x = Config.getInt("Dual.Dual1.x2");
		int y = Config.getInt("Dual.Dual1.y2");
		int z = Config.getInt("Dual.Dual1.z2");
		Location l = new Location(p.getWorld(), x, y, z);;
		p.teleport(l);
	}
	
	public static void GiveItems(Player p) {
		PlayerInventory I = p.getInventory();
		ItemStack food= new ItemStack(Material.COOKED_BEEF, 16);
		ItemStack sword = new ItemStack(Material.STONE_SWORD, 1);
		ItemStack bow = new ItemStack(Material.BOW, 1);
		ItemStack arrows = new ItemStack(Material.ARROW, 32);
		ItemMeta swordmeta = sword.getItemMeta();
		swordmeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		sword.setItemMeta(swordmeta);
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		I.setItem(0, sword);
		I.setItem(1, bow);
        I.setItem(2, food);
        I.setItem(8, arrows);
        I.setHelmet(helmet);
        I.setChestplate(chestplate);
        I.setLeggings(legs);
        I.setBoots(boots);
	}
	
	public static void Start(Player p1, Player p2) {
		p1.getInventory().clear();
		p2.getInventory().clear();
		bar1.addPlayer(p1);
		bar2.addPlayer(p2);
		p1.addScoreboardTag(Tag);
		p2.addScoreboardTag(Tag);
		player1 = p1;
		player2 = p2;
		SpawnPlayer1(player1);
		SpawnPlayer2(player2);
		GiveItems(player1);
		GiveItems(player2);
	}
	
	public void Main(Player p1, Player p2) {
		ToEnd = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				BarUpdate(p1,p2);
			}
		}, 0L, 5L);
	}
	
	public void BarUpdate(Player p1, Player p2) {
		int hp1 = (int) p1.getHealth()*5;
		int hp2 = (int) p2.getHealth()*5;
		int barhp1 = hp1 / 100;
		int barhp2 = hp2 / 100;
		bar2.setProgress(barhp1);
		bar1.setProgress(barhp2);
	}
	
	public void PlayerDeath(PlayerDeathEvent p) {
		p.getDrops().clear();
		final Player player = p.getEntity();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.spigot().respawn(), 1L);
		if (p.getEntity().getName().equals(player1.getName())) {
			player1.setGameMode(GameMode.SPECTATOR);
			SpawnPlayer1(player1);
			Bukkit.getServer().getScheduler().cancelTask(ToEnd);
			bar1.setProgress(0);
			bar1.setColor(BarColor.RED);
			bar1.setTitle(Utils.chat("&d&l"+player2.getName()+" &9&lHas Won the Dual!"));
			bar2.setProgress(1);
			bar2.setColor(BarColor.GREEN);
			bar2.setTitle(Utils.chat("&a&lYou have won the dual!"));
			player1.getInventory().clear();
			player2.getInventory().clear();
			player2.playSound(player2.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
			player1.playSound(player1.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Location Spawn = new Location(player2.getWorld(), Config.getInt("SLocationx"), Config.getInt("Slocationy")
					, Config.getInt("Slocationz"));
			player1.teleport(Spawn);
			player2.teleport(Spawn);
			bar1.removePlayer(player1);
			bar2.removePlayer(player2);
		}else if (p.getEntity().getName().equals(player2.getName())){
			//Player 2 Death
		}
	}
	
	public void ResetVars() {
		bar1 = plugin.getServer().createBossBar(Utils.chat("&c&lDual!"),BarColor.WHITE,BarStyle.SOLID);
		bar2 = plugin.getServer().createBossBar(Utils.chat("&c&lDual!"),BarColor.WHITE,BarStyle.SOLID);
	}
}
