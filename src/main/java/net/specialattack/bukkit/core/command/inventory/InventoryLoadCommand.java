package net.specialattack.bukkit.core.command.inventory;

import java.io.IOException;
import net.specialattack.bukkit.core.PlayerStorage;
import net.specialattack.bukkit.core.command.AbstractSubCommand;
import net.specialattack.bukkit.core.command.CommandException;
import net.specialattack.bukkit.core.command.ISubCommandHolder;
import net.specialattack.bukkit.core.command.easy.EasyCollection;
import net.specialattack.bukkit.core.command.easy.parameter.PlayerCollectionEasyParameter;
import net.specialattack.bukkit.core.command.easy.parameter.StringEasyParameter;
import net.specialattack.bukkit.core.util.ChatFormat;
import net.specialattack.bukkit.core.util.Function;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command that displays version info about SpACore etc...
 */
public class InventoryLoadCommand extends AbstractSubCommand {

    private final StringEasyParameter stash;
    private final PlayerCollectionEasyParameter players;

    public InventoryLoadCommand(ISubCommandHolder command, String name, String permissions, String... aliases) {
        super(command, name, permissions, aliases);
        this.addParameter(this.stash = new StringEasyParameter().setName("stash"));
        this.addParameter(this.players = new PlayerCollectionEasyParameter().setTakeAll());
        this.finish();
    }

    @Override
    public void runCommand(final CommandSender sender) {
        final String stash = this.stash.getValue();
        EasyCollection<Player> players = this.players.getValue();

        players.forEach(new Function<Player>() {
            @Override
            public void run(Player player) {
                try {
                    PlayerStorage.apply(player, stash);
                    sender.sendMessage(ChatFormat.format("Player %s loaded from stash %s", ChatColor.GREEN, player.getName(), stash));
                } catch (IOException e) {
                    throw new CommandException("Failed saving player: %s", e, e.getMessage());
                }
            }
        });
    }

}
