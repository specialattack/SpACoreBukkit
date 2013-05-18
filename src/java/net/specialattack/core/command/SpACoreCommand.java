
package net.specialattack.core.command;

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
        new VersionCommand(this, "version", "spacore.command.version", "about", "v");
        new InventoryCommand(this, "inventory", "spacore.command.inventory", "inv", "i");
    }

    @Override
    public String getDefaultCommand() {
        return "version";
    }

}
