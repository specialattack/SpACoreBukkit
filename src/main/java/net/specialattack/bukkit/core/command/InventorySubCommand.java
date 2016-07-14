package net.specialattack.bukkit.core.command;

import net.specialattack.bukkit.core.command.inventory.InventoryLoadCommand;
import net.specialattack.bukkit.core.command.inventory.InventorySaveCommand;

/**
 * Command that displays version info about SpACore etc...
 */
public class InventorySubCommand extends AbstractMultiSubCommand {

    public InventorySubCommand(ISubCommandHolder command, String name, String permissions, String... aliases) {
        super(command, name, permissions, aliases);
        new InventorySaveCommand(this, "save", "spacore.command.inventory.save");
        new InventoryLoadCommand(this, "load", "spacore.command.inventory.load");
    }
}
