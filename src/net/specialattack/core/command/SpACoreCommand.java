
package net.specialattack.core.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 * Main command class, sub commands can be registered here to decrease the
 * amount of commands.
 * 
 * @author heldplayer
 * 
 */
public class SpACoreCommand implements CommandExecutor, TabCompleter {
    public static final Map<String, SpACoreSubCommand> commands = new HashMap<String, SpACoreSubCommand>();
    public static final Map<String, SpACoreSubCommand> aliases = new HashMap<String, SpACoreSubCommand>();

    /**
     * Constructor, adds default commands to the list.
     */
    public SpACoreCommand() {
        new VersionCommand("version", "spacore.command.version", "about", "v");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof BlockCommandSender) {
            // We don't work with command blocks
            return false;
        }

        if (args.length == 0) {
            commands.get("version").runCommand(sender, "version");
        }
        else {
            SpACoreSubCommand subCommand = commands.get(args[0]);

            if (subCommand == null) {
                subCommand = aliases.get(args[0]);
            }

            if (subCommand == null) {
                sender.sendMessage(ChatColor.RED + "Unkown command, please type /" + alias + " help for a list of commands.");
            }

            if (!subCommand.canUseCommand(sender)) {
                sender.sendMessage(ChatColor.RED + "You cannot use this command.");
            }

            if (!subCommand.hasPermission(sender)) {
                sender.sendMessage(ChatColor.RED + "You do not have permissions to use this command.");
            }

            String[] newArgs = new String[args.length - 1];

            System.arraycopy(args, 1, newArgs, 0, args.length - 1);

            subCommand.runCommand(sender, args[0], newArgs);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            ArrayList<String> complements = new ArrayList<String>();

            Set<Entry<String, SpACoreSubCommand>> commandSet = commands.entrySet();

            for (Entry<String, SpACoreSubCommand> entry : commandSet) {
                SpACoreSubCommand subCommand = entry.getValue();

                if (!subCommand.canUseCommand(sender)) {
                    continue;
                }
                if (!subCommand.hasPermission(sender)) {
                    continue;
                }

                if (subCommand.name.toLowerCase().startsWith(args[0].toLowerCase()))
                    complements.add(subCommand.name);

                for (String commandAlias : subCommand.aliases) {
                    if (commandAlias.toLowerCase().startsWith(args[0].toLowerCase()))
                        complements.add(commandAlias);
                }
            }

            return complements;
        }
        else {
            SpACoreSubCommand subCommand = commands.get(args[0]);

            if (subCommand == null) {
                subCommand = aliases.get(args[0]);
            }

            if (subCommand == null) {
                return null;
            }

            if (!subCommand.canUseCommand(sender)) {
                return null;
            }

            if (!subCommand.hasPermission(sender)) {
                return null;
            }

            String[] newArgs = new String[args.length - 1];

            System.arraycopy(args, 1, newArgs, 0, args.length - 1);

            return subCommand.getTabCompleteResults(sender, alias, newArgs);
        }
    }

}
