package net.specialattack.core.games.team;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Team {

	public List<String> players = new ArrayList<String>();

	private TeamColor color;
	private int sizeLimit;

	public Team(TeamColor color) {
		this(color, -1);
	}

	public Team(TeamColor color, int sizeLimit) {
		this.color = color;
		this.sizeLimit = sizeLimit;
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

	public int getNumberOfPlayers() {
		return players.size() + 1;
	}

	public boolean addPlayer(Player p) {
		return players.add(p.getName());
	}

	public boolean addPlayer(String p) {
		if (getNumberOfPlayers() >= sizeLimit) return false;
		players.add(p);
		return true;
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

	public int getSizeLimit() {
		return sizeLimit;
	}

	public void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}

}
