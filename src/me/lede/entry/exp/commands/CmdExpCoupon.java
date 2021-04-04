package me.lede.entry.exp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lede.entry.EntryFarm;
import me.lede.entry.exp.utils.UtlExpGUI;

public class CmdExpCoupon implements CommandExecutor {

	public CmdExpCoupon(EntryFarm plugin) {
		plugin.getCommand("ent.exp.coupon").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {

		if (!(sender instanceof Player)) return false;
		
		if (arg.length != 0) return false;
		
		Player p = (Player) sender;
		
		p.getInventory().addItem(UtlExpGUI.getLevelCoupon());
		
		return false;
	}
	
	
	
}
