package net.specialattack.core.games.team;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Team {

	public List<String> players = new ArrayList<String>();

	private TeamColor color;

	public Team(TeamColor color) {
		this.color = color;
	}

	public void sendMessageToTeam(String name, String message, String... excluded) {
		int size = players.size();
		for (int i = 0; i < size; i++) {
			Player p = Bukkit.getPlayer(players.get(i));
			if (p != null) {
				if (!isInArray(excluded, p.getName())) {
					p.sendMessage(message);
				}
			}
		}
	}

	private boolean isInArray(String[] array, String name) {
		for (String n : array) {
			return n.equalsIgnoreCase(name);
		}
		return false;
	}

	public void addPlayer(Player p) {
		players.add(p.getName());
	}

	public void addPlayer(String p) {
		players.add(p);
	}

	public void removePlayer(Player p) {
		players.remove(p.getName());
	}

	public void removePlayer(String p) {
		players.remove(p);
	}

	public void clearTeam() {
		players.clear();
	}

	public boolean contains(Player player) {
		return players.contains(player.getName());
	}

	public boolean contains(String name) {
		return players.contains(name);
	}

	public TeamColor getColor() {
		return color;
	}

}
