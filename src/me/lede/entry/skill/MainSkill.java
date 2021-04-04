package me.lede.entry.skill;

import me.lede.entry.EntryFarm;
import me.lede.entry.skill.commands.CmdSkill;
import me.lede.entry.skill.commands.CmdSkillFly;
import me.lede.entry.skill.commands.CmdSkillTicket;
import me.lede.entry.skill.listeners.LstSkill;
import me.lede.entry.skill.listeners.LstSkillFly;
import me.lede.entry.skill.listeners.LstSkillGUI;
import me.lede.entry.skill.listeners.LstSkillHarvest;
import me.lede.entry.skill.listeners.LstSkillJump;

public class MainSkill {

	public MainSkill(EntryFarm plugin) {
		Listeners(plugin);
		Commands(plugin);
	}
	
	private void Listeners(EntryFarm plugin) {
		new LstSkill(plugin);
		new LstSkillGUI(plugin);
		new LstSkillJump(plugin);
		new LstSkillHarvest(plugin);
		new LstSkillFly(plugin);
	}
	
	private void Commands(EntryFarm plugin) {
		new CmdSkill(plugin);
		new CmdSkillTicket(plugin);
		new CmdSkillFly(plugin);
	}
	
}
