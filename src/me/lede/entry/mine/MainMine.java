package me.lede.entry.mine;

import me.lede.entry.EntryFarm;
import me.lede.entry.mine.commands.CmdMine;
import me.lede.entry.mine.listeners.LstMine;

public class MainMine {

	public MainMine(EntryFarm plugin) {
		Listeners(plugin);
		Commands(plugin);
	}
	
	public void Listeners(EntryFarm plugin) {
		new LstMine(plugin);
	}
	
	public void Commands(EntryFarm plugin) {
		new CmdMine(plugin);
	}
	
}
