package me.lede.entry.skill.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class UtlSkill {
	
	
	public static final String skillGuiName = Utils.chat("&f &f &f &f &f &f &f &f &f &f &f&c&l ㆍ Entri ㆍ &8스킬창");
	
	public static final String Icon_Mining = Utils.chat("&f&l[스킬] &7채광 &8(Mining)");
	public static final String Icon_Harvesting = Utils.chat("&f&l[스킬] &e수확 &8(Harvesting)");
	public static final String Icon_Jomp = Utils.chat("&f&l[스킬] &3도약 &8(Jump)");
	public static final String Icon_Affinity = Utils.chat("&f&l[스킬] &6친화력 &8(Affinity)");
	public static final String Icon_Fly = Utils.chat("&f&l[스킬] &f플라이 &8(Fly)");
	
	public static final String Ticket_Fly = Utils.chat("&e플라이 이용권");
	
	public static Inventory getSkillGUI(UUID PlayerUUID) {
		
		Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, skillGuiName);

		inv.setItem(0, getMiningIcon(PlayerUUID));
		inv.setItem(1, getHarvestingIcon(PlayerUUID));
		inv.setItem(2, getJumpIcon(PlayerUUID));
		inv.setItem(3, getAffinityIcon(PlayerUUID));
		inv.setItem(4, getFlyIcon(PlayerUUID));
		
		return inv;
		
	}
	
	public static ItemStack getFlyTicket() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(Ticket_Fly);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack getMiningIcon(UUID PlayerUUID) {
		ItemStack item = new ItemStack(Material.WOOD_PICKAXE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Icon_Mining);
		
		Integer max = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE_MAX.MINING");
		List<String> lore = new ArrayList<>();		
		lore.addAll(getMiningTooltip(PlayerUUID));
		if (getMiningLevel(PlayerUUID) < max) {
			lore.addAll(getNextMiningTooltip(PlayerUUID));
		}	
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getHarvestingIcon(UUID PlayerUUID) {
		ItemStack item = new ItemStack(Material.CARROT_ITEM);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Icon_Harvesting);
		
		Integer max = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE_MAX.HARVESTING");
		List<String> lore = new ArrayList<>();
		lore.addAll(getHarvestingTooltip(PlayerUUID));
		if (getHarvestingLevel(PlayerUUID) < max) {
			lore.addAll(getNextHarvestingTooltip(PlayerUUID));
		}
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getJumpIcon(UUID PlayerUUID) {
		ItemStack item = new ItemStack(Material.FEATHER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Icon_Jomp);
				
		Integer max = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE_MAX.JUMP");
		List<String> lore = new ArrayList<>();
		lore.addAll(getJumpTooltip(PlayerUUID));
		if (getJumpLevel(PlayerUUID) < max) {
			lore.addAll(getNextJumpTooltip(PlayerUUID));
		}
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getAffinityIcon(UUID PlayerUUID) {
		ItemStack item = new ItemStack(Material.DOUBLE_PLANT);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Icon_Affinity);
		
		Integer max = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE_MAX.AFFINITY");
		List<String> lore = new ArrayList<>();
		lore.addAll(getAffinityTooltip(PlayerUUID));
		if (getAffinityLevel(PlayerUUID) < max) {
			lore.addAll(getNextAffinityTooltip(PlayerUUID));
		}
		meta.setLore(lore);
		
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getFlyIcon(UUID PlayerUUID) {
		ItemStack item = new ItemStack(Material.ELYTRA);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Icon_Fly);
		
		List<String> lore = new ArrayList<>();
		lore.addAll(getFlyTooltip(PlayerUUID));
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}
	
	//============================================================
	
	public static int getMiningLevel(UUID PlayerUUID) {	
		return FileUtils.UserDBConfig.getInt(PlayerUUID + ".Skill.Mining");
	}
	
	public static int getHarvestingLevel(UUID PlayerUUID) {	
		return FileUtils.UserDBConfig.getInt(PlayerUUID + ".Skill.Harvesting");
	}
	
	public static int getJumpLevel(UUID PlayerUUID) {	
		return FileUtils.UserDBConfig.getInt(PlayerUUID + ".Skill.Jump");
	}
	
	public static int getAffinityLevel(UUID PlayerUUID) {	
		return FileUtils.UserDBConfig.getInt(PlayerUUID + ".Skill.Affinity");
	}
	
	//============================================================
	
	public static List<String> getMiningTooltip(UUID PlayerUUID) {
		List<String> lore = new ArrayList<String>();
		
		DecimalFormat format = new DecimalFormat();
		format.applyLocalizedPattern("0.0");
		
		Integer sp = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.MINING");
		int level = getMiningLevel(PlayerUUID)/sp;
		String eff = format.format(getMiningEffect(PlayerUUID));
	
		lore.add("");
		lore.add(Utils.chat(" &e스킬 레벨 &f: Lv." + level));
		lore.add("");
		lore.add(Utils.chat("&7특수한 스킬효과로 인하여 광물생성기 에서 &e" + eff + "% &7확률로"));
		lore.add(Utils.chat("&7특별한 광물이 생성됩니다. 특별한 광물은 상점을 통하여"));
		lore.add(Utils.chat("&7비싼 가격에 거래가 가능합니다."));
		
		return lore;
	}
	
	public static List<String> getNextMiningTooltip(UUID PlayerUUID) {
		List<String> lore = new ArrayList<String>();
		
		DecimalFormat format = new DecimalFormat();
		format.applyLocalizedPattern("0.0");
		
		Integer sp = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.MINING");
		Integer level = getMiningLevel(PlayerUUID)/sp + 1;
		Double eff = getMiningEffect(PlayerUUID);
		Double effu = FileUtils.DefaultConfig.getDouble("SKILL_EFFECT.MINING");
		String e = format.format(eff+effu);
		
		lore.add("");
		lore.add(Utils.chat(" &a다음 스킬 레벨 &f: Lv." + level + " &f &f &f &f &f &f &f &f &f &f &f &f &b(필요 SP : " + sp + "SP)"));
		lore.add("");
		lore.add(Utils.chat("&7특수한 스킬효과로 인하여 광물생성기 에서 &e" + e +"% &7확률로"));
		lore.add(Utils.chat("&7특별한 광물이 생성됩니다. 특별한 광물은 상점을 통하여"));
		lore.add(Utils.chat("&7비싼 가격에 거래가 가능합니다."));
		
		return lore;
	}
	
	public static List<String> getHarvestingTooltip(UUID PlayerUUID) {
		List<String> lore = new ArrayList<String>();
		
		DecimalFormat format = new DecimalFormat();
		format.applyLocalizedPattern("0.0");
		
		Integer sp = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.HARVESTING");
		Integer level = getHarvestingLevel(PlayerUUID)/sp;
		String eff = format.format(getHarvestingEffect(PlayerUUID));
		
		lore.add("");
		lore.add(Utils.chat(" &e스킬 레벨 &f: Lv." + level));
		lore.add("");
		lore.add(Utils.chat("&7특수한 스킬효과로 인하여 다 자란 농작물을 수확할 경우"));
		lore.add(Utils.chat("&7희귀한 식물인 &f파꽃&7이 &e" + eff + "%&7확률로 드랍됩니다. &f파꽃&7은"));
		lore.add(Utils.chat("&7상점 또는 각종 아이템 제작에 사용되는 희귀한 재화로"));
		lore.add(Utils.chat("&7사용됩니다. &c(사탕수수, 코코아콩 에서는 드랍되지 않음)"));
		
		return lore;
	}
	
	public static List<String> getNextHarvestingTooltip(UUID PlayerUUID) {
		List<String> lore = new ArrayList<String>();
		
		DecimalFormat format = new DecimalFormat();
		format.applyLocalizedPattern("0.0");
		
		Integer sp = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.HARVESTING");
		Integer level = getHarvestingLevel(PlayerUUID)/sp + 1;	
		Double eff = getHarvestingEffect(PlayerUUID);
		Double effu = FileUtils.DefaultConfig.getDouble("SKILL_EFFECT.HARVESTING");
		
		String e = format.format(eff+effu);
		
		lore.add("");
		lore.add(Utils.chat(" &a다음 스킬 레벨 &f: Lv." + level + " &f &f &f &f &f &f &f &f &f &f &f &f &b(필요 SP : " + sp + "SP)"));
		lore.add("");
		lore.add(Utils.chat("&7특수한 스킬효과로 인하여 다 자란 농작물을 수확할 경우"));
		lore.add(Utils.chat("&7희귀한 식물인 &f파꽃&7이 &e" + e + "%&7확률로 드랍됩니다. &f파꽃&7은"));
		lore.add(Utils.chat("&7상점 또는 각종 아이템 제작에 사용되는 희귀한 재화로"));
		lore.add(Utils.chat("&7사용됩니다. &c(사탕수수, 코코아콩 에서는 드랍되지 않음)"));
		
		return lore;
	}
	
	public static List<String> getJumpTooltip(UUID PlayerUUID) {
		List<String> lore = new ArrayList<String>();
		
		Integer sp = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.JUMP");
		Integer level = getJumpLevel(PlayerUUID)/sp;
		
		lore.add("");
		lore.add(Utils.chat(" &e스킬 레벨 &f: Lv." + level));
		lore.add("");
		lore.add(Utils.chat("&7특수한 스킬효과로 인하여 먼 거리를 간편하게 &f앉기+점프"));
		lore.add(Utils.chat("&7를 이용하여 간편하게 이동하실 수 있습니다."));
		
		return lore;
	}
	
	public static List<String> getNextJumpTooltip(UUID PlayerUUID) {
		List<String> lore = new ArrayList<String>();
		
		Integer sp = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.JUMP");
		Integer level = getJumpLevel(PlayerUUID)/sp + 1;
			
		lore.add("");
		lore.add(Utils.chat(" &a다음 스킬 레벨 &f: Lv." + level + " &f &f &f &f &f &f &f &f &f &f &f &f &b(필요 SP : " + sp + "SP)"));
		lore.add("");
		lore.add(Utils.chat("&7특수한 스킬효과로 인하여 먼 거리를 간편하게 &f앉기+점프"));
		lore.add(Utils.chat("&7를 이용하여 간편하게 이동하실 수 있습니다."));
		
		return lore;
	}
	
	public static List<String> getAffinityTooltip(UUID PlayerUUID) {
		List<String> lore = new ArrayList<String>();
		
		DecimalFormat format = new DecimalFormat();
		format.applyLocalizedPattern("0.0");
		
		Integer sp = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.AFFINITY");
		Integer level = getAffinityLevel(PlayerUUID)/sp;
		String eff = format.format(getAffinityEffect(PlayerUUID));
			
		lore.add("");
		lore.add(Utils.chat(" &e스킬 레벨 &f: Lv." + level));
		lore.add("");
		lore.add(Utils.chat("&7특수한 스킬효과로 인해서 NPC 와의 친화력이 상승하여"));
		lore.add(Utils.chat("&7상점에 아이템을 판매할 시 판매한 금액의 10%를 &e" + eff +"%"));
		lore.add(Utils.chat("&7확률로 추가 지급합니다."));
		
		return lore;
	}
	
	public static List<String> getNextAffinityTooltip(UUID PlayerUUID) {
		List<String> lore = new ArrayList<String>();
		
		DecimalFormat format = new DecimalFormat();
		format.applyLocalizedPattern("0.0");
		
		Integer sp = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.AFFINITY");
		Integer level = getAffinityLevel(PlayerUUID)/sp + 1;	
		Double eff = getAffinityEffect(PlayerUUID);
		Double effu = FileUtils.DefaultConfig.getDouble("SKILL_EFFECT.AFFINITY");
		
		String e = format.format(eff+effu);
		
		lore.add("");
		lore.add(Utils.chat(" &a다음 스킬 레벨 &f: Lv." + level + " &f &f &f &f &f &f &f &f &f &f &f &f &b(필요 SP : " + sp + "SP)"));
		lore.add("");
		lore.add(Utils.chat("&7특수한 스킬효과로 인해서 NPC 와의 친화력이 상승하여"));
		lore.add(Utils.chat("&7상점에 아이템을 판매할 시 판매한 금액의 10%를 &e" + e + "%"));
		lore.add(Utils.chat("&7확률로 추가 지급합니다."));
		
		return lore;
	}
	
	public static List<String> getFlyTooltip(UUID PlayerUUID) {
		List<String> lore = new ArrayList<String>();
		
		Boolean State = FileUtils.UserDBConfig.getBoolean(PlayerUUID + ".Skill.Fly");
		String s = "&c사용 불가";
		if (State) s = "&a사용 가능";
				
		lore.add("");
		lore.add(Utils.chat(" &e스킬 상태 &f: " + s));
		lore.add("");
		lore.add(Utils.chat("&7해당 스킬은 &f플라이 이용권&7을 사용하여 습득이 가능한"));
		lore.add(Utils.chat("&7스킬 입니다. &f플라이 이용권&7은 &e후원 &7또는 &e이벤트&7를 통해"));
		lore.add(Utils.chat("&7획득이 가능합니다."));
		
		return lore;
	}

	public static void setTotalSkillPoint(Player p) {
		setTotalSkillPoint(p.getUniqueId().toString());
	}
	
	public static void setTotalSkillPoint(UUID uuid) {
		setTotalSkillPoint(uuid.toString());
	}
	
	public static void setTotalSkillPoint(String uuid) {
		Integer level = FileUtils.UserDBConfig.getInt(uuid + ".Level");
		Integer point = FileUtils.DefaultConfig.getInt("SKILL_POINT");
		FileUtils.UserDBConfig.set(uuid + ".Skill.Total", (--level)*point);
		FileUtils.save("UserDB");
	}
	
	public static Integer getTotalSkillPoint(Player p) {
		return getTotalSkillPoint(p.getUniqueId().toString());
	}
	
	public static Integer getTotalSkillPoint(UUID uuid) {
		return getTotalSkillPoint(uuid.toString());
	}
	
	public static Integer getTotalSkillPoint(String uuid) {
		return FileUtils.UserDBConfig.getInt(uuid + ".Skill.Total");
	}
	
	public static Integer getMiningSkillPoint(Player p) {
		return getMiningSkillPoint(p.getUniqueId().toString());
	}
	
	public static Integer getMiningSkillPoint(UUID uuid) {
		return getMiningSkillPoint(uuid.toString());
	}
	
	public static Integer getMiningSkillPoint(String uuid) {
		return FileUtils.UserDBConfig.getInt(uuid + ".Skill.Mining");
	}
	
	public static Integer getHarvestingSkillPoint(Player p) {
		return getHarvestingSkillPoint(p.getUniqueId().toString());
	}
	
	public static Integer getHarvestingSkillPoint(UUID uuid) {
		return getHarvestingSkillPoint(uuid.toString());
	}
	
	public static Integer getHarvestingSkillPoint(String uuid) {
		return FileUtils.UserDBConfig.getInt(uuid + ".Skill.Harvesting");
	}
	
	public static Integer getJumpSkillPoint(Player p) {
		return getJumpSkillPoint(p.getUniqueId().toString());
	}
	
	public static Integer getJumpSkillPoint(UUID uuid) {
		return getJumpSkillPoint(uuid.toString());
	}
	
	public static Integer getJumpSkillPoint(String uuid) {
		return FileUtils.UserDBConfig.getInt(uuid + ".Skill.Jump");
	}
	
	public static Integer getAffinitySkillPoint(Player p) {
		return getAffinitySkillPoint(p.getUniqueId().toString());
	}
	
	public static Integer getAffinitySkillPoint(UUID uuid) {
		return getAffinitySkillPoint(uuid.toString());
	}
	
	public static Integer getAffinitySkillPoint(String uuid) {
		return FileUtils.UserDBConfig.getInt(uuid + ".Skill.Affinity");
	}
	
	public static Boolean isSkillUpgradeable(Player p) {
		return isSkillUpgradeable(p.getUniqueId().toString());
	}
	
	public static Boolean isSkillUpgraedable(UUID uuid) {
		return isSkillUpgradeable(uuid.toString());
	}
	
	public static Boolean isSkillUpgradeable(String uuid) {
		Integer total = getTotalSkillPoint(uuid);
		Integer mining = getMiningSkillPoint(uuid);
		Integer harvesting = getHarvestingSkillPoint(uuid);
		Integer jump = getJumpSkillPoint(uuid);
		Integer affinity = getAffinitySkillPoint(uuid);
		if (total > mining + harvesting + jump + affinity) return true;
		return false;	
	}
	
	public static Integer getPointLeft(Player p) {
		return getPointLeft(p.getUniqueId().toString());
	}
	
	public static Integer getPointLeft(UUID uuid) {
		return getPointLeft(uuid.toString());
	}
	
	public static Integer getPointLeft(String uuid) {
		Integer total = getTotalSkillPoint(uuid);
		Integer mining = getMiningSkillPoint(uuid);
		Integer harvesting = getHarvestingSkillPoint(uuid);
		Integer jump = getJumpSkillPoint(uuid);
		Integer affinity = getAffinitySkillPoint(uuid);
		Integer point = total - (mining + harvesting + jump + affinity);
		return point;
	}
	
	public static void upgradeMiningSkill(Player p) {
		upgradeMiningSkill(p.getUniqueId().toString());
	}
	
	public static void upgradeMiningSkill(UUID uuid) {
		upgradeMiningSkill(uuid.toString());
	}
	
	public static void upgradeMiningSkill(String uuid) {
		Integer point = FileUtils.UserDBConfig.getInt(uuid + ".Skill.Mining");
		Integer add = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.MINING");
		FileUtils.UserDBConfig.set(uuid + ".Skill.Mining", point + add);
		FileUtils.save("UserDB");
		Bukkit.getLogger().log(Level.INFO, Utils.chat("&e" + uuid + " : Skill Upgrade [ &aMining " + (point+add) + " &e]"));
	}
	
	public static void upgradeHarvestingSkill(Player p) {
		upgradeHarvestingSkill(p.getUniqueId().toString());
	}
	
	public static void upgradeHarvestingSkill(UUID uuid) {
		upgradeHarvestingSkill(uuid.toString());
	}
	
	public static void upgradeHarvestingSkill(String uuid) {
		Integer point = FileUtils.UserDBConfig.getInt(uuid + ".Skill.Harvesting");
		Integer add = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.HARVESTING");
		FileUtils.UserDBConfig.set(uuid + ".Skill.Harvesting", point + add);
		FileUtils.save("UserDB");
		Bukkit.getLogger().log(Level.INFO, Utils.chat("&e" + uuid + " : Skill Upgrade [ &aHarvesting " + (point+add) + " &e]"));
	}
	
	public static void upgradeJumpSkill(Player p) {
		upgradeJumpSkill(p.getUniqueId().toString());
	}
	
	public static void upgradeJumpSkill(UUID uuid) {
		upgradeJumpSkill(uuid.toString());
	}
	
	public static void upgradeJumpSkill(String uuid) {
		Integer point = FileUtils.UserDBConfig.getInt(uuid + ".Skill.Jump");
		Integer add = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.JUMP");
		FileUtils.UserDBConfig.set(uuid + ".Skill.Jump", point + add);
		FileUtils.save("UserDB");
		Bukkit.getLogger().log(Level.INFO, Utils.chat("&e" + uuid + " : Skill Upgrade [ &aJump " + (point+add) + " &e]"));
	}
	
	public static void upgradeAffinitySkill(Player p) {
		upgradeAffinitySkill(p.getUniqueId().toString());
	}
	
	public static void upgradeAffinitySkill(UUID uuid) {
		upgradeAffinitySkill(uuid.toString());
	}
	
	public static void upgradeAffinitySkill(String uuid) {
		Integer point = FileUtils.UserDBConfig.getInt(uuid + ".Skill.Affinity");
		Integer add = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE.AFFINITY");
		FileUtils.UserDBConfig.set(uuid + ".Skill.Affinity", point + add);
		FileUtils.save("UserDB");
		Bukkit.getLogger().log(Level.INFO, Utils.chat("&e" + uuid + " : Skill Upgrade [ &aAffinity " + (point+add) + " &e]"));
	}
	
	public static Double getMiningEffect(Player p){
		return getMiningEffect(p.getUniqueId().toString());
	}
	
	public static Double getMiningEffect(UUID uuid){
		return getMiningEffect(uuid.toString());
	}
	
	public static Double getMiningEffect(String uuid){
		double eff = FileUtils.DefaultConfig.getDouble("SKILL_EFFECT.MINING");
		int level = FileUtils.UserDBConfig.getInt(uuid + ".Skill.Mining");
		return eff * level;
	}
	
	public static Double getHarvestingEffect(Player p) {
		return getHarvestingEffect(p.getUniqueId().toString());
	}
	
	public static Double getHarvestingEffect(UUID uuid) {
		return getHarvestingEffect(uuid.toString());
	}
	
	public static Double getHarvestingEffect(String uuid) {
		double eff = FileUtils.DefaultConfig.getDouble("SKILL_EFFECT.HARVESTING");
		int level = FileUtils.UserDBConfig.getInt(uuid + ".Skill.Harvesting");
		return eff * level;
	}
	
	public static Double getAffinityEffect(Player p) {
		return getAffinityEffect(p.getUniqueId().toString());
	}
	
	public static Double getAffinityEffect(UUID uuid) {
		return getAffinityEffect(uuid.toString());
	}
	
	public static Double getAffinityEffect(String uuid) {
		double eff = FileUtils.DefaultConfig.getDouble("SKILL_EFFECT.AFFINITY");
		int level = FileUtils.UserDBConfig.getInt(uuid + ".Skill.Affinity");
		return eff * level;
	}
	
	public static void initSkill(Player p) {
		initSkill(p.getUniqueId().toString());
	}
	
	public static void initSkill(UUID uuid) {
		initSkill(uuid.toString());
	}
	
	public static Boolean isMiningSkillMax(Player p) {
		return isMiningSkillMax(p.getUniqueId().toString());
	}
	
	public static Boolean isMiningSkillMax(UUID uuid) {
		return isMiningSkillMax(uuid.toString());
	}
	
	public static Boolean isMiningSkillMax(String uuid) {
		Integer skill = getMiningSkillPoint(uuid);
		Integer max = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE_MAX.MINING");
		if (skill >= max) return false;
		return true;
	}
	
	public static Boolean isHarvestingSkillMax(Player p) {
		return isHarvestingSkillMax(p.getUniqueId().toString());
	}
	
	public static Boolean isHarvestingSkillMax(UUID uuid) {
		return isHarvestingSkillMax(uuid.toString());
	}
	
	public static Boolean isHarvestingSkillMax(String uuid) {
		Integer skill = getHarvestingSkillPoint(uuid);
		Integer max = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE_MAX.HARVESTING");
		if (skill >= max) return false;
		return true;
	}
	
	public static Boolean isJumpSkillMax(Player p) {
		return isJumpSkillMax(p.getUniqueId().toString());
	}
	
	public static Boolean isJumpSkillMax(UUID uuid) {
		return isJumpSkillMax(uuid.toString());
	}
	
	public static Boolean isJumpSkillMax(String uuid) {
		Integer skill = getJumpSkillPoint(uuid);
		Integer max = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE_MAX.JUMP");
		if (skill >= max) return true;
		return false;
	}
	
	public static Boolean isAffinitySkillMax(Player p) {
		return isAffinitySkillMax(p.getUniqueId().toString());
	}
	
	public static Boolean isAffinitySkillMax(UUID uuid) {
		return isAffinitySkillMax(uuid.toString());
	}
	
	public static Boolean isAffinitySkillMax(String uuid) {
		Integer skill = getAffinitySkillPoint(uuid);
		Integer max = FileUtils.DefaultConfig.getInt("SKILL_UPGRADE_MAX.AFFINITY");
		if (skill >= max) return false;
		return true;
	}
	
	public static void initSkill(String uuid) {
		Integer level = FileUtils.UserDBConfig.getInt(uuid + ".Skill.Total");
		if (level == 0) {
			FileUtils.UserDBConfig.set(uuid + ".Skill.Total", 0);
			FileUtils.UserDBConfig.set(uuid + ".Skill.Mining", 0);
			FileUtils.UserDBConfig.set(uuid + ".Skill.Harvesting", 0);
			FileUtils.UserDBConfig.set(uuid + ".Skill.Jump", 0);
			FileUtils.UserDBConfig.set(uuid + ".Skill.Affinity", 0);
			FileUtils.UserDBConfig.set(uuid + ".Skill.Fly", false);
			FileUtils.save("UserDB");
			Bukkit.getLogger().log(Level.INFO, Utils.chat("&e" + uuid + " : Initialize User &aSkill &eDatabase"));
		}
	}
	

}





