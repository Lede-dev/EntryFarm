package me.lede.entry.skill.listeners;

import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;

import me.lede.entry.EntryFarm;
import me.lede.entry.skill.utils.UtlSkill;
import me.lede.entry.utils.Utils;

public class LstSkillHarvest implements Listener {

	public LstSkillHarvest(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onBreak(BlockBreakEvent event) {
		
		Player p = event.getPlayer();
		Block b = event.getBlock();
		Location loc = b.getLocation();
		Material mate = b.getType();
		
		if (mate == Material.CROPS || mate == Material.CARROT || mate == Material.POTATO || 
				mate == Material.BEETROOT_BLOCK) {
			Crops crops = (Crops) b.getState().getData();
			if (!crops.getState().equals(CropState.RIPE)) return;

			SpecialItem(p, loc);
		}
		
		
		else if (mate == Material.MELON_BLOCK || mate == Material.PUMPKIN) {

			SpecialItem(p, loc);
			
		}
		
	}
	
	// 호박, 수박 설치 캔슬
	@EventHandler
	private void onPlace(BlockPlaceEvent event) {
		
		Material mate = event.getBlock().getType();
		
		if (mate == Material.PUMPKIN || mate == Material.MELON_BLOCK) {
			event.setCancelled(true);	
			event.getPlayer().sendMessage(Utils.chat("&c호박과 수박은 설치할 수 없습니다."));
		}
				
	}
	
	private void SpecialItem(Player p, Location loc) {
		Double eff = UtlSkill.getHarvestingEffect(p);
		
		if (eff == null || eff <= 0.0d) return;
		
		int effi = (int) (eff * 10);
		int r = (int) (Math.random()*1000);
		
		if (r > effi) return;
		
		loc.getWorld().dropItem(loc, new ItemStack(Material.RED_ROSE, 1, (short) 2));
	}
	
}
