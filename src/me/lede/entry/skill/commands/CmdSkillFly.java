package me.lede.entry.skill.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lede.entry.EntryFarm;
import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class CmdSkillFly implements CommandExecutor {

	public CmdSkillFly(EntryFarm plugin) {
		plugin.getCommand("ent.skill.fly").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {

		if (!(sender instanceof Player)) return false;
		
		Player p = (Player) sender;
		
		Boolean state = FileUtils.UserDBConfig.getBoolean(p.getUniqueId().toString() + ".Skill.Fly");
		if (state != true) {
			p.setAllowFlight(false);
			p.sendMessage(Utils.chat("&c플라이 이용권을 사용하여 플라이 스킬을 활성화할 수 있습니다."));
			return false;
		}
		
		if (p.getAllowFlight()) {
			p.setAllowFlight(false);
			p.sendMessage(Utils.chat("&c플라이 모드를 비활성화 하였습니다."));
			return false;
		}
		p.setAllowFlight(true);
		p.sendMessage(Utils.chat("&a플라이 모드를 활성화 하였습니다."));

		return false;
	}
	
	
}
