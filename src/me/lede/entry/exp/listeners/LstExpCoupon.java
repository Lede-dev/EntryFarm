package me.lede.entry.exp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.lede.entry.EntryFarm;
import me.lede.entry.exp.utils.UtlExp;
import me.lede.entry.exp.utils.UtlExpGUI;
import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class LstExpCoupon implements Listener {

	public LstExpCoupon(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onRightClick(PlayerInteractEvent event) {
		
		if (event.getHand() != EquipmentSlot.HAND) return;
		
		if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		
		Player p = event.getPlayer();
		
		ItemStack tool = p.getInventory().getItemInMainHand();
		
		if (tool == null || tool.getType() == Material.AIR) return;
		
		try {
			String coupon = Utils.chat(FileUtils.DefaultConfig.getString("LEVEL_ADDITIONAL_COUPON"));
			String toolName = tool.getItemMeta().getDisplayName();
			
			if (!coupon.equalsIgnoreCase(toolName)) return;
			
			p.openInventory(UtlExpGUI.getCouponGui());
		} catch (Exception ex) {
			
		}


		
	}
	
	@EventHandler
	private void onInvClick(InventoryClickEvent event) {
		
		String invName = event.getInventory().getName();

		if (invName != null && invName.equalsIgnoreCase(UtlExpGUI.CouponGuiName)) {
			event.setCancelled(true);
			Integer click = event.getRawSlot();
			Player p = (Player) event.getWhoClicked();
			ItemStack tool = p.getInventory().getItemInMainHand();
			if (click == 1) {
				if (!UtlExp.isLevelCouponAvailabel(p)) {
					p.sendMessage(Utils.chat("&c지금은 레벨 확장을 사용할 수 없습니다."));
					p.closeInventory();
					return;
				}
				p.sendMessage(Utils.chat("&a레벨 확장권을 사용하였습니다. &e/레벨"));
				tool.setAmount(tool.getAmount()-1);
				UtlExp.addCouponLevel(p);
				p.closeInventory();
				return;
			}
			
			else if (click == 3) {
				p.sendMessage(Utils.chat("&c레벨 확장권 사용을 취소하였습니다."));
				p.closeInventory();
				return;
			}
		}
		
	}
}
