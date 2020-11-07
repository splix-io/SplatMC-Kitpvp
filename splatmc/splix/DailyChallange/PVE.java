package splatmc.splix.DailyChallange;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachment;

import splatmc.splix.main.Main;
import splatmc.splix.utils.Utils;

public class PVE implements Listener {
	
	private static int Wave = 1;
	private static ArrayList<Entity> Mobs = new ArrayList<Entity>();
	private static Main plugin;
	private static long DAY = 1000 * 60 * 60 * 24;
	//private static int tostop = 0;
	private static int deathcounter = 0;
	private static HashMap<Player , ItemStack[]> items = new HashMap<Player , ItemStack[]>();
	private static BossBar bar = plugin.getServer().createBossBar(Utils.chat("&c&lChallange! &bPvE &dKill all Mobs"),BarColor.BLUE,BarStyle.SOLID);
	
	@SuppressWarnings("static-access")
	public PVE(Main plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public static void Death(PlayerDeathEvent e) {
		FileConfiguration Config = plugin.getConfig();
		e.getDrops().clear();
		final Player player = e.getEntity();
		if (deathcounter==3){
			File file = new File(plugin.getDataFolder(), "data.yml");
			YamlConfiguration Config2 = YamlConfiguration.loadConfiguration(file);
			UUID find = player.getUniqueId();
			long ut2 = System.currentTimeMillis();
			int current = (int) (ut2 - DAY);
			player.sendMessage(Utils.chat(Config.getString("DailyChallangeFail")));
			Config2.set(find.toString(), current);
			plugin.saveConfig();
			Stop(player);
		}else{
			deathcounter +=1;
			player.sendMessage(Utils.chat("&d"+player.getName()+" &9You have died &c"+deathcounter+"&9! If you die &a3 &9times you lose the challange!"));
        	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.spigot().respawn(), 1L);
        	ItemStack[] content = e.getEntity().getInventory().getContents();
        	items.put(e.getEntity(), content);
		}
	}
	
	public static void Respawn(PlayerRespawnEvent e) {
		FileConfiguration Config = plugin.getConfig();
		Player p = e.getPlayer();
		World world = p.getWorld();
		Location l = new Location(world,Config.getInt("1Locationx"),
				Config.getInt("1Locationy"),
				Config.getInt("1Locationz"));
		e.setRespawnLocation(l);
        if(items.containsKey(e.getPlayer())){
            e.getPlayer().getInventory().clear();
            for(ItemStack stack : items.get(e.getPlayer())){
            	if (!(stack == null)) {
            		e.getPlayer().getInventory().addItem(stack);
            	}
            }
           
            items.remove(e.getPlayer());
        }
	}
	
