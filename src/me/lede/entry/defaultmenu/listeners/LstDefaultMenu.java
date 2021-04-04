package me.lede.entry.defaultmenu.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.lede.entry.EntryFarm;
import me.lede.entry.defaultmenu.utils.CreateInv;
import me.lede.entry.defaultmenu.utils.DefaultMenuCustomLoc;
import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class LstDefaultMenu implements Listener {

	private static String saveComplete = "&a메뉴 저장 완료";
	
	public LstDefaultMenu(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	private void onInvClick(InventoryClickEvent event) {

		if (!event.getInventory().getName().equals(CreateInv.Title)) return;

		event.setCancelled(true);	
		
		if (!(event.getClick().equals(ClickType.LEFT) || event.getClick().equals(ClickType.RIGHT))) return;
		
		int slot = event.getRawSlot();
		Player p = (Player) event.getWhoClicked();
		
		try {	
			Bukkit.dispatchCommand(p, FileUtils.MainMenuDBConfig.getString("Commands." + slot));		
		}
		catch (Exception ex) { }
		
	}
	
	@EventHandler
	private void onDrag(InventoryDragEvent event) {
		if (!event.getView().getTitle().equals(CreateInv.Title)) return;	
		event.setCancelled(true);
	}
	
	@EventHandler
	private void onClose(InventoryCloseEvent event) {
		
		if (!event.getInventory().getName().equals(CreateInv.EditorTitle)) return;
		
		int size = event.getInventory().getSize();
		
		for (int i=0; i<size; i++) {
			ItemStack item = event.getInventory().getItem(i);
			if (item == null) item = new ItemStack(Material.AIR);
			FileUtils.MainMenuDBConfig.set("Items." + i, item.clone());
		}
		
		FileUtils.save("MainMenuDB");
		event.getPlayer().sendMessage(Utils.chat(saveComplete));
		
	}
	
	@EventHandler
	private void openInv(PlayerSwapHandItemsEvent event) {
		
		Player p = event.getPlayer();
		
		if (p.isSneaking()) {
			event.setCancelled(true);
			p.openInventory(CreateInv.create());
		}
		
	}
	
	@EventHandler
	private void openInv(PlayerInteractEvent event) {
		
		if(event.getHand() != EquipmentSlot.HAND) return;
		
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		
		Location loc = event.getClickedBlock().getLocation();	
		Player p = event.getPlayer();
		
		try {		
			boolean openInv = FileUtils.MainMenuDBConfig.getBoolean("NpcLocation." + DefaultMenuCustomLoc.create(loc));		
			if (openInv) {
				p.openInventory(CreateInv.create());
			}		
		}
		catch (Exception ex) {}
		
		
		
	}
	
	
}












