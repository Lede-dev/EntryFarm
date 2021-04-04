package me.lede.entry.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.lede.entry.EntryFarm;
import me.lede.entry.utils.Utils;

public class CmdGetType implements CommandExecutor {
	
	public CmdGetType(EntryFarm plugin) {
		plugin.getCommand("ent.util.type").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {

		if (!(sender instanceof Player)) return false;
		
		Player p = (Player) sender;
		ItemStack tool = p.getInventory().getItemInMainHand();
		
		if (tool != null) {
			p.sendMessage(Utils.chat("&f플레이어 도구의 타입 : &e" + tool.getType().toString()));
			return false;
		}
		p.sendMessage(Utils.chat("&cERROR! 플레이어 도구의 타입을 확인할 수 없습니다."));
		return false;
	}
	

}
