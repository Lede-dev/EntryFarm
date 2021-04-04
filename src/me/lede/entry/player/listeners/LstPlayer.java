package me.lede.entry.player.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.lede.entry.EntryFarm;
import me.lede.entry.player.utils.UtlPlayer;

public class LstPlayer implements Listener {

	public LstPlayer(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		
		UtlPlayer.registPlayer(event.getPlayer());
		
	}
	
}
