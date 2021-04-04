package me.lede.entry.exp;

import me.lede.entry.EntryFarm;
import me.lede.entry.exp.commands.CmdExpAdmin;
import me.lede.entry.exp.commands.CmdExpAlter;
import me.lede.entry.exp.commands.CmdExpCoupon;
import me.lede.entry.exp.commands.CmdExpSort;
import me.lede.entry.exp.commands.CmdExpUser;
import me.lede.entry.exp.listeners.LstExp;
import me.lede.entry.exp.listeners.LstExpAlter;
import me.lede.entry.exp.listeners.LstExpCoupon;
import me.lede.entry.exp.utils.UtlExp;

public class MainExp {

	public MainExp(EntryFarm plugin) {
		Commands(plugin);
		Listeners(plugin);
		UtlExp.sortExp();
	}
	
	private void Commands(EntryFarm plugin) {
		new CmdExpSort(plugin);
		new CmdExpAlter(plugin);
		new CmdExpUser(plugin);
		new CmdExpAdmin(plugin);
		new CmdExpCoupon(plugin);
	}
	
	private void Listeners(EntryFarm plugin) {
		new LstExp(plugin);
		new LstExpCoupon(plugin);
		new LstExpAlter(plugin);
	}
	
}
