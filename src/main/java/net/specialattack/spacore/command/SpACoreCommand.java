package net.specialattack.spacore.command;

import net.specialattack.spacore.Consts;
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
        new VersionSubCommand(this, plugin.getDescription(), "version", Consts.PERM_COMMAND_SPA_VERSION, "about", "v");
        new HelpSubCommand(this, "help", Consts.PERM_COMMAND_SPA_HELP, "?");
        new UUIDSubCommand(this, "uuid", Consts.PERM_COMMAND_SPA_UUID, "id");
        new TestSubCommand(this, "test", Consts.PERM_COMMAND_SPA_TEST);
    }

    @Override
    public String getDefaultCommand() {
        return "version";
    }
}
