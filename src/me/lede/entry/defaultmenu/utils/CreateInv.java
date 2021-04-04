package me.lede.entry.defaultmenu.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.lede.entry.utils.FileUtils;

public class CreateInv {

	public static String Title = "【 메뉴 】";
	public static String EditorTitle = "【 메뉴설정 】";
	
	public static Inventory create() {
		try {
			
			int size = FileUtils.MainMenuDBConfig.getInt("Size");
	
			Inventory inv = Bukkit.createInventory(null, size, Title);
			
			for (int i=0; i<size; i++) {
				ItemStack item = FileUtils.MainMenuDBConfig.getItemStack("Items." + i);
				if (item == null) item = new ItemStack(Material.AIR);
				inv.setItem(i, item.clone());
			}
			
			return inv;
		}
		catch (Exception ex) {return Bukkit.createInventory(null, 9, Title);}
		
	}
	
	public static Inventory editor() {
		try {
			
			int size = FileUtils.MainMenuDBConfig.getInt("Size");
	
			Inventory inv = Bukkit.createInventory(null, size, EditorTitle);
			
			for (int i=0; i<size; i++) {
				ItemStack item = FileUtils.MainMenuDBConfig.getItemStack("Items." + i);
				if (item == null) item = new ItemStack(Material.AIR);
				inv.setItem(i, item.clone());
			}
			
			return inv;
		}
		catch (Exception ex) {return Bukkit.createInventory(null, 9, EditorTitle);}
		
	}
	
}
