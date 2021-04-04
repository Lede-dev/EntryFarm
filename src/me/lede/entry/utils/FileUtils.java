package me.lede.entry.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.lede.entry.EntryFarm;

public class FileUtils {

	public static File NoChat;
	public static FileConfiguration NoChatConfig;

	//=======================================================
	
	
	public static File MainMenuDB;
	public static FileConfiguration MainMenuDBConfig;
	
	public static File MineDB;
	public static FileConfiguration MineDBConfig;
	
	public static File UserDB;
	public static FileConfiguration UserDBConfig;
	
	public static File Default;
	public static FileConfiguration DefaultConfig;
	
	public static File SystemDB;
	public static FileConfiguration SystemDBConfig;
	
	public static File ShopDB;
	public static FileConfiguration ShopDBConfig;
	
	public static File ShopItemDB;
	public static FileConfiguration ShopItemDBConfig;
	
	public static File ShopNpcDB;
	public static FileConfiguration ShopNpcDBConfig;

	//=======================================================
	
	public FileUtils(EntryFarm plugin) {	
		loadConfig(plugin);
		loadDB(plugin);
		createConfigFile(plugin);
		createDBFile(plugin);
	}
	
	public static void saveDatabase(FileConfiguration config, File file) {
		try {
			config.save(file);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void loadConfig(EntryFarm plugin) {
		Default = new File(plugin.getDataFolder()+"/config.yml");
		DefaultConfig = YamlConfiguration.loadConfiguration(Default);
	}
	
	private void loadDB(EntryFarm plugin) {
		MainMenuDB = new File(plugin.getDataFolder()+"/DB/Menu/MainMenuDB.yml");
		MainMenuDBConfig = YamlConfiguration.loadConfiguration(MainMenuDB);
		
		MineDB = new File(plugin.getDataFolder()+"/DB/Mine/MineDB.yml");
		MineDBConfig = YamlConfiguration.loadConfiguration(MineDB);
		
		UserDB = new File(plugin.getDataFolder()+"/DB/User/UserDB.yml");
		UserDBConfig = YamlConfiguration.loadConfiguration(UserDB);
		
		SystemDB = new File(plugin.getDataFolder()+"/DB/System/SystemDB.yml");
		SystemDBConfig = YamlConfiguration.loadConfiguration(SystemDB);
		
		ShopDB = new File(plugin.getDataFolder()+"/DB/Shop/ShopDB.yml");
		ShopDBConfig = YamlConfiguration.loadConfiguration(ShopDB);
		
		ShopItemDB = new File(plugin.getDataFolder()+"/DB/Shop/ShopItemDB.yml");
		ShopItemDBConfig = YamlConfiguration.loadConfiguration(ShopItemDB);
		
		ShopNpcDB = new File(plugin.getDataFolder()+"/DB/Shop/ShopNpcDB.yml");
		ShopNpcDBConfig = YamlConfiguration.loadConfiguration(ShopNpcDB);
		
	}

	private void createConfigFile(EntryFarm plugin) {
		
	}
	
	private void createDBFile(EntryFarm plugin) {
		if (!MainMenuDB.exists()) plugin.saveResource("DB/Menu/MainMenuDB.yml", false);
		if (!MineDB.exists()) plugin.saveResource("DB/Mine/MineDB.yml", false);
		if (!UserDB.exists()) plugin.saveResource("DB/User/UserDB.yml", false); 
		if (!SystemDB.exists()) plugin.saveResource("DB/System/SystemDB.yml", false);
		if (!ShopDB.exists()) plugin.saveResource("DB/Shop/ShopDB.yml", false);
		if (!ShopItemDB.exists()) plugin.saveResource("DB/Shop/ShopItemDB.yml", false);
		if (!ShopNpcDB.exists()) plugin.saveResource("DB/Shop/ShopNpcDB.yml", false);
	}
	
	
	//=============================================================
	
	public static void save(String filename) {
		
		if (filename == "MainMenuDB") {
			saveDatabase(MainMenuDBConfig, MainMenuDB);
		}
		
		else if (filename == "MineDB") {
			saveDatabase(MineDBConfig, MineDB);
		}
		
		else if (filename == "UserDB") {
			saveDatabase(UserDBConfig, UserDB);
		}
		
		else if (filename == "Config") {
			saveDatabase(DefaultConfig, Default);
		}
		
		else if (filename == "SystemDB") {
			saveDatabase(SystemDBConfig, SystemDB);
		}
		
		else if (filename == "ShopDB") {
			saveDatabase(ShopDBConfig, ShopDB);
		}
		
		else if (filename == "ShopItemDB") {
			saveDatabase(ShopItemDBConfig, ShopItemDB);;
		}
		
		else if (filename == "ShopNpcDB") {
			saveDatabase(ShopNpcDBConfig, ShopNpcDB);;
		}
	}
	
	
}
