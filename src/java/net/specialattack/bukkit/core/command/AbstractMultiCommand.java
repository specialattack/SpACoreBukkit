
package net.specialattack.bukkit.core.command;

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
public abstract class AbstractMultiCommand implements CommandExecutor, TabCompleter {

    public final Map<String, AbstractSubCommand> commands;
    public final Map<String, AbstractSubCommand> aliases;
    public String lastAlias;

    /**
     * Constructor, adds default commands to the list.
     */
    public AbstractMultiCommand() {
        this.commands = new HashMap<String, AbstractSubCommand>();
        this.aliases = new HashMap<String, AbstractSubCommand>();
    }

    public abstract String getDefaultCommand();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof BlockCommandSender) {
            // We don't work with command blocks
            return false;
        }

        this.lastAlias = alias;

        try {
            if (args.length == 0) {
                this.commands.get(this.getDefaultCommand()).runCommand(sender, "version");
            }
            else {
                AbstractSubCommand subCommand = this.commands.get(args[0]);

                if (subCommand == null) {
                    subCommand = this.aliases.get(args[0]);
                }

                if (subCommand == null) {
                    sender.sendMessage(ChatColor.RED + "Unkown command, please type /" + alias + " help for a list of commands.");
                    return true;
                }

                if (!subCommand.canUseCommand(sender)) {
                    sender.sendMessage(ChatColor.RED + "You cannot use this command.");
                    return true;
                }

                if (!subCommand.hasPermission(sender)) {
                    sender.sendMessage(ChatColor.RED + "You do not have permissions to use this command.");
                    return true;
                }

                String[] newArgs = new String[args.length - 1];

                System.arraycopy(args, 1, newArgs, 0, args.length - 1);

                subCommand.runCommand(sender, args[0], newArgs);
            }
        }
        catch (Throwable e) {
            sender.sendMessage(ChatColor.RED + "An error occoured while performing command");
            sender.sendMessage(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> possibles = new ArrayList<String>();

            Set<Entry<String, AbstractSubCommand>> commandSet = this.commands.entrySet();

            for (Entry<String, AbstractSubCommand> entry : commandSet) {
                AbstractSubCommand subCommand = entry.getValue();

                if (!subCommand.canUseCommand(sender)) {
                    continue;
                }
                if (!subCommand.hasPermission(sender)) {
                    continue;
                }

                possibles.add(subCommand.name);

                if (args[0].length() > 0) {
                    for (String commandAlias : subCommand.aliases) {
                        possibles.add(commandAlias);
                    }
                }
            }

            ArrayList<String> result = new ArrayList<String>();

            for (String possible : possibles) {
                if (possible.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                    result.add(possible);
                }
            }

            return result;
        }
        else {
            AbstractSubCommand subCommand = this.commands.get(args[0]);

            if (subCommand == null) {
                subCommand = this.aliases.get(args[0]);
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

            List<String> possibles = subCommand.getTabCompleteResults(sender, alias, newArgs);

            ArrayList<String> result = new ArrayList<String>();

            if (possibles != null) {
                for (String possible : possibles) {
                    if (possible.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                        result.add(possible);
                    }
                }
            }
            else {
                return null;
            }

            return result;
        }
    }

}
