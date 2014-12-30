package net.specialattack.bukkit.core.command;

import java.util.*;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.command.*;

/**
 * Main command class, sub commands can be registered here to decrease the
 * amount of commands.
 */
public abstract class AbstractMultiCommand implements CommandExecutor, TabCompleter, ISubCommandHolder {

    public final Map<String, AbstractSubCommand> commands;
    public final Map<String, AbstractSubCommand> aliases;
    public String lastAlias;

    /**
     * Constructor, adds default commands to the list.
     */
    public AbstractMultiCommand() {
        this.commands = new TreeMap<String, AbstractSubCommand>();
        this.aliases = new TreeMap<String, AbstractSubCommand>();
    }

    public abstract String getDefaultCommand();

    @Override
    public void addSubCommand(String name, AbstractSubCommand command) {
        this.commands.put(name, command);
    }

    @Override
    public void addAlias(String name, AbstractSubCommand command) {
        this.aliases.put(name, command);
    }

    @Override
    public Map<String, AbstractSubCommand> getCommands() {
        return this.commands;
    }

    @Override
    public String getLastUsedAlias() {
        return this.lastAlias;
    }

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
            } else {
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
        } catch (Throwable e) {
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
                    Collections.addAll(possibles, subCommand.aliases);
                }
            }

            ArrayList<String> result = new ArrayList<String>();

            for (String possible : possibles) {
                if (possible.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                    result.add(possible);
                }
            }

            return result;
        } else {
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
            } else {
                return null;
            }

            return result;
        }
    }

}
