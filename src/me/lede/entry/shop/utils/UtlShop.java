package me.lede.entry.shop.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class UtlShop {

	public static void sell(Player p, ItemStack item, Integer amount) {
		
	}
	
	public static void buy(Player p, ItemStack item, Integer amount) {
		
	}
	
	public static Inventory gui(String title, Integer size) {	
		Inventory inv = Bukkit.createInventory(null, size, "상점 제작 - " + title);
		
		for (int i = 0; i < size; i++) {
			inv.setItem(i, FileUtils.ShopDBConfig.getItemStack(title + ".items." + i));
		}
		
		return inv;		
	}
	
	public static ItemStack item(ItemStack item, Integer sell, Integer buy, Integer amount) {
		ItemStack clone = item.clone();
		ItemMeta meta = clone.getItemMeta();
		
		List<String> lore = new ArrayList<>();
		if (meta.getLore() != null)
			lore.addAll(meta.getLore());
		
		String sellPrice = Integer.toString(sell);
		String buyPrice = Integer.toString(buy);
		if (sellPrice == "0") sellPrice = "&c판매 불가";
		if (buyPrice == "0") buyPrice = "&c구매 불가";
		
		lore.add("");
		lore.add(Utils.chat("&f구매가격 - " + buyPrice));
		lore.add(Utils.chat("&f판매가격 - " + sellPrice));		
		lore.add(Utils.chat("&f수량 - " + amount));
		lore.add("");
		lore.add(Utils.chat("&6좌클릭&f으로 아이템을 &6구매&f하고"));
		lore.add(Utils.chat("&3우클릭&f으로 아이템을 &3판매&f할 수 있습니다"));
		lore.add("");
		
		meta.setLore(lore);
		clone.setItemMeta(meta);
		
		return clone;
	}
	
	public static Inventory itemList(Integer page) {
		Inventory inv = Bukkit.createInventory(null, 54, Utils.chat("상점 아이템 목록 - " + page));
		
		Set<String> keys = FileUtils.ShopItemDBConfig.getKeys(false);
		int count = 0;
		
		int minSlot = (page-1)*45;
		int maxSlot = page*45;
		
		for (String key: keys) {
			if (count >= minSlot && count < maxSlot) {
				ItemStack item = FileUtils.ShopItemDBConfig.getItemStack(key + ".shopitem");
				if (item == null) {
					count++;
					continue;
				}
				inv.setItem(count, item.clone());
				count++;
			}
		}
		
		inv.setItem(51, button_Help());
		inv.setItem(52, button_Prev());
		inv.setItem(53, button_Next());
		
		return inv;
	}
	
	public static ItemStack button_Prev() {
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.chat("&a이전 페이지로 이동"));
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack button_Next() {
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 2);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.chat("&c다음 페이지로 이동"));
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack button_Help() {
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.chat("&e아이템을 클릭하여 꺼낼 수 있습니다."));
		item.setItemMeta(meta);
		return item;
	}
	
	public static Inventory getShop(String shop) {
		Integer line = FileUtils.ShopDBConfig.getInt(shop+".line");
		Integer slot = line*9;
		Inventory inv = Bukkit.createInventory(null, slot, Utils.chat(shop));
		
		for (int i = 0; i<slot; i++) {
			ItemStack item = FileUtils.ShopDBConfig.getItemStack(shop + ".items." + i);
			if (item == null) item = new ItemStack(Material.AIR);
			inv.setItem(i, item.clone());
		}
		
		return inv;
	}

}














