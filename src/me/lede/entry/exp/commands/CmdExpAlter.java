package me.lede.entry.exp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lede.entry.EntryFarm;
import me.lede.entry.exp.utils.UtlExp;

public class CmdExpAlter implements CommandExecutor {

	public CmdExpAlter(EntryFarm plugin) {
		plugin.getCommand("ent.exp.alter").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		
		if (!(sender instanceof Player)) return false;	
		Player p = (Player) sender;	
		p.openInventory(UtlExp.getAltar());
		return false;
	}
	
}
