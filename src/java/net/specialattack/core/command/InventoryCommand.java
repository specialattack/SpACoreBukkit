
package net.specialattack.core.command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;

import net.specialattack.core.PlayerStorage;
import net.specialattack.core.SpACore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command that displays version info about SpACore etc...
 * 
 * @author heldplayer
 * 
 */
public class InventoryCommand extends SpACoreSubCommand {

    public InventoryCommand(String name, String permissions, String... aliases) {
        super(name, permissions, aliases);
    }

    @Override
    public void runCommand(CommandSender sender, String alias, String... args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            try {
                if (args[0].equalsIgnoreCase("save")) {
                    PlayerStorage.store(player);
                }
                else if (args[0].equalsIgnoreCase("load")) {
                    PlayerStorage.apply(player);
                }
                else {
                    sender.sendMessage(ChatColor.RED + "Invalid syntax. Correct syntax:");
                    sender.sendMessage(ChatColor.GRAY + "/" + alias + " <save|load> [username]");
                }
            }
            catch (FileNotFoundException e) {
                sender.sendMessage(ChatColor.RED + "Player has not been saved before.");
            }
            catch (IOException e) {
                sender.sendMessage(ChatColor.RED + "Error while proccessing command: " + e.getMessage());
                SpACore.log(Level.WARNING, "Error while proccessing command", e);
            }
        }
        else if (args.length == 2) {
            try {
                player = Bukkit.getPlayer(args[1]);
                if (player == null) {
                    sender.sendMessage(ChatColor.RED + "Player not online.");
                    return;
                }

                if (args[0].equalsIgnoreCase("save")) {
                    PlayerStorage.store(player);
                }
                else if (args[0].equalsIgnoreCase("load")) {
                    PlayerStorage.apply(player);
                }
                else {
                    sender.sendMessage(ChatColor.RED + "Invalid syntax. Correct syntax:");
                    sender.sendMessage(ChatColor.GRAY + "/" + alias + " <save|load> [username]");
                }
            }
            catch (FileNotFoundException e) {
                sender.sendMessage(ChatColor.RED + "Player has not been saved before.");
            }
            catch (IOException e) {
                sender.sendMessage(ChatColor.RED + "Error while proccessing command: " + e.getMessage());
                SpACore.log(Level.WARNING, "Error while proccessing command", e);
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "Invalid syntax. Correct syntax:");
            sender.sendMessage(ChatColor.GRAY + "/" + alias + " <save|load> [username]");
        }
    }

    @Override
    public boolean canUseCommand(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public List<String> getTabCompleteResults(CommandSender sender, String alias, String... args) {
        if (args.length == 2) {
            return null;
        }

        ArrayList<String> result = new ArrayList<String>();

        TreeSet<String> possibles = new TreeSet<String>();
        possibles.add("save");
        possibles.add("load");

        for (String possible : possibles) {
            if (possible.startsWith(args[0])) {
                result.add(possible);
            }
        }

        return result;
    }
}
