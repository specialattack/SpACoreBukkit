package net.specialattack.bukkit.core.command;

import net.specialattack.bukkit.core.SpACore;

/**
 * Main command class, sub commands can be registered here to decrease the
 * amount of commands.
 */
public class SpACoreCommand extends AbstractMultiCommand {

    /**
     * Constructor, adds default commands to the list.
     */
    public SpACoreCommand() {
        super();
        new VersionSubCommand(this, SpACore.instance.getDescription(), "version", "spacore.command.version", "about", "v");
        new InventorySubCommand(this, "inventory", "spacore.command.inventory", "inv", "i");
        new HelpSubCommand(this, "help", "spacore.command.help", "?");
        new UUIDSubCommand(this, "uuid", "spacore.command.uuid", "id");
        new TestSubCommand(this, "test", "spacore.command.test");
    }

    @Override
    public String getDefaultCommand() {
        return "version";
    }
}
