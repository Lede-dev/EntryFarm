package me.lede.entry.shop.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.lede.entry.EntryFarm;
import me.lede.entry.shop.utils.UtlShop;
import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class CmdShopCreate implements CommandExecutor {

	public CmdShopCreate(EntryFarm plugin) {
		plugin.getCommand("ent.shop.create").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {

		if (!(sender instanceof Player)) return false;

		Player p = (Player) sender;
		
		if (arg.length == 0) {
			
			p.sendMessage(Utils.chat("/ent.shop.create create <상점이름> : 상점을 생성합니다"));
			p.sendMessage(Utils.chat("/ent.shop.create remove <상점이름> : 생성한 상점을 삭제합니다."));
			p.sendMessage(Utils.chat("/ent.shop.create line <상점이름> : 상점의 줄을 설정합니다."));
			p.sendMessage(Utils.chat("/ent.shop.create gui <상점이름>: 상점의 GUI를 배치합니다."));
			p.sendMessage(Utils.chat("/ent.shop.create background : 손에 든 아이템을 이름과 로어가 존재하지 않는 배경 아이템으로 설정합니다."));
			p.sendMessage(Utils.chat("/ent.shop.create item <구매가격> <판매가격> <수량> : 손에 든 아이템을 상점 아이템으로 추가합니다."));
			p.sendMessage(Utils.chat("/ent.shop.create npc <상점이름> <NPC이름> : 상점NPC를 설정합니다."));
			p.sendMessage(Utils.chat("/ent.shop.craete removenpc <NPC이름> : 상점NPC를 제거합니다."));
			p.sendMessage(Utils.chat("/ent.shop.create itemlist : 이전에 등록해둔 아이템의 목록을 출력하여 아이템을 가져올 수 있습니다."));
			p.sendMessage(Utils.chat("/ent.shop.create shoplist : 상점 목록을 출력합니다."));
			
			return false;
		}
		
		if (arg[0].equalsIgnoreCase("create")) {
			if (arg.length != 2) {
				p.sendMessage(Utils.chat("/ent.shop.create create <상점이름>"));
				return false;
			}
			FileUtils.ShopDBConfig.set(arg[1]+".state", true);
			FileUtils.save("ShopDB");
			p.sendMessage(Utils.chat("&f상점 &6" + arg[1] + "&f을(를) 생성하였습니다."));
			return false;
		}
		
		if (arg[0].equalsIgnoreCase("remove")) {
			if (arg.length != 2) {
				p.sendMessage(Utils.chat("/ent.shop.create create <상점이름>"));
				return false;
			}
			if (FileUtils.ShopDBConfig.getBoolean(arg[1] + ".state") != true) {
				p.sendMessage(Utils.chat("&c존재하지 않는 상점입니다."));
				return false;
			}
			FileUtils.ShopDBConfig.set(arg[1], null);
			FileUtils.save("ShopDB");
			p.sendMessage(Utils.chat("&f상점 &6" + arg[1] + "&f을(를) 삭제하였습니다."));
			return false;
		}
		
		
		if (arg[0].equalsIgnoreCase("line")) {
			if (arg.length != 3) {
				p.sendMessage(Utils.chat("/ent.shop.create line <상점이름> <상점 라인>"));
				return false;
			}
			if (FileUtils.ShopDBConfig.getBoolean(arg[1] + ".state") != true) {
				p.sendMessage(Utils.chat("&c존재하지 않는 상점입니다."));
				return false;
			}
			Integer line = Integer.parseInt(arg[2]);
			try {
				if (line > 0 && line < 7) {
					FileUtils.ShopDBConfig.set(arg[1]+".line", line);
					FileUtils.save("ShopDB");
					p.sendMessage(Utils.chat("상점 &6" + arg[1] + "&f의 라인을 &6" + line + "&f(으)로 설정하였습니다."));
					return false;
				}
				p.sendMessage(Utils.chat("&c1~6 사이의 정수를 입력해주세요."));
				return false;
				
			}catch(Exception ex) {
				p.sendMessage(Utils.chat("&c1~6 사이의 정수를 입력해주세요."));
				return false;
			}

		}
				
		if (arg[0].equalsIgnoreCase("gui")) {
			if (arg.length != 2) {
				p.sendMessage(Utils.chat("/ent.shop.create gui <상점이름>"));
				return false;
			}
			if (FileUtils.ShopDBConfig.getBoolean(arg[1] + ".state") != true) {
				p.sendMessage(Utils.chat("&c존재하지 않는 상점입니다."));
				return false;
			}
			int line = FileUtils.ShopDBConfig.getInt(arg[1] + ".line");
			if (line < 1 || line > 6) {
				p.sendMessage(Utils.chat("&c상점의 라인이 정상적으로 설정되어있지 않습니다."));
				return false;
			}
			p.openInventory(UtlShop.gui(arg[1], line*9));
			return false;
		}
		
		if (arg[0].equalsIgnoreCase("background")) {
			ItemStack tool = p.getInventory().getItemInMainHand();
			if (tool == null || tool.getType() == Material.AIR) {
				p.sendMessage(Utils.chat("&c손에 들고있는 아이템이 없습니다."));
				return false;
			}
			ItemMeta meta = tool.getItemMeta();
			meta.setDisplayName(" ");
			tool.setItemMeta(meta);
			p.sendMessage(Utils.chat("상점 배경 아이템을 설정하였습니다."));
			return false;
		}
		
		if (arg[0].equalsIgnoreCase("npc")) {
			if (arg.length != 3) {
				p.sendMessage(Utils.chat("/ent.shop.create npc <상점이름> <NPC이름>"));
				return false;
			}
			if (FileUtils.ShopDBConfig.getBoolean(arg[1] + ".state") != true) {
				p.sendMessage(Utils.chat("&c존재하지 않는 상점입니다."));
				return false;
			}
			FileUtils.ShopNpcDBConfig.set(arg[2]+".state", true);
			FileUtils.ShopNpcDBConfig.set(arg[2]+".shop", arg[1]);
			FileUtils.save("ShopNpcDB");
			p.sendMessage(Utils.chat("NPC &6" + arg[2] + "&f의 상점을 &6" + arg[1] + "&f(으)로 설정하였습니다."));
			return false;
		}
		
		if (arg[0].equalsIgnoreCase("removenpc")) {
			if (arg.length != 2) {
				p.sendMessage(Utils.chat("/ent.shop.create removenpc <NPC이름>"));
				return false;
			}
			if (FileUtils.ShopNpcDBConfig.getBoolean(arg[1] + ".state") != true) {
				p.sendMessage(Utils.chat("&c존재하지 않는 상점NPC입니다."));
				return false;
			}
			FileUtils.ShopNpcDBConfig.set(arg[1], null);
			FileUtils.save("ShopNpcDB");
			p.sendMessage(Utils.chat("상점NPC &6" + arg[2] + "&f(을)를 삭제하였습니다."));
			return false;
		}
		
		if (arg[0].equalsIgnoreCase("item")) {
			if (arg.length != 4) {
				p.sendMessage(Utils.chat("/ent.shop.create item <구매가격> <판매가격> <수량>"));
				return false;
			}
			ItemStack tool = p.getInventory().getItemInMainHand();
			if (tool == null || tool.getType() == Material.AIR) {
				p.sendMessage(Utils.chat("&c손에 들고있는 아이템이 없습니다."));
				return false;
			}
			Integer sell = Integer.parseInt(arg[2]);
			Integer buy = Integer.parseInt(arg[1]);
			Integer amount = Integer.parseInt(arg[3]);
			
			try {
				if (sell < 0) {
					p.sendMessage(Utils.chat("&c아이템의 판매 가격은 0 이하로 설정할 수 없습니다."));
					return false;
				}
				
			}catch (Exception ex) {
				p.sendMessage(Utils.chat("&c아이템의 판매 가격은 0 이하로 설정할 수 없습니다."));
				return false;
			}
			
			try {
				
				if (buy < 0) {
					p.sendMessage(Utils.chat("&c아이템의 구매 가격은 0 이하로 설정할 수 없습니다."));
					return false;
				}
				if (buy == 0) {
					
				}
				
			} catch (Exception ex) {
				p.sendMessage(Utils.chat("&c아이템의 구매 가격은 0 이하로 설정할 수 없습니다."));
				return false;
			}
			
			try {
				if (amount < 1) {
					p.sendMessage(Utils.chat("&c아이템의 수량은 0개 이하로 설정할 수 없습니다."));
					return false;
				}
				
			}catch(Exception ex) {
				p.sendMessage(Utils.chat("&c아이템의 수량은 0개 이하로 설정할 수 없습니다."));
				return false;
			}
			
			ItemStack clone = UtlShop.item(tool, sell, buy, amount);
			FileUtils.ShopItemDBConfig.set(clone + ".sell", sell);
			FileUtils.ShopItemDBConfig.set(clone + ".buy", buy);
			FileUtils.ShopItemDBConfig.set(clone + ".amount", amount);
			FileUtils.ShopItemDBConfig.set(clone + ".shopitem", clone);
			FileUtils.ShopItemDBConfig.set(clone + ".item", tool);
			FileUtils.save("ShopItemDB");
			p.sendMessage(Utils.chat("&f아이템 생성을 완료하였습니다. /ent.shop.create itemlist 명령어로 생성된 아이템을 확인할 수 있습니다."));
			return false;
		}
		
		if (arg[0].equalsIgnoreCase("itemlist")) {
			p.openInventory(UtlShop.itemList(1));
			return false;
		}
		
		if (arg[0].equalsIgnoreCase("shoplist")) {
			p.openInventory(UtlShop.itemList(1));
			return false;
		}
		
		p.sendMessage(Utils.chat("/ent.shop.create create <상점이름> : 상점을 생성합니다"));
		p.sendMessage(Utils.chat("/ent.shop.create remove <상점이름> : 생성한 상점을 삭제합니다."));
		p.sendMessage(Utils.chat("/ent.shop.create line <상점이름> : 상점의 줄을 설정합니다."));
		p.sendMessage(Utils.chat("/ent.shop.create gui <상점이름>: 상점의 GUI를 배치합니다."));
		p.sendMessage(Utils.chat("/ent.shop.create background : 손에 든 아이템을 이름과 로어가 존재하지 않는 배경 아이템으로 설정합니다."));
		p.sendMessage(Utils.chat("/ent.shop.create item <구매가격> <판매가격> <수량> : 손에 든 아이템을 상점 아이템으로 추가합니다."));
		p.sendMessage(Utils.chat("/ent.shop.create npc <상점이름> <NPC이름> : 상점NPC를 설정합니다."));
		p.sendMessage(Utils.chat("/ent.shop.craete removenpc <NPC이름> : 상점NPC를 제거합니다."));
		p.sendMessage(Utils.chat("/ent.shop.create itemlist : 이전에 등록해둔 아이템의 목록을 출력하여 아이템을 가져올 수 있습니다."));
		p.sendMessage(Utils.chat("/ent.shop.create shoplist : 상점 목록을 출력합니다."));
		return false;
	}
	
	
	
}

















