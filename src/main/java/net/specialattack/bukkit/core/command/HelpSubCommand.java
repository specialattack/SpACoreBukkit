package net.specialattack.bukkit.core.command;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpSubCommand extends AbstractSubCommand {

    public HelpSubCommand(ISubCommandHolder command, String name, String permission, String... aliases) {
        super(command, name, permission, aliases);
    }

    @Override
    public void runCommand(CommandSender sender, String alias, String... args) {
        Set<Entry<String, AbstractSubCommand>> commandSet = this.owner.getCommands().entrySet();

        for (Entry<String, AbstractSubCommand> entry : commandSet) {
            AbstractSubCommand subCommand = entry.getValue();

            if (!subCommand.canUseCommand(sender)) {
                continue;
            }
            if (!subCommand.hasPermission(sender)) {
                continue;
            }

            String[] usages = subCommand.getHelpMessage(sender);

            for (int i = 0; i < usages.length; i++) {
                usages[i] = ChatColor.DARK_GRAY + "/" + this.owner.getLastUsedAlias() + " " + ChatColor.GRAY + usages[i];
            }

            sender.sendMessage(usages);
        }
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
