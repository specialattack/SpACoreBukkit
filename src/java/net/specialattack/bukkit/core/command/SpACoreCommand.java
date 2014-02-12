
package net.specialattack.bukkit.core.command;

/**
 * Main command class, sub commands can be registered here to decrease the
 * amount of commands.
 * 
 * @author heldplayer
 * 
 */
public class SpACoreCommand extends AbstractMultiCommand {

    /**
     * Constructor, adds default commands to the list.
     */
    public SpACoreCommand() {
        super();
        new VersionSubCommand(this, "version", "spacore.command.version", "about", "v");
        new InventorySubCommand(this, "inventory", "spacore.command.inventory", "inv", "i");
        new HelpSubCommand(this, "help", "spacore.command.help", "?");
    }

    @Override
    public String getDefaultCommand() {
        return "version";
    }

}
