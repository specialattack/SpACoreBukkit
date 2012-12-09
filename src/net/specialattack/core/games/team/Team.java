
package net.specialattack.core.games.team;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Team {

    //Gonna store the users name rather than their actual player object to save memory
    public List<String> players = new ArrayList<String>();
    private TeamColors teamColor;
    private int maxTeamSize;

    public Team(TeamColors color, int limit) {
        this.teamColor = color;
        this.maxTeamSize = limit;
    }

    public Team(TeamColors color) {
        this(color, -1);
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

    public boolean addPlayer(Player p) {
        return addPlayer(p.getName());
    }

    public boolean addPlayer(String p) {
        if (maxTeamSize >= 0) {
            if (getSize() >= maxTeamSize)
                return false;
        }
        players.add(p);
        return true;
    }

    private int getSize() {
        return players.size();
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

    public TeamColors getTeamColor() {
        return teamColor;
    }

    public ChatColor getChatColor() {
        return teamColor.getChatColor();
    }

    public int getWoolColor() {
        return teamColor.getBlockData();
    }

    public void setMaxTeamSize(int size) {
        this.maxTeamSize = size;
    }
}
