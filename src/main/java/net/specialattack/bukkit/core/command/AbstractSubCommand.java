package net.specialattack.bukkit.core.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.CommandSender;

/**
 * Sub Command class for SpACoreCommand
 *
 * @author Mitchel
 * @see SpACoreCommand
 */
public abstract class AbstractSubCommand {

    public static final List<String> emptyTabResult = Collections.emptyList();
    public static final List<String> trueFalseResult = Collections.unmodifiableList(Arrays.asList("true", "false"));

    private String permission;
    protected final String name;
    protected final String[] aliases;
    public final AbstractMultiCommand owner;

    /**
     * Constructor of the base sub command.
     *
     * @param name
     *         The name of the sub command, by which is should always be
     *         accessible.
     * @param permission
     *         The permission required to use this sub command.
     * @param aliases
     *         Aliases for this sub command.
     */
    public AbstractSubCommand(AbstractMultiCommand command, String name, String permission, String... aliases) {
        this.permission = permission;
        this.name = name;
        this.aliases = aliases;
        this.owner = command;

        command.commands.put(name, this);

        for (String alias : aliases) {
            command.aliases.put(alias, this);
        }
    }

    /**
     * Method to check if a CommandSender has permission to use this sub command
     *
     * @param sender
     *         The CommandSender to check
     *
     * @return True if the CommandSender has permission, false otherwise.
     */
    public boolean hasPermission(CommandSender sender) {
        if (sender.hasPermission("spacore.command.*")) {
            return true;
        }

        return sender.hasPermission(this.permission);
    }

    public boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission(this.permission + "." + permission);
    }

    /**
     * Method that runs the sub command.
     *
     * @param sender
     *         The sender of the command.
     * @param alias
     *         The used alias for the command.
     * @param args
     *         The arguments.
     */
    public abstract void runCommand(CommandSender sender, String alias, String... args);

    /**
     * Method to determine if the sub command can be used by specified
     * CommandSender
     *
     * @param sender
     *         The sender who should be checked.
     *
     * @return True if the CommandSender can use this sub command. False
     * otherwise.
     */
    public abstract boolean canUseCommand(CommandSender sender);

    public abstract List<String> getTabCompleteResults(CommandSender sender, String alias, String... args);

    public abstract String[] getHelpMessage();

}
