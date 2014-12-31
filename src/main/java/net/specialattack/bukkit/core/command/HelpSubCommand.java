package net.specialattack.bukkit.core.command;

import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpSubCommand extends AbstractSubCommand {

    public HelpSubCommand(ISubCommandHolder command, String name, String permission, String... aliases) {
        super(command, name, permission, aliases);
        this.finish();
    }

    @Override
    public void runCommand(CommandSender sender) {
        Set<Map.Entry<String, AbstractSubCommand>> commandSet = this.owner.getCommands().entrySet();

        for (Map.Entry<String, AbstractSubCommand> entry : commandSet) {
            AbstractSubCommand subCommand = entry.getValue();

            if (!subCommand.canUseCommand(sender)) {
                continue;
            }
            if (!subCommand.hasPermission(sender)) {
                continue;
            }

            String[] usages = subCommand.getHelpMessage(sender);

            for (int i = 0; i < usages.length; i++) {
                usages[i] = ChatColor.GOLD + "/" + this.owner.getLastUsedAlias() + ChatColor.YELLOW + " " + entry.getKey() + " " + usages[i];
            }

            sender.sendMessage(usages);
        }
    }

}
