package net.specialattack.spacore.api.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import net.specialattack.spacore.util.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class AbstractMultiSubCommand extends AbstractSubCommand implements ISubCommandHolder {

    public final Map<String, AbstractSubCommand> commands;
    public final Map<String, AbstractSubCommand> aliases;
    public String lastAlias;

    public AbstractMultiSubCommand(ISubCommandHolder command, String name, String permission, String... aliases) {
        super(command, name, permission, aliases);
        this.commands = new TreeMap<>();
        this.aliases = new TreeMap<>();

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
    public void parseParameters(CommandSender sender, String alias, String... args) {
        this.lastAlias = alias;

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

            subCommand.parseParameters(sender, args[0], newArgs);
        }
    }

    @Override
    public final void runCommand(CommandSender sender) {
        // We don't do anything with this
    }

    @Override
    public List<String> getTabCompleteResults(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> possibles = new ArrayList<>();

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

            String lower = args[args.length - 1].toLowerCase();

            return possibles.stream().map(String::toLowerCase).filter(possible -> possible.startsWith(lower)).collect(Collectors.toList());
        } else {
            AbstractSubCommand subCommand = this.commands.get(args[0]);

            if (subCommand == null) {
                subCommand = this.aliases.get(args[0]);
            }

            if (subCommand == null) {
                return ChatUtil.TAB_RESULT_EMPTY;
            }

            if (!subCommand.canUseCommand(sender)) {
                return ChatUtil.TAB_RESULT_EMPTY;
            }

            if (!subCommand.hasPermission(sender)) {
                return ChatUtil.TAB_RESULT_EMPTY;
            }

            String[] newArgs = new String[args.length - 1];

            System.arraycopy(args, 1, newArgs, 0, args.length - 1);

            List<String> possibles = subCommand.getTabCompleteResults(sender, newArgs);

            if (possibles != null) {
                String lower = args[args.length - 1].toLowerCase();
                return possibles.stream().map(String::toLowerCase).filter(possible -> possible.startsWith(lower)).collect(Collectors.toList());
            } else {
                return null;
            }
        }
    }

    @Override
    public String[] getHelpMessage(CommandSender sender) {
        Set<Map.Entry<String, AbstractSubCommand>> commandSet = this.commands.entrySet();

        List<String> result = new ArrayList<>();

        for (Map.Entry<String, AbstractSubCommand> entry : commandSet) {
            AbstractSubCommand subCommand = entry.getValue();

            if (!subCommand.canUseCommand(sender)) {
                continue;
            }
            if (!subCommand.hasPermission(sender)) {
                continue;
            }

            String[] usages = subCommand.getHelpMessage(sender);

            for (String usage : usages) {
                result.add(entry.getKey() + " " + usage);
            }
        }

        return result.toArray(new String[result.size()]);
    }
}
