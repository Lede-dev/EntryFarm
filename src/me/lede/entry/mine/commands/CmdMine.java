package me.lede.entry.mine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.lede.entry.EntryFarm;
import me.lede.entry.mine.utils.UtlMine;
import me.lede.entry.utils.Utils;

public class CmdMine implements CommandExecutor {

	public CmdMine(EntryFarm plugin) {
		plugin.getCommand("ent.mine.getitem").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		
		if (!(sender instanceof Player)) return false;
		
		Player p = (Player) sender;
		
		if (arg.length != 1) {
			p.sendMessage(Utils.chat("/ent.mine.getitem <광물생성기 이름>"));
			return false;
		}
		
		ItemStack mine = UtlMine.getMine(arg[0]);
		
		if (mine == null) {
			p.sendMessage(Utils.chat("&c해당 이름의 광물생성기는 존재하지 않습니다."));
			return false;
		}
		
		p.getInventory().addItem(mine);
		p.sendMessage(Utils.chat("&f광물생성기 &e[" + arg[0] + "] &f을(를) 획득하였습니다."));
		return false;
	}
	
}
