package net.specialattack.spacore.command;

import net.specialattack.spacore.SpACore;
import net.specialattack.spacore.api.command.AbstractMultiCommand;

/**
 * Main command class, sub commands can be registered here to decrease the
 * amount of commands.
 */
public class SpACoreCommand extends AbstractMultiCommand {

    /**
     * Constructor, adds default commands to the list.
     */
    public SpACoreCommand(SpACore plugin) {
        super();
        new VersionSubCommand(this, plugin.getDescription(), "version", "spacore.command.version", "about", "v");
        new HelpSubCommand(this, "help", "spacore.command.help", "?");
        new UUIDSubCommand(this, plugin, "uuid", "spacore.command.uuid", "id");
        new TestSubCommand(this, "test", "spacore.command.test");
    }

    @Override
    public String getDefaultCommand() {
        return "version";
    }
}
