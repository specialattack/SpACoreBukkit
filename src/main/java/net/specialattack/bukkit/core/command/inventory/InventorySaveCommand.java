package net.specialattack.bukkit.core.command.inventory;

import java.io.FileNotFoundException;
import java.io.IOException;
import net.specialattack.bukkit.core.PlayerStorage;
import net.specialattack.bukkit.core.command.AbstractSubCommand;
import net.specialattack.bukkit.core.command.CommandException;
import net.specialattack.bukkit.core.command.ISubCommandHolder;
import net.specialattack.bukkit.core.command.easy.EasyCollection;
import net.specialattack.bukkit.core.command.easy.parameter.PlayerCollectionEasyParameter;
import net.specialattack.bukkit.core.command.easy.parameter.StringEasyParameter;
import net.specialattack.bukkit.core.util.ChatFormat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command that displays version info about SpACore etc...
 */
public class InventorySaveCommand extends AbstractSubCommand {

    private final StringEasyParameter stash;
    private final PlayerCollectionEasyParameter players;

    public InventorySaveCommand(ISubCommandHolder command, String name, String permissions, String... aliases) {
        super(command, name, permissions, aliases);
        this.addParameter(this.stash = new StringEasyParameter().setName("stash"));
        this.addParameter(this.players = new PlayerCollectionEasyParameter().setTakeAll());
        this.finish();
    }

    @Override
    public void runCommand(final CommandSender sender) {
        final String stash = this.stash.get();
        EasyCollection<Player> players = this.players.get();

        players.forEach(player -> {
            try {
                PlayerStorage.store(player, stash);
                sender.sendMessage(ChatFormat.format("Player %s saved to stash %s", ChatColor.GREEN, player.getName(), stash));
            } catch (FileNotFoundException e) {
                sender.sendMessage(ChatColor.RED + "Player has not been saved before.");
            } catch (IOException e) {
                throw new CommandException("Failed loading player: %s", e, e.getMessage());
            }
        });
    }
}
