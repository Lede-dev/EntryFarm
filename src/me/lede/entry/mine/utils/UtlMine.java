package me.lede.entry.mine.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.lede.entry.utils.Utils;

public class UtlMine {
	
	public static final String MINE_DEFAULT = Utils.chat("&e광물 생성기");

	public static Material getChance(String type) {
		if (type == "default") return getDefaultMineChance();
		
		
		return getDefaultMineChance();
	}
	
	public static Material getDefaultMineChance() {
		int r = (int) (Math.random()*10000);
		
		if (r < 3000) return Material.COAL_ORE;	
		if (r < 4000) return Material.IRON_ORE;
		if (r < 5000) return Material.GOLD_ORE;
		if (r < 5500) return Material.DIAMOND_ORE;
		if (r < 6000) return Material.EMERALD_ORE;
		return Material.STONE;
	}
	
	public static ItemStack getMine(String type) {
		if (type == "default") return getDefaultMineItem();
		return getDefaultMineItem();
	}
	
	public static ItemStack getDefaultMineItem() {
		ItemStack item = new ItemStack(Material.FENCE);
		ItemMeta meta = item.getItemMeta();
		
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		meta.setDisplayName(MINE_DEFAULT);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
}
