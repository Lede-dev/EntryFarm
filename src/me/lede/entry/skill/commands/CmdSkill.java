package me.lede.entry.skill.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lede.entry.EntryFarm;
import me.lede.entry.skill.utils.UtlSkill;

public class CmdSkill implements CommandExecutor {

	public CmdSkill(EntryFarm plugin) {
		plugin.getCommand("ent.skill.show").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		
		if (!(sender instanceof Player)) return false;
		
		if (arg.length != 0) return false;
		
		Player p = (Player) sender;
		
		p.openInventory(UtlSkill.getSkillGUI(p.getUniqueId()));
		
		return false;
	}
	
}
