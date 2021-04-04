package me.lede.entry.exp.utils;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.lede.entry.skill.utils.UtlSkill;
import me.lede.entry.utils.FileUtils;
import me.lede.entry.utils.Utils;

public class UtlExp {

	public static final String altarGuiName = Utils.chat("&f &f &f &f &f &f &f&a&l ㆍ 공헌의제단 ㆍ &8제단공간");
	
	public static Inventory getAltar() {
		Inventory inv = Bukkit.createInventory(null, 36, altarGuiName);
		return inv;
	}
	
	public static void sortExp() {
		
		Bukkit.getLogger().log(Level.INFO, Utils.chat("&eExperience System Load..."));
		
		int LEVEL_MAX = FileUtils.DefaultConfig.getInt("LEVEL_MAX");
		double EXP_BASIC = FileUtils.DefaultConfig.getDouble("EXP_BASIC");
		int EXP_INCREASE_RATE = FileUtils.DefaultConfig.getInt("EXP_INCREASE_RATE");
		
		for (int i=1; i <= LEVEL_MAX; i++) {
			FileUtils.SystemDBConfig.set("Exp." + i, (int) EXP_BASIC);
			EXP_BASIC += EXP_BASIC * EXP_INCREASE_RATE / 10000;
		}
		
		FileUtils.save("SystemDB");
		Bukkit.getLogger().log(Level.INFO, Utils.chat("&aExperience System Load Complete!"));
		
	}
	
	public static void addExp(Player p, int exp) {
		addExp(p.getUniqueId().toString(), exp);
	}
	
	public static void addExp(UUID uuid, int exp) {
		addExp(uuid.toString(), exp);
	}
	
	public static void addExp(String uuid, int exp) {
		
		int getLevel = FileUtils.UserDBConfig.getInt(uuid + ".Level");
		int MAX_LEVEL = FileUtils.DefaultConfig.getInt("LEVEL_MAX");
		if (getLevel == MAX_LEVEL) {
			FileUtils.UserDBConfig.set(uuid + ".Exp", 0);
			return;
		}
		
		int getExp = FileUtils.UserDBConfig.getInt(uuid + ".Exp");
		int getLevelUpExp = FileUtils.SystemDBConfig.getInt("Exp." + (getLevel+1));
		
		int expP = getExp + exp;
		
		if (expP < 0) expP = 0;
		
		if (expP >= getLevelUpExp) {
			expP -= getLevelUpExp;
			FileUtils.UserDBConfig.set(uuid + ".Level", (getLevel+1));
			FileUtils.UserDBConfig.set(uuid + ".Exp", expP);
			FileUtils.save("UserDB");
			UtlSkill.setTotalSkillPoint(uuid);
			Bukkit.getLogger().log(Level.INFO, Utils.chat("&e" + uuid + " : Level &a1 &eUp"));
			addExp(uuid, expP);
		}	
		else {
			FileUtils.UserDBConfig.set(uuid + ".Exp", expP);
			FileUtils.save("UserDB");
		}
	}
	
	public static void addCouponLevel(Player p) {
		addCouponLevel(p.getUniqueId().toString());
	}
	
	public static void addCouponLevel(UUID uuid) {
		addCouponLevel(uuid.toString());
	}
	
	public static void addCouponLevel(String uuid) {
		Integer level = FileUtils.UserDBConfig.getInt(uuid + ".CouponLevel");
		Integer max = FileUtils.DefaultConfig.getInt("LEVEL_ADDITIONAL_MAX");
		if (level > max) return;
		FileUtils.UserDBConfig.set(uuid + ".CouponLevel", level + 1);
		FileUtils.save("UserDB");
	}
	
	public static int getCouponLevel(Player p) {
		return getCouponLevel(p.getUniqueId().toString());
	}
	
	public static int getCouponLevel(UUID uuid) {
		return getCouponLevel(uuid.toString());
	}
	
	public static int getCouponLevel(String uuid) {
		int level = FileUtils.UserDBConfig.getInt(uuid + ".CouponLevel");
		return level;
	}
	
