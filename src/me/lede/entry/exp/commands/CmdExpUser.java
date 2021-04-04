package me.lede.entry.exp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lede.entry.EntryFarm;
import me.lede.entry.exp.utils.UtlExp;
import me.lede.entry.utils.Utils;

public class CmdExpUser implements CommandExecutor {

	public CmdExpUser(EntryFarm plugin) {
		plugin.getCommand("ent.exp.show").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		
		if (!(sender instanceof Player)) return false;
		
		if (arg.length != 0) return false;
		
		Player p = (Player) sender;
		
		Integer level = UtlExp.getLevel(p);
		Integer exp = UtlExp.getExp(p);
		Integer coupon = UtlExp.getCouponLevel(p);
		Integer total = UtlExp.getTotalLevel(p);
		Integer levelUpExp = UtlExp.getLevelUpExp(p);
		
		p.sendMessage(Utils.chat(
				"&6======================================" +
				" \n \n" +
				"   &e[ &6레벨 정보 &e] \n \n" +
				"   &eᗚ &f레벨 : &e" + total + " &f( &b" + level + " &f+&a " + coupon + " &f) \n" +
				"   &eᗚ &f경험치 : &e" + exp + " &f/ &b" + levelUpExp +
				" \n \n" +
				"&6======================================"));
		
		return false;
	}
	
}
