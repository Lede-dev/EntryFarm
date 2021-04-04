package me.lede.entry;

import org.bukkit.plugin.java.JavaPlugin;

import me.lede.entry.commands.CmdGetType;
import me.lede.entry.defaultmenu.MainDefaultMenu;
import me.lede.entry.exp.MainExp;
import me.lede.entry.mine.MainMine;
import me.lede.entry.player.MainPlayer;
import me.lede.entry.shop.MainShop;
import me.lede.entry.skill.MainSkill;
import me.lede.entry.utils.FileUtils;

public class EntryFarm extends JavaPlugin {

	@Override
	public void onEnable() {
		saveDefaultConfig();
		Utils();
		Commands();
		Instance();
	}
	
	private void Utils() {
		new FileUtils(this);
	}
	
	private void Instance() {
		new MainDefaultMenu(this);
		new MainMine(this);
		new MainExp(this);
		new MainPlayer(this);
		new MainSkill(this);
		new MainShop(this);
	}
	
	private void Commands() {
		new CmdGetType(this);
	}
	
}
