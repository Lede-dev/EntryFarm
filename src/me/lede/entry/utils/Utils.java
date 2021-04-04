package me.lede.entry.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class Utils {

	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static void startLogTitle(String text) {		
		Bukkit.getConsoleSender().sendMessage(Utils.chat("&2ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"));
		Bukkit.getConsoleSender().sendMessage(Utils.chat("&e < " + text + " >"));	
	}
	
	public static void startLogBody(String text) {
		Bukkit.getConsoleSender().sendMessage(Utils.chat("  &e- " + text));
	}
	
    public static final Block getTarget(Player player, Integer range) {
        BlockIterator bi = new BlockIterator(player, range);
        Block lastBlock = bi.next();
        while (bi.hasNext()) {
            lastBlock = bi.next();
            if (lastBlock.getType() == Material.AIR)
                continue;
            break;
        }
        return lastBlock;
    }
	
}
