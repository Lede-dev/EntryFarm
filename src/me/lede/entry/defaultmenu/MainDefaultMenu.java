package me.lede.entry.defaultmenu;

import me.lede.entry.EntryFarm;
import me.lede.entry.defaultmenu.commands.CmdDefaultMenu;
import me.lede.entry.defaultmenu.listeners.LstDefaultMenu;

public class MainDefaultMenu {

	public MainDefaultMenu(EntryFarm plugin) {		
		Commands(plugin);	
		Listeners(plugin);
	}
	
	
	private void Commands(EntryFarm plugin) {
		new CmdDefaultMenu(plugin);
	}
	
	private void Listeners(EntryFarm plugin) {
		new LstDefaultMenu(plugin);
	}
}
