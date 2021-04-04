package me.lede.entry.skill.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.lede.entry.EntryFarm;
import me.lede.entry.skill.utils.UtlSkill;
import me.lede.entry.skill.utils.UtlSkillTicketGui;
import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class LstSkillFly implements Listener {

	public LstSkillFly(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	/*
	@EventHandler
	private void onFlyToggle(PlayerToggleFlightEvent event) {
		
		Player p = event.getPlayer();
		
		if (p.getGameMode() == GameMode.CREATIVE) return;
		
		Boolean state = FileUtils.UserDBConfig.getBoolean(p.getUniqueId().toString() + ".Skill.Fly");
		if (state != true) {	
			event.setCancelled(true);
			p.setFlying(false);
			p.sendMessage(Utils.chat("&c플라이 이용권을 사용하여 플라이 스킬을 활성화할 수 있습니다."));
			return;
		}
		
	}
	*/
	
	@EventHandler
	private void onRightClick(PlayerInteractEvent event) {
		
		if (event.getHand() != EquipmentSlot.HAND) return;
		
		Player p = event.getPlayer();
		
		ItemStack tool = p.getInventory().getItemInMainHand();
		
		if (tool == null || tool.getType() == Material.AIR) return;
		
		String name = tool.getItemMeta().getDisplayName();
		
		if (name == null || !name.equalsIgnoreCase(UtlSkill.Ticket_Fly)) return;
		
		Boolean state = FileUtils.UserDBConfig.getBoolean(p.getUniqueId().toString() + ".Skill.Fly");
		if (state) {
			p.sendMessage(Utils.chat("&c플라이 이용권은 1개 이상 사용할 수 없습니다."));
			return;
		}
		
		p.openInventory(UtlSkillTicketGui.getTicketGui());
		
	}
	
	@EventHandler
	private void onInvClick(InventoryClickEvent event) {
		if (!event.getInventory().getName().equalsIgnoreCase(UtlSkillTicketGui.FlyTicketGuiName)) return;
		
		event.setCancelled(true);
		Player p = (Player) event.getWhoClicked();
		Integer click = event.getRawSlot();
		ItemStack tool = p.getInventory().getItemInMainHand();
		
		if (click == 1) {
			p.sendMessage(Utils.chat("&a플라이 이용권을 사용하였습니다."));
			tool.setAmount(tool.getAmount()-1);
			FileUtils.UserDBConfig.set(p.getUniqueId().toString() + ".Skill.Fly", true);
			FileUtils.save("UserDB");
			p.closeInventory();
			return;
		}
		
		else if (click == 3) {
			p.sendMessage(Utils.chat("&c플라이 이용권 사용을 취소하였습니다."));
			p.closeInventory();
			return;
		}
		
	}
	
	@EventHandler
	private void onInvDrag(InventoryDragEvent event) {
		if (event.getInventory().getName().equalsIgnoreCase(UtlSkillTicketGui.FlyTicketGuiName)) {
			event.setCancelled(true);
		}
	}
}















