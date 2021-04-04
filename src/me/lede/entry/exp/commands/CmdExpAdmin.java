package me.lede.entry.exp.commands;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lede.entry.EntryFarm;
import me.lede.entry.exp.utils.UtlExp;
import me.lede.entry.player.utils.UtlPlayer;
import me.lede.entry.utils.Utils;

public class CmdExpAdmin implements CommandExecutor {

	public CmdExpAdmin(EntryFarm plugin) {
		plugin.getCommand("ent.exp.setting").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {

		if (!(sender instanceof Player)) return false;
		
		Player p = (Player) sender;
		
		if (arg.length != 3) {
			p.sendMessage(Utils.chat(
					"/ent.exp.setting <addlevel/addexp> <player> <int>\n"
					+ "/ent.exp.setting <removelevel/removeexp> <player> <int>\n"
					+ "/ent.exp.setting <setlevel/setexp> <player> <int>"));
			return false;
		}
		
		try {
			UUID uuidT = UtlPlayer.getUUIDByName(arg[1]);
			if (uuidT == null) {
				p.sendMessage(Utils.chat(
						"/ent.exp.setting <addlevel/addexp> <player> <int>\n"
						+ "/ent.exp.setting <removelevel/removeexp> <player> <int>\n"
						+ "/ent.exp.setting <setlevel/setexp> <player> <int>"));
				return false;
			}

			Integer value = Integer.parseInt(arg[2]);
			if (value <= 0) {
				p.sendMessage(Utils.chat(
						"/ent.exp.setting <addlevel/addexp> <player> <int>\n"
						+ "/ent.exp.setting <removelevel/removeexp> <player> <int>\n"
						+ "/ent.exp.setting <setlevel/setexp> <player> <int>"));
				return false;
			}
			
			if (arg[0].equalsIgnoreCase("addexp")) {
				UtlExp.addExp(uuidT.toString(), value);
				Bukkit.getLogger().log(Level.INFO, Utils.chat("&3Admin : Give Exp &a" + value + " &3to " + "&a" +uuidT));
				p.sendMessage(Utils.chat("&e플레이어 &6"+arg[1]+"&e에게 경험치 &a" + value + "&e만큼 지급하였습니다."));
				return false;
			}
			
			if (arg[0].equalsIgnoreCase("removeexp")) {
				UtlExp.addExp(uuidT.toString(), value*-1);
				Bukkit.getLogger().log(Level.INFO, Utils.chat("&3Admin : Remove Exp &c" + value + " &3from " + "&a" + uuidT));
				p.sendMessage(Utils.chat("&e플레이어 &6"+arg[1]+"&e의 경험치를 &c" + value + "&e만큼 차감하였습니다."));
				return false;
			}
			
			if (arg[0].equalsIgnoreCase("setexp")) {
				UtlExp.setExp(uuidT, value);
				Bukkit.getLogger().log(Level.INFO, Utils.chat("&3Admin : Set Exp &c" + value + " &3to " + "&a" + uuidT));
				p.sendMessage(Utils.chat("&e플레이어 &6"+arg[1]+"&e의 경험치를 &a" + value + "&e로 설정하였습니다."));
				return false;
			}
		} catch(Exception ex) {
			Bukkit.getLogger().log(Level.WARNING, Utils.chat("&cCommand Error : ent.exp.setting"));
			p.sendMessage(Utils.chat(
					"/ent.exp.setting <addlevel/addexp> <player> <int>\n"
					+ "/ent.exp.setting <removelevel/removeexp> <player> <int>\n"
					+ "/ent.exp.setting <setlevel/setexp> <player> <int>"));
			return false;
		}
		

			
		return false;
	}
	
	
	
}



