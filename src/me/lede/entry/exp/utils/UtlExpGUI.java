package me.lede.entry.exp.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;


public class UtlExpGUI {
	
	public static final String CouponGuiName = Utils.chat("&0레벨 확장권을 사용할까요?");

	public static Inventory getCouponGui() {
		
		ItemStack accept = createButton(Material.GRASS, "&a✔");
		ItemStack deny = createButton(Material.BARRIER, "&cX");
			
		Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, CouponGuiName);
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
	
	public static ItemStack getLevelCoupon() {
		ItemStack item = new ItemStack(Material.PAPER, 1);
		ItemMeta meta = item.getItemMeta();	
		String name = FileUtils.DefaultConfig.getString("LEVEL_ADDITIONAL_COUPON");
		meta.setDisplayName(Utils.chat(name));
		item.setItemMeta(meta);
		return item;
	}
}
