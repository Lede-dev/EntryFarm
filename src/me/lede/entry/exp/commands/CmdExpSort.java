package me.lede.entry.exp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.lede.entry.EntryFarm;

public class CmdExpSort implements CommandExecutor{

	public CmdExpSort(EntryFarm plugin) {
		plugin.getCommand("ent.exp.sort").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
