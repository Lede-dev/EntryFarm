package me.lede.entry.defaultmenu.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.lede.entry.EntryFarm;
import me.lede.entry.defaultmenu.utils.CreateInv;
import me.lede.entry.defaultmenu.utils.DefaultMenuCustomLoc;
import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class CmdDefaultMenu implements CommandExecutor {

	private static final String WrongSize = "&c1~6 사이의 수를 입력해주세요";
	private static final String LineSetComplete = "&a줄 설정 완료";
	private static final String WrongCommand = "&c커맨드를 입력해주세요";
	private static final String InputCommand = "&6[ %슬롯% ] &a번 슬롯에 입력한 커맨드는 &6[ %커맨드% ] &a입니다";
	private static final String WrongCommandSlot = "&c1~54 사이의 수를 입력해주세요";
	private static final String WrongBlock = "&c%칸%칸 안에 블럭이 존재하지 않습니다";
	private static final String addBlock = "&e좌표 &6[ %좌표% ] &e블럭 &6[ %블럭% ] &a추가&e완료";
	private static final String removeBlock = "&e좌표 &6[ %좌표% ] &e블럭 &6[ %블럭% ] &c삭제&e완료";
	private static final int blockCheckRange = 10;
	
	public CmdDefaultMenu(EntryFarm plugin) {
		plugin.getCommand("Command.Custom.Menu.Default").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {

		if (!(sender instanceof Player)) return true;
		
		Player p = (Player) sender;
		
		if (arg.length == 0) { // Open Menu.
			p.openInventory(CreateInv.create());
			return true;
		}
		
		if (arg[0].equalsIgnoreCase("설정")) { // Setting.
			
			if (arg.length == 1) return true;
			
			if (arg[1].equalsIgnoreCase("줄")) { // Line.
				int size = Integer.parseInt(arg[2]);
				
				try {
					
					if (size < 1) {
						p.sendMessage(Utils.chat(WrongSize));
						return true;
					}
					
					else if (size > 6) {
						p.sendMessage(Utils.chat(WrongSize));
						return true;
					}
					
					FileUtils.MainMenuDBConfig.set("Size", size*9);
					FileUtils.save("MainMenuDB");	
					p.sendMessage(Utils.chat(LineSetComplete));
					return true;
				}
				catch (Exception ex) {}
				
			}
			
			else if (arg[1].equalsIgnoreCase("배치")) { // Items.
				p.openInventory(CreateInv.editor());
			}
			
			else if (arg[1].equalsIgnoreCase("커맨드")) { // Commands.
				
				int length = arg.length;
				int slot = Integer.parseInt(arg[2]);
				
				if (slot < 1) {
					p.sendMessage(Utils.chat(WrongCommandSlot));
					return true;
				}
				
				if (slot > 54) {
					p.sendMessage(Utils.chat(WrongCommandSlot));
					return true;
				}
				
				if (length <= 3) {
					p.sendMessage(Utils.chat(WrongCommand));
					return true;
				}
				
				String command = new String();
				
				for (int i=3; i< length; i++) {
					command += (arg[i]);
					if (i >= length-1) break;
					command += " ";
				}
				
				p.sendMessage(Utils.chat(InputCommand.replace("%커맨드%", "/" + command).replace("%슬롯%", slot + "")));
				FileUtils.MainMenuDBConfig.set("Commands." + (slot-1), command);
				FileUtils.save("MainMenuDB");
				
			}
			
			else if (arg[1].equalsIgnoreCase("좌표")) {
				
				Block b = Utils.getTarget(p, blockCheckRange);
				Location bLoc = b.getLocation();
				String customLoc = DefaultMenuCustomLoc.create(bLoc);
				Material bType = b.getType();

				if (bType.equals(Material.AIR)) {
					p.sendMessage(Utils.chat(WrongBlock.replace("%칸%", blockCheckRange + "")));
					return true;
				}
				
				if (arg[2].equalsIgnoreCase("등록") || arg[2].equalsIgnoreCase("추가")) {
					
					FileUtils.MainMenuDBConfig.set("NpcLocation." + customLoc, true);
					FileUtils.save("MainMenuDB");
					
					p.sendMessage(Utils.chat(addBlock.replace("%좌표%", bLoc.getWorld().getName() + " " + bLoc.getX() + " " + bLoc.getY() + " " + bLoc.getZ()).replace("%블럭%", bType.toString())));
				}
				
				else if (arg[2].equalsIgnoreCase("해제") || arg[2].equalsIgnoreCase("삭제")) {
					
					FileUtils.MainMenuDBConfig.set("NpcLocation." + customLoc, null);
					FileUtils.save("MainMenuDB");
					
					p.sendMessage(Utils.chat(removeBlock.replace("%좌표%", bLoc.getWorld().getName() + " " + bLoc.getX() + " " + bLoc.getY() + " " + bLoc.getZ()).replace("%블럭%", bType.toString())));
				}
				
			}
					
		}
		
		return true;
	}
	
	
}
