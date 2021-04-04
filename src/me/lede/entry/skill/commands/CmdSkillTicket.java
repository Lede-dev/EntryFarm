package me.lede.entry.skill.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lede.entry.EntryFarm;
import me.lede.entry.skill.utils.UtlSkill;
import me.lede.entry.utils.Utils;

public class CmdSkillTicket implements CommandExecutor {

	public CmdSkillTicket(EntryFarm plugin) {
		plugin.getCommand("ent.skill.ticket").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		
		if (!(sender instanceof Player)) return false;
		
		Player p = (Player) sender;
		
		p.getInventory().addItem(UtlSkill.getFlyTicket());
		p.sendMessage(Utils.chat("플라이 이용권을 획득하였습니다."));
		
		return false;
	}
	
}
