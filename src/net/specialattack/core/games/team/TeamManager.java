
package net.specialattack.core.games.team;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class TeamManager {

    public HashMap<TeamColors, Team> teams = new HashMap<TeamColors, Team>();
    private int maxTeamSize;
    private boolean allowTeamsToBeAdded;

    public TeamManager() {
        //Heh, Perhaps we need to tell it the playground it is a part of... Not too sure atm... 
        allowTeamsToBeAdded = true;
    }

    public boolean addTeam(TeamColors color) {
        if (!allowTeamsToBeAdded)
            return false;
        if (teams.containsKey(color))
            return false;
        teams.put(color, new Team(color, maxTeamSize));
        return true;
    }

    public boolean addPlayer(Player p, TeamColors color) {
        if (teams.containsKey(color)) {
            teams.get(color).addPlayer(p);
        }
        else {
            if (addTeam(color)) {
                teams.get(color).addPlayer(p);
            }
        }
        return false;
    }

    public void removePlayer(Player p) {
        Team t = getTeamForPlayer(p);
        if (t != null) {
            t.removePlayer(p);
        }
    }

    public void allowTeamsToBeAdded(boolean allow) {
        this.allowTeamsToBeAdded = allow;
    }

    public void setTeamSizeMax(int size) {
        this.maxTeamSize = size;
        for (Team t : teams.values()) {
            t.setMaxTeamSize(size);
        }
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public Team getTeamForPlayer(Player player) {
        for (int i = 0; i < 15; i++) {
            if (teams.containsKey(i)) {
                Team t = teams.get(i);
                if (t.contains(player)) {
                    return t;
                }
            }
        }
        return null;
    }

    public Team getTeamForName(String name) {
        for (int i = 0; i < 15; i++) {
            if (teams.containsKey(i)) {
                Team t = teams.get(i);
                if (t.contains(name)) {
                    return t;
                }
            }
        }
        return null;
    }

    public Team getTeam(int data) {
        if (teams.containsKey(data)) {
            return teams.get(data);
        }
        return null;
    }

}
