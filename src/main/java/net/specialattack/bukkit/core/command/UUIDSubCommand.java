package net.specialattack.bukkit.core.command;

import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;
import java.util.List;
import net.specialattack.bukkit.core.SpACore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command that finds the UUID of a player
 *
 * @author heldplayer
 */
public class UUIDSubCommand extends AbstractSubCommand {

    public UUIDSubCommand(AbstractMultiCommand command, String name, String permissions, String... aliases) {
        super(command, name, permissions, aliases);
    }

    @Override
    public void runCommand(CommandSender sender, String alias, String... args) {
        String[] names = args;
        if (args.length == 0) {
            if (sender instanceof Player) {
                names = new String[] { sender.getName() };
            } else {
                sender.sendMessage(ChatColor.RED + "Please supply a player name as an argument");
                return;
            }
        }

        HttpProfileRepository repository = SpACore.getProfileRepository();
        Profile[] profiles = repository.findProfilesByNames(names);

        if (profiles.length == 0) {
            sender.sendMessage("Found 0 results");
        }

        for (Profile profile : profiles) {
            sender.sendMessage(String.format("'%s' = '%s'", profile.getUUID(), profile.getName()));
        }
    }

    @Override
    public boolean canUseCommand(CommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompleteResults(CommandSender sender, String alias, String... args) {
        return null;
    }

    @Override
    public String[] getHelpMessage() {
        return new String[] { this.name + " [player1 [player2 [...]]]" };
    }

}
