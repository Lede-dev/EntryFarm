package me.lede.entry.shop.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import me.lede.entry.EntryFarm;
import me.lede.entry.shop.utils.UtlShop;
import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class LstShopTrade implements Listener {

	public LstShopTrade(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onNpcClick(NPCRightClickEvent event) {
		
		String npc = event.getNPC().getName();
		Boolean state = FileUtils.ShopNpcDBConfig.getBoolean(npc + ".state");
		
		if (state != true) return;
		
		String shop = FileUtils.ShopNpcDBConfig.getString(npc + ".shop");
		
		if (shop == null) return;
		
		event.getClicker().openInventory(UtlShop.getShop(shop));
		
	}
	
	@EventHandler
	private void onInvClick(InventoryClickEvent event) {
		
		Inventory inv = event.getInventory();
		String name = inv.getName();
		Boolean state = FileUtils.ShopDBConfig.getBoolean(name + ".state");
		if (state != true) return;
		
		event.setCancelled(true);
		
		ItemStack click = event.getCurrentItem();
		if (click == null || click.getType() == Material.AIR) return;
		
		ItemStack it = FileUtils.ShopItemDBConfig.getItemStack(click + ".item");
		if (it == null) return;
		ItemStack item = it.clone();
		
		Player p = (Player) event.getWhoClicked();
		CMIUser cp = CMI.getInstance().getPlayerManager().getUser(p);
		
		String itemname = item.getItemMeta().getDisplayName();
		if (itemname == null) itemname = item.getType().toString();
		
		if (event.getClick().equals(ClickType.LEFT)) {
			Integer price = FileUtils.ShopItemDBConfig.getInt(click + ".buy");
			Integer amount = FileUtils.ShopItemDBConfig.getInt(click + ".amount");
			if (price <= 0 || amount <= 0) {
				p.sendMessage(Utils.chat("&c구매할 수 없는 아이템입니다."));
				return;
			}
			if (cp.getBalance() < price) {
				p.sendMessage(Utils.chat("&c잔액이 부족하여 아이템을 구매할 수 없습니다."));
				return;
			}
			if (p.getInventory().firstEmpty() == -1) {
				p.sendMessage(Utils.chat("&c인벤토리가 가득 차 아이템을 구매할 수 없습니다."));
				return;
			}
			item.setAmount(amount);
			p.getInventory().addItem(item);
			cp.withdraw((double)price);
			p.sendMessage(Utils.chat(
					"&6======================================" +
					"\n " +
					"\n   &e[ &6구매 정보 &e]" +
					"\n " +
					"\n   &eᗚ &f아이템 : &e" + itemname +
					"\n   &eᗚ &f개수 : &e" + amount +
					"\n   &eᗚ &f가격 : &e" + price +
					"\n   &eᗚ &f잔액 : &e" + cp.getBalance() +
					"\n " +
					"\n&6======================================"));
			return;
		}
		
		else if (event.getClick().equals(ClickType.RIGHT)) {
			Integer price = FileUtils.ShopItemDBConfig.getInt(click + ".sell");
			Integer amount = FileUtils.ShopItemDBConfig.getInt(click + ".amount");
			if (price <= 0 || amount <= 0) {
				p.sendMessage(Utils.chat("&c판매할 수 없는 아이템입니다."));
				return;
			}
			for (int i = 0; i<36; i++) {
				try {
					ItemStack rItem = p.getInventory().getItem(i);
					ItemStack invItem = rItem.clone();
					ItemStack cloneItem = item.clone();
					int invItemAmount = invItem.getAmount();
					invItem.setAmount(1);
					cloneItem.setAmount(1);
					if (cloneItem.equals(invItem)) {
						if (invItemAmount < amount) continue;
						rItem.setAmount(invItemAmount-amount);
						Integer bonus = 0;
						Integer chance = (int) (FileUtils.UserDBConfig.getDouble(p.getUniqueId().toString() + ".Skill.Affinity") * 10);
						Integer r = (int) (Math.random()*1000);
						if (r < chance) bonus = (int) (price*0.1);
						Integer result = price + bonus;
						cp.deposit((double)result);
						p.sendMessage(Utils.chat(
								"&6======================================" +
								"\n " +
								"\n   &e[ &6판매 정보 &e]" +
								"\n " +
								"\n   &eᗚ &f아이템 : &e" + itemname +
								"\n   &eᗚ &f개수 : &e" + amount +
								"\n   &eᗚ &f가격 : &e" + result + " ( &b" + price + " &e+ &a" + bonus + " &e)" +
								"\n   &eᗚ &f잔액 : &e" + cp.getBalance() +
								"\n " +
								"\n&6======================================"));
						return;
					}
				}catch(Exception ex) {
					
				}
			}
			p.sendMessage(Utils.chat("&c보유중인 아이템의 개수가 부족하여 판매할 수 없습니다."));
			return;
		}
		
	}
	
	@EventHandler
	private void onInvDrag(InventoryDragEvent event) {
		Inventory inv = event.getInventory();
		String name = inv.getName();
		Boolean state = FileUtils.ShopDBConfig.getBoolean(name + ".state");
		if (state != true) return;		
		event.setCancelled(true);
		
	}
	
}
