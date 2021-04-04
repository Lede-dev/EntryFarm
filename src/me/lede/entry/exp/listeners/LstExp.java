package me.lede.entry.exp.listeners;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.lede.entry.EntryFarm;
import me.lede.entry.exp.utils.UtlExp;
import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class LstExp implements Listener {

	public LstExp(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onInvClose(InventoryCloseEvent event) {
		
		Inventory inv = event.getInventory();
		String name = inv.getName();
		
		if (!name.equalsIgnoreCase(UtlExp.altarGuiName)) return;
		
		Player p = (Player) event.getPlayer();
		int exp = 0;
		
		for (ItemStack item : inv.getContents()) {
			if (item == null || item.getType().equals(Material.AIR)) continue;
			Integer e = UtlExp.getCropExp(item);
			if (e == null || e == 0) {
				p.getWorld().dropItem(p.getLocation(), item);
				continue;
			}
			exp += UtlExp.getCropExp(item);
		}
		
		if (exp <= 0) return;
		
		Bukkit.getLogger().log(Level.INFO, Utils.chat("&e" + p.getUniqueId().toString() + " : Gained &a" + exp + " &eExp"));
		p.sendMessage(Utils.chat(FileUtils.DefaultConfig.getString("MSG_ADDEXP").replace("%exp%", exp+"")));
		UtlExp.addExp(p, exp);
		
	}
	
	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		UtlExp.initExp(event.getPlayer());	
	}
}



















