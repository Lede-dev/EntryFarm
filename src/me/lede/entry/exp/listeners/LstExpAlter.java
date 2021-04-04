package me.lede.entry.exp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.lede.entry.EntryFarm;
import me.lede.entry.exp.utils.UtlExp;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class LstExpAlter implements Listener {

	public LstExpAlter(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onNpcClick(NPCRightClickEvent event) {
		if (event.getNPC().getName().equalsIgnoreCase("제단")) {
			event.getClicker().openInventory(UtlExp.getAltar());
		}
	}
	
	
	
}
