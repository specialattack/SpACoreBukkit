package net.specialattack.bukkit.core.games.team;

import java.util.ArrayList;
import java.util.List;
import net.specialattack.bukkit.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author mbl111
 */
public class Team {

    //Gonna store the users name rather than their actual player object to save memory
    // And because storing the playername instead of the play object is smarter ;)
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
        for (String player : this.players) {
            Player p = Bukkit.getPlayer(player);
            if (p != null) {
                if (!Util.isInArray(excluded, p.getName())) {
                    p.sendMessage(message);
                }
            }
        }
    }

    public boolean addPlayer(Player player) {
        return this.addPlayer(player.getName());
    }

    public boolean addPlayer(String player) {
        if (this.maxTeamSize >= 0) {
            if (this.getSize() >= this.maxTeamSize) {
                return false;
            }
        }
        this.players.add(player.toLowerCase());
        return true;
    }

    private int getSize() {
        return this.players.size();
    }

    public void removePlayer(Player player) {
        this.players.remove(player.getName().toLowerCase());
    }

    public void removePlayer(String player) {
        this.players.remove(player.toLowerCase());
    }

    public void clearTeam() {
        this.players.clear();
    }

    public boolean contains(Player player) {
        return this.players.contains(player.getName().toLowerCase());
    }

    public boolean contains(String name) {
        return this.players.contains(name.toLowerCase());
    }

    public TeamColors getTeamColor() {
        return this.teamColor;
    }

    public ChatColor getChatColor() {
        return this.teamColor.getChatColor();
    }

    public int getWoolColor() {
        return this.teamColor.getBlockData();
    }

    public int getArmorColor() {
        return this.teamColor.getArmorColor();
    }

    public String[] setMaxTeamSize(int size) {
        this.maxTeamSize = size;

        ArrayList<String> removedPlayers = new ArrayList<String>();

        while (this.players.size() > this.maxTeamSize) {
            String name = this.players.remove(this.players.size() - 1);

            removedPlayers.add(name);
        }

        String[] names = new String[removedPlayers.size()];

        for (int i = 0; i < names.length; i++) {
            names[i] = removedPlayers.get(i);
        }

        return names;
    }
}