	public static void Quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		for (Entity ent : Mobs) {
			ent.remove();
		}
		Wave = 1;
		deathcounter = 0;
		Mobs.clear();
		bar.removePlayer(p);
		FileConfiguration Config = plugin.getConfig();
		World world = p.getWorld();
		HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
        PermissionAttachment attachment = p.addAttachment(plugin);
        perms.put(p.getUniqueId(), attachment);
		perms.get(p.getUniqueId()).unsetPermission("SplatMC.Daily");
		//
		Location l = new Location(world,Config.getInt("Slocationx"),
				Config.getInt("Slocationy"),
				Config.getInt("Slocationz"));
		p.teleport(l);
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setHelmet(new ItemStack(Material.AIR));
		inv.setChestplate(new ItemStack(Material.AIR));
		inv.setLeggings(new ItemStack(Material.AIR));
		inv.setBoots(new ItemStack(Material.AIR));
	}
	
	public static void ToCAll(Player p) {
		FileConfiguration Config = plugin.getConfig();
		World world = p.getWorld();
		Location l = new Location(world,Config.getInt("1Locationx"),
				Config.getInt("1Locationy"),
				Config.getInt("1Locationz"));
		p.teleport(l);
		p.setGameMode(GameMode.SURVIVAL);
		PlayerInventory inv= p.getInventory();
		inv.clear();
		ItemStack food= new ItemStack(Material.COOKED_BEEF, 16);
		ItemStack sword = new ItemStack(Material.WOODEN_SWORD, 1);
		ItemMeta swordmeta = sword.getItemMeta();
		swordmeta.addEnchant(Enchantment.DURABILITY, 10, true);
		sword.setItemMeta(swordmeta);
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		inv.setItem(0, sword);
        inv.setItem(1, food);
        inv.setHelmet(helmet);
        inv.setChestplate(chestplate);
        inv.setLeggings(legs);
        inv.setBoots(boots);
        // Perms
		HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
        PermissionAttachment attachment = p.addAttachment(plugin);
        perms.put(p.getUniqueId(), attachment);
        PermissionAttachment pperms = perms.get(p.getUniqueId());
        pperms.setPermission("SplatMC.Daily", true);
        // Create Bar
        //bar = p.getServer().createBossBar(Utils.chat("&c&lChallange! &bPvE &dKill all Mobs"), Color.AQUA, style., barFlags);
        bar.addPlayer(p);
        Spawn(p);
        //Bukkit.getScheduler().cancelTasks(plugin);
	}
	
	public static void Stop(Player p) {
		Bukkit.broadcastMessage("Stop");
		Wave = 1;
		deathcounter = 0;
		Mobs.clear();
		bar.removePlayer(p);
		//bar.removeAll();
		FileConfiguration Config = plugin.getConfig();
		World world = p.getWorld();
		HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
        PermissionAttachment attachment = p.addAttachment(plugin);
        perms.put(p.getUniqueId(), attachment);
		perms.get(p.getUniqueId()).unsetPermission("SplatMC.Daily");
		//
		Location l = new Location(world,Config.getInt("Slocationx"),
				Config.getInt("Slocationy"),
				Config.getInt("Slocationz"));
		p.teleport(l);
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setHelmet(new ItemStack(Material.AIR));
		inv.setChestplate(new ItemStack(Material.AIR));
		inv.setLeggings(new ItemStack(Material.AIR));
		inv.setBoots(new ItemStack(Material.AIR));
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"bc It works");
	}
	
	public static void Levelup(Player p) {
		PlayerInventory inv = p.getInventory();
		p.setHealth(20);
		p.setFoodLevel(20);
		if (Wave == 2) {
			inv.clear();
			ItemStack food= new ItemStack(Material.COOKED_BEEF, 2);
			ItemStack sword = new ItemStack(Material.STONE_SWORD, 1);
			ItemStack bow = new ItemStack(Material.BOW, 1);
			ItemStack arrows = new ItemStack(Material.ARROW, 32);
			ItemMeta swordmeta = sword.getItemMeta();
			swordmeta.addEnchant(Enchantment.DURABILITY, 10, true);
			swordmeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			sword.setItemMeta(swordmeta);
			ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
			ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
			ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
			inv.setItem(0, sword);
			inv.setItem(1, bow);
	        inv.setItem(2, food);
	        inv.setItem(8, arrows);
	        inv.setHelmet(helmet);
	        inv.setChestplate(chestplate);
	        inv.setLeggings(legs);
	        inv.setBoots(boots);
		}else if (Wave == 3) {
			inv.clear();
			ItemStack food= new ItemStack(Material.COOKED_BEEF, 3);
			ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);
			ItemStack bow = new ItemStack(Material.BOW, 1);
			ItemStack arrows = new ItemStack(Material.ARROW, 32);
			ItemMeta swordmeta = sword.getItemMeta();
			ItemMeta bowmeta = bow.getItemMeta();
			swordmeta.addEnchant(Enchantment.DURABILITY, 10, true);
			swordmeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
			bowmeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
			sword.setItemMeta(swordmeta);
			bow.setItemMeta(bowmeta);
			ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
			ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			ItemStack legs = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
			inv.setItem(0, sword);
			inv.setItem(1, bow);
	        inv.setItem(2, food);
	        inv.setItem(8, arrows);
	        inv.setHelmet(helmet);
	        inv.setChestplate(chestplate);
	        inv.setLeggings(legs);
	        inv.setBoots(boots);
		}else if (Wave == 4) {
			inv.clear();
			ItemStack food= new ItemStack(Material.COOKED_BEEF, 4);
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
			ItemStack bow = new ItemStack(Material.BOW, 1);
			ItemStack arrows = new ItemStack(Material.ARROW, 32);
			ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 1);
			ItemStack shield = new ItemStack(Material.SHIELD, 1);
			ItemMeta swordmeta = sword.getItemMeta();
			ItemMeta bowmeta = bow.getItemMeta();
			swordmeta.addEnchant(Enchantment.DURABILITY, 10, true);
			swordmeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
			bowmeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
			sword.setItemMeta(swordmeta);
			bow.setItemMeta(bowmeta);
			ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
			ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			ItemStack legs = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
			inv.setItem(0, sword);
			inv.setItem(1, bow);
			inv.setItem(2, gapple);
	        inv.setItem(3, food);
	        inv.setItem(8, arrows);
	        inv.setItemInOffHand(shield);
	        inv.setHelmet(helmet);
	        inv.setChestplate(chestplate);
	        inv.setLeggings(legs);
	        inv.setBoots(boots);
		}else if (Wave == 5) {
			inv.clear();
			ItemStack food= new ItemStack(Material.COOKED_BEEF, 5);
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
			ItemStack bow = new ItemStack(Material.BOW, 1);
			ItemStack arrows = new ItemStack(Material.ARROW, 32);
			ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 2);
			ItemStack shield = new ItemStack(Material.SHIELD, 1);
			ItemMeta swordmeta = sword.getItemMeta();
			ItemMeta bowmeta = bow.getItemMeta();
			ItemMeta shieldmeta = shield.getItemMeta();
			swordmeta.addEnchant(Enchantment.DURABILITY, 10, true);
			swordmeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
			bowmeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
			bowmeta.addEnchant(Enchantment.ARROW_FIRE, 2, true);
			shieldmeta.addEnchant(Enchantment.DURABILITY, 3, true);
			sword.setItemMeta(swordmeta);
			bow.setItemMeta(bowmeta);
			shield.setItemMeta(shieldmeta);
			ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
			ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			ItemStack legs = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
			inv.setItem(0, sword);
			inv.setItem(1, bow);
			inv.setItem(2, gapple);
	        inv.setItem(3, food);
	        inv.setItem(8, arrows);
	        inv.setItemInOffHand(shield);
	        inv.setHelmet(helmet);
	        inv.setChestplate(chestplate);
	        inv.setLeggings(legs);
	        inv.setBoots(boots);
		}
	}
	
	public static void Spawn(Player p) {
		Bukkit.broadcastMessage("Spawn");
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				Bukkit.broadcastMessage("Working");
				if (areMobsDead(p, Mobs)) {
					Levelup(p);
					Mobs = Spawn2(p);
				}
			}
		}, 0L, 40L);
	}
	
	@SuppressWarnings("deprecation")
	public static ArrayList<Entity> Spawn2(Player p) {
		ArrayList<Entity> Tosend = new ArrayList<Entity>();
		if (Wave == 6 ) {
    		Bukkit.getScheduler().cancelTasks(plugin);;
    		Stop(p);
    		return null;
    	}else {
			// Spawning 
			FileConfiguration Config = plugin.getConfig();
			World world = p.getWorld();
			Location l = new Location(world,Config.getInt("1Locationx"),
					Config.getInt("1Locationy"),
					Config.getInt("1Locationz"));
			int minx;
			int maxx;
			int minz;
			int maxz;
			if (Config.getInt("1Locationx") > Config.getInt("1Rangex")) {
				minx = Config.getInt("1Rangex")- Config.getInt("1Locationx") ;
			    maxx = Config.getInt("1Locationx")-Config.getInt("1Rangex");
			}else {
				maxx = Config.getInt("1Rangex")- Config.getInt("1Locationx") ;
			    minx = Config.getInt("1Locationx")-Config.getInt("1Rangex");
			}
			if (Config.getInt("1Locationz") > Config.getInt("1Rangez")) {
				minz = Config.getInt("1Rangez")- Config.getInt("1Locationz") ;
			    maxz = Config.getInt("1Locationz")-Config.getInt("1Rangez");
			}else {
				maxz = Config.getInt("1Rangez")- Config.getInt("1Locationz") ;
			    minz = Config.getInt("1Locationz")-Config.getInt("1Rangez");
			}
			int count = Wave*5-1;
			for (int i = 0; i <= count; i++) {
				int random_intx = (int)(Math.random() * (maxx - minx + 1) + minx);
				int random_intz = (int)(Math.random() * (maxz - minz + 1) + minz);
				if (Wave >= 4) {
					if (i >= 20 && i % 2 == 0) {
						Entity Mob = (Ravager) world.spawnEntity(l.add(random_intx,0,random_intz), EntityType.RAVAGER);
						Tosend.add(Mob);
					}else if (i % 2 == 0) {
						Entity Mob = (Skeleton) world.spawnEntity(l.add(random_intx,0,random_intz), EntityType.SKELETON);
						Tosend.add(Mob);
					}else {
						Entity Mob = (Zombie) world.spawnEntity(l.add(random_intx,0,random_intz), EntityType.ZOMBIE);
						Tosend.add(Mob);
					}
				}else {
					if (i % 2 == 0) {
						Entity Mob = (Skeleton) world.spawnEntity(l.add(random_intx,0,random_intz), EntityType.SKELETON);
						Tosend.add(Mob);
					}else {
						Entity Mob = (Zombie) world.spawnEntity(l.add(random_intx,0,random_intz), EntityType.ZOMBIE);
						Tosend.add(Mob);
					}
				}
			}
			count+=1;
			p.sendTitle(Utils.chat("&dPlayer&b! Kill all"), Utils.chat("&a"+count+" &bMobs"));
			p.sendMessage(Utils.chat("&dPlayer&b! Kill all &a"+count+" &bMobs"));
			Bukkit.broadcastMessage(Tosend.toString());
			return Tosend;
    	}
	}
	
	public static boolean areMobsDead(Player p,ArrayList<Entity> ents) {
		int count = 0;
		if (ents.isEmpty()) {
			return true;
		}
		for (Entity ent : ents) {
			if (!ent.isDead()) {
				//return false; //If any of the mobs are alive don't spawn new ones
				count += 1;
			}
		}
		if (count==0) {
			bar.setColor(BarColor.BLUE);
			bar.setProgress(1);
			Wave+=1;
			return true;
		}
		
		//double progress = count/total4;
		//if (progress < 0.4) {
		//	bar.setColor(BarColor.RED);
		//}else if (progress < 0.7) {
		//	bar.setColor(BarColor.YELLOW);
		//}else {
		//	bar.setColor(BarColor.GREEN);
		//}
		bar.setColor(BarColor.GREEN);
		bar.setTitle(Utils.chat("&b&lKill all &a&l"+count+" &d&lMobs&b&l! &b&lWave &9&l"+Wave+"&b&lof &9&l5"));
		p.setLevel(count);
		p.setExp(1);
		//bar.setProgress(progress);
		return false;
	}
	
	//ADD
	//DEATH FAIL
	//PROGRESS BAR IN areMobsDead
	//HEAL
	
}