	public static int getLevel(Player p) {
		return getLevel(p.getUniqueId().toString());
	}
	
	public static int getLevel(UUID uuid) {
		return getLevel(uuid.toString());
	}
	
	public static int getLevel(String uuid) {
		int level = FileUtils.UserDBConfig.getInt(uuid + ".Level");
		return level;
	}
	
	public static int getExp(Player p) {
		return getExp(p.getUniqueId().toString());
	}
	
	public static int getExp(UUID uuid) {
		return getExp(uuid.toString());
	}
	
	public static int getExp(String uuid) {
		int exp = FileUtils.UserDBConfig.getInt(uuid + ".Exp");
		return exp;
	}
	
	public static int getTotalLevel(Player p) {
		return getTotalLevel(p.getUniqueId().toString());
	}
	
	public static int getTotalLevel(UUID uuid) {
		return getTotalLevel(uuid.toString());
	}
	
	public static int getTotalLevel(String uuid) {
		int level = getLevel(uuid);
		int coupon = getCouponLevel(uuid);
		return level + coupon;
	}
	
	public static int getLevelUpExp(Player p) {
		return getLevelUpExp(p.getUniqueId().toString());
	}
	
	public static int getLevelUpExp(UUID uuid) {
		return getLevelUpExp(uuid.toString());
	}
	
	public static int getLevelUpExp(String uuid) {
		int level = getLevel(uuid);
		int levelUpExp = FileUtils.SystemDBConfig.getInt("Exp." + (level+1));
		return levelUpExp;
	}
	
	public static void setExp(Player p, int value) {
		setExp(p.getUniqueId().toString(), value);
	}
	
	public static void setExp(UUID uuid, int value) {
		setExp(uuid.toString(), value);
	}
	
	public static void setExp(String uuid, int value) {
		if (value < 0) value = 0;
		FileUtils.UserDBConfig.set(uuid + ".Exp", value);
		FileUtils.save("UserDB");
		addExp(uuid, 0);
	}
	
	public static boolean isLevelCouponAvailabel(Player p) {
		return isLevelCouponAvailable(p.getUniqueId().toString());
	}
	
	public static boolean isLevelCouponAvailable(UUID uuid) {
		return isLevelCouponAvailable(uuid.toString());
	}
	
	public static boolean isLevelCouponAvailable(String uuid) {
		int realLevel = FileUtils.UserDBConfig.getInt(uuid + ".Level");
		int MAX_LEVEL = FileUtils.DefaultConfig.getInt("LEVEL_MAX");
		if (realLevel < MAX_LEVEL) return false;
		
		int couponLevel = FileUtils.UserDBConfig.getInt(uuid + ".CouponLevel");
		int MAX_COUPON_LEVEL = FileUtils.DefaultConfig.getInt("LEVEL_ADDITIONAL_MAX");
		if (couponLevel >= MAX_COUPON_LEVEL) return false;
		
		return true;
	}

	public static int getCropExp(ItemStack item) {
		int exp = 0;
		for (int i = 0; i < item.getAmount(); i++) {
			exp += getCropExp(item.getType().toString());
		}
		return exp;
	}
	
	public static int getCropExp(Material item) {
		return getCropExp(item.toString());
	}
	
	public static int getCropExp(String item) {
		return FileUtils.DefaultConfig.getInt("EXP_CREATE." + item);
	};
	
	public static void initExp(Player p) {
		initExp(p.getUniqueId().toString());
	}
	
	public static void initExp(UUID uuid) {
		initExp(uuid.toString());
	}
	
	public static void initExp(String uuid) {
		Integer level = FileUtils.UserDBConfig.getInt(uuid + ".Level");
		if (level == 0) {
			FileUtils.UserDBConfig.set(uuid + ".Level", 1);
			FileUtils.UserDBConfig.set(uuid + ".Exp", 0);
			FileUtils.UserDBConfig.set(uuid + ".CouponLevel", 0);
			FileUtils.save("UserDB");
			Bukkit.getLogger().log(Level.INFO, Utils.chat("&e" + uuid + " : Initialize User &aExp &eDatabase"));
		}
	}
}



















