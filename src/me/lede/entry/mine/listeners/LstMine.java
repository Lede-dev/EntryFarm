package me.lede.entry.mine.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;

import me.lede.entry.EntryFarm;
import me.lede.entry.mine.utils.UtlMine;
import me.lede.entry.skill.utils.UtlSkill;
import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class LstMine implements Listener {
	
	private EntryFarm plugin;
	
	public static final String MINE_ITEM = "광물 생성기";

	public LstMine(EntryFarm plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onPlace(BlockPlaceEvent event) {

		ItemStack tool = event.getItemInHand();
		String toolName = ChatColor.stripColor(tool.getItemMeta().getDisplayName());
		
		if (toolName == null || !toolName.equalsIgnoreCase(MINE_ITEM)) return;
		
		Player p = event.getPlayer();
		SuperiorPlayer sp = SuperiorSkyblockAPI.getPlayer(p);
		
		Location bLoc = event.getBlockPlaced().getLocation();
		String loc = CustomLoc(bLoc);
		String uuid = p.getUniqueId().toString();
		
		if (sp.getIsland() == null || !sp.getIsland().equals(SuperiorSkyblockAPI.getIslandAt(bLoc))) {
			p.sendMessage(Utils.chat("&c광물 생성기는 소속된 섬에만 설치할 수 있습니다."));
			event.setCancelled(true);
			return;
		}
		
		bLoc.setY(bLoc.getY()+1);
		bLoc.getBlock().setType(Material.STONE);
		
		bLoc.setY(bLoc.getY()+1.8);
		bLoc.setX(bLoc.getX()+0.5);
		bLoc.setZ(bLoc.getZ()+0.5);
		CMIHologram holo = new CMIHologram(loc, bLoc);
		holo.setLine(1, "&8< 광물생성기 >");
		CMI.getInstance().getHologramManager().addHologram(holo);
		holo.update();
		

		FileUtils.MineDBConfig.set(loc + ".state", true);
		FileUtils.MineDBConfig.set(loc + ".owner", uuid);
		FileUtils.MineDBConfig.set(loc + ".type", "default");
		FileUtils.save("MineDB");
		
	}
	
	@EventHandler
	private void replaceMine(PlayerInteractEvent event) {
		
		if (event.getHand() != EquipmentSlot.HAND) return;
		
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		
		if (event.getPlayer().isSneaking()) return;
		
		Location bLoc = event.getClickedBlock().getLocation();
		String loc = CustomLoc(bLoc);
		
		boolean state = FileUtils.MineDBConfig.getBoolean(loc + ".state") ? true : false;
		if (state == false) return;
		
		Player p = event.getPlayer();
		SuperiorPlayer sp = SuperiorSkyblockAPI.getPlayer(p);
		Island is = sp.getIsland();
		
		if (is == null) {
			p.sendMessage(Utils.chat("&c광물 생성기를 설치한 유저, 혹은 섬에 소속된 유저만 광물생성기를 회수할 수 있습니다."));
			return;
		}
		
		if (!(FileUtils.MineDBConfig.getString(loc+".owner").equalsIgnoreCase(p.getUniqueId().toString()) 
				|| is.equals(SuperiorSkyblockAPI.getIslandAt(p.getLocation())))) {
			p.sendMessage(Utils.chat("&c광물 생성기를 설치한 유저, 혹은 섬에 소속된 유저만 광물생성기를 회수할 수 있습니다."));
			return;
		}
					
		String type = FileUtils.MineDBConfig.getString(loc + ".type");
		ItemStack mine = UtlMine.getMine(type);
		
		FileUtils.MineDBConfig.set(loc, null);
		FileUtils.save("MineDB");
		
		Block b = event.getClickedBlock();
		bLoc.setY(bLoc.getY()+1);
		Block ub = bLoc.getBlock();
		b.setType(Material.AIR);
		ub.setType(Material.AIR);
		b.getWorld().dropItem(b.getLocation(), mine);
		
		bLoc.setY(bLoc.getY()+1.8);
		bLoc.setX(bLoc.getX()+0.5);
		bLoc.setZ(bLoc.getZ()+0.5);
		CMIHologram holo = CMI.getInstance().getHologramManager().getByLoc(bLoc);
		if (holo == null) return;
		holo.remove();
	}
	
	@EventHandler
	private void onBreak(BlockBreakEvent event) {
		Player p = event.getPlayer();
		Block b = event.getBlock();
		Location loc = b.getLocation();
		loc.setY(loc.getY()-1);
		String locS = CustomLoc(loc);
		
		boolean state = FileUtils.MineDBConfig.getBoolean(locS + ".state") ? true : false;
		if (state == false) return;
		
		SpecialItem(p, loc);
		
		String type = FileUtils.MineDBConfig.getString(locS + ".type");
		Material material = UtlMine.getChance(type);
			
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				b.setType(material);	
			}
		}, 1);
	}

	
	@EventHandler
	private void mineBreak(BlockBreakEvent event) {

		String loc = CustomLoc(event.getBlock().getLocation());
		
		boolean state = FileUtils.MineDBConfig.getBoolean(loc + ".state") ? true : false;
		if (state == false) return;
		
		event.setCancelled(true);
		event.getPlayer().sendMessage(Utils.chat("&c광물생성기는 우클릭으로 회수할 수 있습니다."));
		
	}
	
	private void SpecialItem(Player p, Location loc) {
		Double eff = UtlSkill.getMiningEffect(p);
		
		if (eff == null || eff <= 0.0d) return;
		
		int effi = (int) (eff * 10);
		int r = (int) (Math.random()*1000);
		
		if (r > effi) return;
		
		loc.getWorld().dropItem(loc, new ItemStack(Material.REDSTONE, 1));
	}
	
	public static String CustomLoc(Location loc) {
		
		int x = (int) loc.getX();
		int y = (int) loc.getY();
		int z = (int) loc.getZ();
		String w = loc.getWorld().getName();
		
		String r = w + "/" + x + "/" + y + "/" + z;
		return r;
	}
}


































