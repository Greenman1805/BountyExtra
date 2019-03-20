package de.greenman1805.bountyextra;

import net.milkbowl.vault.economy.Economy;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static Economy econ = null;
	public static String prefix = "§f[§6Bounty§f] §7";
	public static YamlConfiguration playerdata;

	public static int bounty_kill;
	public static int bounty_after_death;

	@Override
	public void onEnable() {
		if (!setupEconomy()) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PvPListener(), this);
		checkFiles();
		setupConfig();
		getValues();
		loadPlayerData();
	}
	
	
	@Override
	public void onDisable() {
		savePlayerData();
	}

	public static void loadPlayerData() {
		playerdata = YamlConfiguration.loadConfiguration(new File("plugins//BountyExtra//playerdata.yml"));
	}

	public static void savePlayerData() {
		try {
			playerdata.save(new File("plugins//BountyExtra//playerdata.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkFiles() {
		File file1 = new File("plugins//BountyExtra");
		File file2 = new File("plugins//BountyExtra//playerdata.yml");

		if (!file1.isDirectory()) {
			file1.mkdir();
		}

		if (!file2.exists()) {
			try {
				file2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void setupConfig() {
		getConfig().addDefault("Kopfgeld pro Kill", 100);
		getConfig().addDefault("Kopfgeld nach Tod", 0);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	private void getValues() {
		bounty_kill = getConfig().getInt("Kopfgeld pro Kill");
		bounty_after_death = getConfig().getInt("Kopfgeld nach Tod");
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

}
