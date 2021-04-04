package me.lede.entry.player.utils;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class UtlPlayer {

	public static void registPlayer(Player p) {
		String name = p.getName();
		String uuid = p.getUniqueId().toString();
		FileUtils.SystemDBConfig.set("UserList." + name, uuid);
		FileUtils.save("SystemDB");
		Bukkit.getLogger().log(Level.INFO, Utils.chat("&e" + uuid + " : Server Connected with name [ &a"+ name + " &e]"));
	}
	
	public static OfflinePlayer getOfflinePlayerByName(String name) {

		UUID uuid = getUUIDByName(name);
		OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
		
		return p;
	}
	
	public static UUID getUUIDByName(String name) {
		return UUID.fromString(FileUtils.SystemDBConfig.getString("UserList." + name));
	}
	
}
