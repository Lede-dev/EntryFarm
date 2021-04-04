package me.lede.entry.skill.listeners;



import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import me.lede.entry.EntryFarm;
import me.lede.entry.skill.utils.UtlSkill;

public class LstSkillJump implements Listener {

	
	public LstSkillJump(EntryFarm plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onJump(PlayerMoveEvent event) {
			
		Player p = event.getPlayer();
		
		if (!p.isSneaking()) return;
					
		if (!UtlSkill.isJumpSkillMax(p)) return;
		
		if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) return;
				
		if(event.getFrom().getY() + 0.419 >= event.getTo().getY()) return;
			
		Vector unitVector = new Vector(p.getLocation().getDirection().getX(), 0.3, p.getLocation().getDirection().getZ());
		unitVector = unitVector.normalize();   
		p.setVelocity(unitVector.multiply(2));	
		
		
	}
	
}
