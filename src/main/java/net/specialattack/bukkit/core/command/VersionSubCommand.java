package net.specialattack.bukkit.core.command;

import java.util.List;
import net.specialattack.bukkit.core.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

/**
 * Command that displays version info about SpACore etc...
 */
public class VersionSubCommand extends AbstractSubCommand {

    private PluginDescriptionFile description;

    public VersionSubCommand(ISubCommandHolder command, PluginDescriptionFile description, String name, String permissions, String... aliases) {
        super(command, name, permissions, aliases);
        this.description = description;
    }

    @Override
    public void runCommand(CommandSender sender, String alias, String... args) {
        sender.sendMessage(ChatColor.GRAY + "========== " + ChatColor.GREEN + this.description.getFullName() + ChatColor.GRAY + " ==========");
        sender.sendMessage(ChatColor.YELLOW + "Authors: " + ChatColor.GRAY + Util.join(this.description.getAuthors()));
    }

    @Override
    public List<String> getTabCompleteResults(CommandSender sender, String alias, String... args) {
        return emptyTabResult;
    }

    @Override
    public String[] getHelpMessage(CommandSender sender) {
        return new String[] { this.name };
    }

}
