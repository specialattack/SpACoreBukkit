package net.specialattack.spacore.command;

import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;
import java.util.List;
import net.specialattack.spacore.SpACore;
import net.specialattack.spacore.api.command.AbstractSubCommand;
import net.specialattack.spacore.api.command.ISubCommandHolder;
import net.specialattack.spacore.api.command.parameter.StringCollectionEasyParameter;
import org.bukkit.command.CommandSender;

/**
 * Command that finds the UUID of a player
 */
public class UUIDSubCommand extends AbstractSubCommand {

    private final StringCollectionEasyParameter players;

    public UUIDSubCommand(ISubCommandHolder command, String name, String permissions, String... aliases) {
        super(command, name, permissions, aliases);
        this.addParameter(this.players = new StringCollectionEasyParameter() {
            @Override
            public List<String> getTabComplete(CommandSender sender, String input) {
                return null; // Tab completion is desired in this case
            }
        }.setName("name"));
        this.finish();
    }

    @Override
    public void runCommand(CommandSender sender) {
        List<String> players = this.players.get();

        HttpProfileRepository repository = SpACore.getProfileRepository();
        Profile[] profiles = repository.findProfilesByNames(players.toArray(new String[players.size()]));

        if (profiles.length == 0) {
            sender.sendMessage("Found no results");
        }

        for (Profile profile : profiles) {
            sender.sendMessage(String.format("'%s' = '%s'", profile.getUUID(), profile.getName()));
        }
    }
}
