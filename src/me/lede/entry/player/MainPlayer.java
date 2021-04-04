package me.lede.entry.player;

import me.lede.entry.EntryFarm;
import me.lede.entry.player.listeners.LstPlayer;

public class MainPlayer {

	public MainPlayer(EntryFarm plugin) {
		Listeners(plugin);
	}
	
	private void Listeners(EntryFarm plugin) {
		new LstPlayer(plugin);
	}
}
