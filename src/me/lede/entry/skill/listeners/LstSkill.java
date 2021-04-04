package me.lede.entry.skill.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.lede.entry.EntryFarm;
import me.lede.entry.skill.utils.UtlSkill;

public class LstSkill implements Listener {

	public LstSkill(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onJoin(PlayerJoinEvent event) {		
		Player p = event.getPlayer();
		UtlSkill.initSkill(p);	
	}
	
}
