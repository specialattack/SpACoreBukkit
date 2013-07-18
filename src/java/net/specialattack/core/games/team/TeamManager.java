
package net.specialattack.core.games.team;

import java.util.HashMap;

import org.bukkit.entity.Player;

/**
 * 
 * @author mbl111
 * 
 */
public class TeamManager {

    public HashMap<TeamColors, Team> teams = new HashMap<TeamColors, Team>();
    private int maxTeamSize;
    private boolean allowTeamsToBeAdded;

    public TeamManager() {
        //Heh, Perhaps we need to tell it the playground it is a part of... Not too sure atm... 
        this.allowTeamsToBeAdded = true;
    }

    public boolean addTeam(TeamColors color) {
        if (!this.allowTeamsToBeAdded) {
            return false;
        }
        if (this.teams.containsKey(color)) {
            return false;
        }
        this.teams.put(color, new Team(color, this.maxTeamSize));
        return true;
    }

    public boolean addPlayer(Player p, TeamColors color) {
        if (this.teams.containsKey(color)) {
            this.teams.get(color).addPlayer(p);
        }
        else {
            if (this.addTeam(color)) {
                this.teams.get(color).addPlayer(p);
            }
        }
        return false;
    }

    public void removePlayer(Player p) {
        Team t = this.getTeamForPlayer(p);
        if (t != null) {
            t.removePlayer(p);
        }
    }

    public void allowTeamsToBeAdded(boolean allow) {
        this.allowTeamsToBeAdded = allow;
    }

    public void setTeamSizeMax(int size) {
        this.maxTeamSize = size;
        for (Team t : this.teams.values()) {
            t.setMaxTeamSize(size);
        }
    }

    public int getMaxTeamSize() {
        return this.maxTeamSize;
    }

    public Team getTeamForPlayer(Player player) {
        for (int i = 0; i < 15; i++) {
            if (this.teams.containsKey(i)) {
                Team t = this.teams.get(i);
                if (t.contains(player)) {
                    return t;
                }
            }
        }
        return null;
    }

    public Team getTeamForName(String name) {
        for (int i = 0; i < 15; i++) {
            if (this.teams.containsKey(i)) {
                Team t = this.teams.get(i);
                if (t.contains(name)) {
                    return t;
                }
            }
        }
        return null;
    }

    public Team getTeam(int data) {
        if (this.teams.containsKey(data)) {
            return this.teams.get(data);
        }
        return null;
    }

}
