package me.lede.entry.defaultmenu.utils;

import org.bukkit.Location;

public class DefaultMenuCustomLoc {

	public static String create(Location loc) {
		return "W" + loc.getWorld().getName() + "X" + (int)loc.getX() + "Y" + (int)loc.getY() + "Z" + (int)loc.getZ();	
	}
	
}
