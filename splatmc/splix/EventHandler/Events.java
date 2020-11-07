package splatmc.splix.EventHandler;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import splatmc.splix.DailyChallange.PVE;

public class Events implements Listener {
	
	private static ArrayList<Player> ListeningPlayers = new ArrayList<>();
	private static ArrayList<AsyncPlayerChatEvent> StringPlayers = new ArrayList<>();
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (e.getEntity().hasPermission("SplatMC.Daily")) {
			PVE.Death(e);
		}
		if (e.getEntity().getScoreboardTags().contains("Dual")) {
			
		}
	}
	
	@EventHandler
    public void onRespawn(PlayerRespawnEvent e){
		if (e.getPlayer().hasPermission("SplatMC.Daily")) {
			PVE.Respawn(e);
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (e.getPlayer().hasPermission("SplatMC.Daily")) {
			PVE.Quit(e);
		}
	}
	
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (ListeningPlayers.contains(p)) {
			StringPlayers.add(e);
		}
	}
	
	public static void ReadFor(Player p) {
		ListeningPlayers.add(p);
	}
	
	public static ArrayList<AsyncPlayerChatEvent> ReadRecvFor() {
		return StringPlayers;
	}
}
