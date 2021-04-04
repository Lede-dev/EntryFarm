package me.lede.entry.skill.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.lede.entry.utils.Utils;

public class UtlSkillTicketGui {

	public static final String FlyTicketGuiName = Utils.chat("&0플라이 이용권을 사용할까요?");

	public static Inventory getTicketGui() {
		
		ItemStack accept = createButton(Material.GRASS, "&a✔");
		ItemStack deny = createButton(Material.BARRIER, "&cX");
			
		Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, FlyTicketGuiName);
		inv.setItem(1, accept);
		inv.setItem(3, deny);
		return inv;
	}
	
	public static ItemStack createButton(Material material, String name) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();		
		meta.setDisplayName(Utils.chat(name));
		item.setItemMeta(meta);
		return item;
	}
	
}
