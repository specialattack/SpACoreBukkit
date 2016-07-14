package net.specialattack.bukkit.core.command;

import net.specialattack.bukkit.core.command.easy.AbstractEasyCommand;
import org.bukkit.command.CommandSender;

/**
 * Sub Command class for SpACoreCommand
 *
 * @see SpACoreCommand
 */
public abstract class AbstractSubCommand extends AbstractEasyCommand {

    private String permission;
    protected final String name;
    protected final String[] aliases;
    public final ISubCommandHolder owner;

    /**
     * Constructor of the base sub command.
     *
     * @param name
     *         The name of the sub command, by which is should always beaccessible.
     * @param permission
     *         The permission required to use this sub command.
     * @param aliases
     *         Aliases for this sub command.
     */
    public AbstractSubCommand(ISubCommandHolder command, String name, String permission, String... aliases) {
        this.permission = permission;
        this.name = name;
        this.aliases = aliases;
        this.owner = command;

        command.addSubCommand(name, this);

        for (String alias : aliases) {
            command.addAlias(alias, this);
        }
    }

    /**
     * Method to check if a CommandSender has permission to use this sub command.
     *
     * @param sender
     *         The CommandSender to check.
     *
     * @return True if the CommandSender has permission, false otherwise.
     */
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(this.permission);
    }

    /**
     * Method to determine if the sub command can be used by the given CommandSender.
     *
     * @param sender
     *         The sender who should be checked.
     *
     * @return True if the CommandSender can use this sub command. False otherwise.
     */
    public boolean canUseCommand(CommandSender sender) {
        return true;
    }
}
