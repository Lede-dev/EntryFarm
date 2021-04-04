package me.lede.entry.shop.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.lede.entry.EntryFarm;
import me.lede.entry.shop.utils.UtlShop;
import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class LstShop implements Listener {

	public LstShop(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onInventoryClose(InventoryCloseEvent event) {
		
		Inventory inv = event.getInventory();
		String name = inv.getName();
		if (!name.contains("상점 제작 - ")) return;
		
		String shop = name.replace("상점 제작 - ", "");
		Integer slot = FileUtils.ShopDBConfig.getInt(shop + ".line") * 9;
		
		for (int i = 0; i < slot; i++) {
			ItemStack item = inv.getItem(i);
			if (item == null) item = new ItemStack(Material.AIR);
			FileUtils.ShopDBConfig.set(shop + ".items." + i, item.clone());
		}
		FileUtils.save("ShopDB");
		event.getPlayer().sendMessage(Utils.chat("&f상점 &6" + shop + "&f의 GUI 설정을 완료하였습니다."));
		
	}
	
	@EventHandler
	private void onItemListClick(InventoryClickEvent event) {
		
		Inventory inv = event.getInventory();
		String name = inv.getName();
		
		if (!name.contains("상점 아이템 목록 - ")) return;
		
		event.setCancelled(true);
		
		Player p = (Player) event.getWhoClicked();
		ItemStack click = event.getCurrentItem();
		
		if (click == null || click.getType() == Material.AIR) return;
		
		if (click.equals(UtlShop.button_Help())) return;
		
		String pageString = name.replace("상점 아이템 목록 - ", "");
		Integer page = Integer.parseInt(pageString);
		
		if (click.equals(UtlShop.button_Prev())) {
			if (--page < 1) return;
			p.openInventory(UtlShop.itemList(page));
			return;
		}
		
		if (click.equals(UtlShop.button_Next())) {
			p.openInventory(UtlShop.itemList(page+1));
			return;
		}
		
		p.getInventory().addItem(click);
		String itemname = click.getItemMeta().getDisplayName();
		if (itemname == null) itemname = click.getType().toString();
		p.sendMessage(Utils.chat("&a아이템 &6" + itemname + "&f을(를) 꺼냈습니다."));
				
	}
	
	@EventHandler
	private void onItemListDrag(InventoryDragEvent event) {
		String name = event.getInventory().getName();
		if (name == null || !name.contains("상점 아이템 목록 - ")) return;
		event.setCancelled(true);
	}
	
}
