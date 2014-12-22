package net.specialattack.bukkit.core.command;

import java.util.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class AbstractMultiSubCommand extends AbstractSubCommand implements ISubCommandHolder {

    public final Map<String, AbstractSubCommand> commands;
    public final Map<String, AbstractSubCommand> aliases;
    public String lastAlias;

    public AbstractMultiSubCommand(ISubCommandHolder command, String name, String permission, String... aliases) {
        super(command, name, permission, aliases);
        this.commands = new TreeMap<String, AbstractSubCommand>();
        this.aliases = new TreeMap<String, AbstractSubCommand>();

        this.lastAlias = name;
    }

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
    public void runCommand(CommandSender sender, String alias, String... args) {
        this.lastAlias = alias;

        try {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Unkown command, please type /" + alias + " help for a list of commands.");
            } else {
                AbstractSubCommand subCommand = this.commands.get(args[0]);

                if (subCommand == null) {
                    subCommand = this.aliases.get(args[0]);
                }

                if (subCommand == null) {
                    sender.sendMessage(ChatColor.RED + "Unkown command, please type /" + alias + " help for a list of commands.");
                    return;
                }

                if (!subCommand.canUseCommand(sender)) {
                    sender.sendMessage(ChatColor.RED + "You cannot use this command.");
                    return;
                }

                if (!subCommand.hasPermission(sender)) {
                    sender.sendMessage(ChatColor.RED + "You do not have permissions to use this command.");
                    return;
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
    }

    @Override
    public List<String> getTabCompleteResults(CommandSender sender, String alias, String... args) {
        if (args.length == 1) {
            List<String> possibles = new ArrayList<String>();

            Set<Map.Entry<String, AbstractSubCommand>> commandSet = this.commands.entrySet();

            for (Map.Entry<String, AbstractSubCommand> entry : commandSet) {
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

    @Override
    public String[] getHelpMessage(CommandSender sender) {
        List<String> result = new ArrayList<String>();

        for (Map.Entry<String, AbstractSubCommand> entry : this.commands.entrySet()) {
            AbstractSubCommand subCommand = entry.getValue();

            if (!subCommand.canUseCommand(sender)) {
                continue;
            }
            if (!subCommand.hasPermission(sender)) {
                continue;
            }

            String[] usages = subCommand.getHelpMessage(sender);

            for (String usage : usages) {
                result.add(this.lastAlias + " " + usage);
            }
        }
        return result.toArray(new String[result.size()]);
    }
}
