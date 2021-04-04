package me.lede.entry.shop;

import me.lede.entry.EntryFarm;
import me.lede.entry.shop.commands.CmdShopCreate;
import me.lede.entry.shop.listeners.LstShop;
import me.lede.entry.shop.listeners.LstShopTrade;

public class MainShop {

	public MainShop(EntryFarm plugin) {
		Listeners(plugin);
		Commands(plugin);
	}
	
	public void Listeners(EntryFarm plugin) {
		new LstShop(plugin);
		new LstShopTrade(plugin);
	}
	
	public void Commands(EntryFarm plugin) {
		new CmdShopCreate(plugin);
	}
}
