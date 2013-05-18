
package net.specialattack.core.command;

import java.util.List;

import net.specialattack.core.SpACore;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Command that displays version info about SpACore etc...
 * 
 * @author heldplayer
 * 
 */
public class VersionCommand extends AbstractSubCommand {

    public VersionCommand(AbstractMultiCommand command, String name, String permissions, String... aliases) {
        super(command, name, permissions, aliases);
    }

    @Override
    public void runCommand(CommandSender sender, String alias, String... args) {
        sender.sendMessage(ChatColor.GRAY + "========== " + ChatColor.GREEN + SpACore.instance.getDescription().getFullName() + ChatColor.GRAY + " ==========");
        sender.sendMessage(ChatColor.YELLOW + "Authors: " + ChatColor.GRAY + " mbl111, heldplayer");
    }

    @Override
    public boolean canUseCommand(CommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompleteResults(CommandSender sender, String alias, String... args) {
        return emptyTabResult;
    }

}
