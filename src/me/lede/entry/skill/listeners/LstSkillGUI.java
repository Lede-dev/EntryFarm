package me.lede.entry.skill.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import me.lede.entry.EntryFarm;
import me.lede.entry.skill.utils.UtlSkill;
import me.lede.entry.utils.Utils;

public class LstSkillGUI implements Listener {
	
	public static final String UpgradeError = Utils.chat("&c스킬 포인트가 부족합니다.");
	public static final String UpgradeError2 = Utils.chat("&c해당 스킬을 더이상 강화할 수 없습니다.");

	public LstSkillGUI(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onInvClick(InventoryClickEvent event) {
		
		
		Inventory inv = event.getInventory();
		String name = inv.getName();
		
		if (!name.equalsIgnoreCase(UtlSkill.skillGuiName)) return;
		
		Player p = (Player) event.getWhoClicked();
		event.setCancelled(true);
		
		Integer slot = event.getRawSlot();
		
		if (slot == 0) {
			if (!UtlSkill.isSkillUpgradeable(p)) {
				p.sendMessage(UpgradeError);
				return;
			}
			if (!UtlSkill.isMiningSkillMax(p)) {
				p.sendMessage(UpgradeError2);
				return;
			}
			UtlSkill.upgradeMiningSkill(p);
			p.openInventory(UtlSkill.getSkillGUI(p.getUniqueId()));
		}
		
		else if (slot == 1) {
			if (!UtlSkill.isSkillUpgradeable(p)) {
				p.sendMessage(UpgradeError);
				return;
			}
			if (!UtlSkill.isHarvestingSkillMax(p)) {
				p.sendMessage(UpgradeError2);
				return;
			}
			UtlSkill.upgradeHarvestingSkill(p);
			p.openInventory(UtlSkill.getSkillGUI(p.getUniqueId()));
		}
		
		else if (slot == 2) {
			if (!UtlSkill.isSkillUpgradeable(p)) {
				p.sendMessage(UpgradeError);
				return;
			}
			if (!UtlSkill.isJumpSkillMax(p)) {
				p.sendMessage(UpgradeError2);
				return;
			}
			UtlSkill.upgradeJumpSkill(p);
			p.openInventory(UtlSkill.getSkillGUI(p.getUniqueId()));
		}
		
		else if (slot == 3) {
			if (!UtlSkill.isSkillUpgradeable(p)) {
				p.sendMessage(UpgradeError);
				return;
			}
			if (!UtlSkill.isAffinitySkillMax(p)) {
				p.sendMessage(UpgradeError2);
				return;
			}
			UtlSkill.upgradeAffinitySkill(p);
			p.openInventory(UtlSkill.getSkillGUI(p.getUniqueId()));
		}
		
		else if (slot == 4) {
			
		}
		
		
	}
	
	@EventHandler
	private void onInvDrag(InventoryDragEvent event) {
		if (event.getInventory().getName() != UtlSkill.skillGuiName) return;
		event.setCancelled(true);
	}
}
