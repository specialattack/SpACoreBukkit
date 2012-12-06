package net.specialattack.core.games.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

public class TeamManager {

	public HashMap<TeamColor, Team> teams = new HashMap<TeamColor, Team>();
	private List<String> inChat = new ArrayList<String>();
	private boolean canAddTeams = true;
	private int sizeLimit = -1;

	public void setInTeamChat(Player player, boolean inTeamChat) {
		if (inTeamChat) {
			inChat.add(player.getName());
		} else {
			inChat.remove(player.getName());
		}
	}

	public boolean addPlayerToTeam(Player player, TeamColor color) {
		if (teams.containsKey(color)) {
			teams.get(color).addPlayer(player);
			return true;
		} else {
			if (canAddTeams) {
				addTeam(color);
				return teams.get(color).addPlayer(player);
			}
		}
		return false;
	}

	public void addTeam(TeamColor color) {
		if (!teams.containsKey(color)) {
			teams.put(color, new Team(color, sizeLimit));
		}
	}

	public void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}

	public int getSizeLimit() {
		return sizeLimit;
	}
}
