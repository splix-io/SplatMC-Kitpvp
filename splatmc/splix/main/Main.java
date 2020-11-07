package splatmc.splix.main;

import org.bukkit.plugin.java.JavaPlugin;

import splatmc.splix.DailyChallange.PVE;
import splatmc.splix.GUI.DailyGUI;
import splatmc.splix.commands.DailyChallange;
import splatmc.splix.commands.DualRequest;
import splatmc.splix.commands.SetLocationDual1;
import splatmc.splix.commands.SetLocationDual2;
import splatmc.splix.commands.SetSpawnChallange;
import splatmc.splix.commands.SetSpawnRangex;
import splatmc.splix.commands.SetSpawnRangez;
import splatmc.splix.commands.SetSpawnSpawn;
import splatmc.splix.listeners.Chat.Chat;
import splatmc.splix.listeners.GUI.ClickAction;
import splatmc.splix.listeners.GUI.Clicked;
import splatmc.splix.pvp.dual.Dual;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		new DailyChallange(this);
		new SetSpawnSpawn(this);
		new SetSpawnChallange(this);
		new PVE(this);
		new Clicked(this);
		new Chat(this);
		new ClickAction(this);
		new SetSpawnRangex(this);
		new SetSpawnRangez(this);
		new SetLocationDual1(this);
		new SetLocationDual2(this);
		new DualRequest(this);
		new Dual(this);
		DailyGUI.initialize();
	}

}
