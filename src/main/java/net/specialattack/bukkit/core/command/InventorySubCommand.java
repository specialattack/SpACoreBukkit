package net.specialattack.bukkit.core.command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import net.specialattack.bukkit.core.PlayerStorage;
import net.specialattack.bukkit.core.SpACore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command that displays version info about SpACore etc...
 *
 * @author heldplayer
 */
public class InventorySubCommand extends AbstractSubCommand {

	private final List<String> possibles;

	public InventorySubCommand(ISubCommandHolder command, String name, String permissions, String... aliases) {
		super(command, name, permissions, aliases);
		this.possibles = new ArrayList<String>();
		this.possibles.add("load");
		this.possibles.add("save");
	}

	@Override
	public void runCommand(CommandSender sender, String alias, String... args) {
		Player player = (Player) sender;

		if (args.length == 1) {
			try {
				if (args[0].equalsIgnoreCase("save")) {
					PlayerStorage.store(player, PlayerStorage.DEFAULT_STASH);
					sender.sendMessage(ChatColor.GREEN + "Player " + player.getName() + " saved to stash " + PlayerStorage.DEFAULT_STASH);
				} else if (args[0].equalsIgnoreCase("load")) {
					PlayerStorage.apply(player, PlayerStorage.DEFAULT_STASH);
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid syntax. Correct syntax:");
					sender.sendMessage(ChatColor.GRAY + "/" + alias + " <save|load> [stash] [username]");
				}
			} catch (FileNotFoundException e) {
				sender.sendMessage(ChatColor.RED + "Player has not been saved before.");
			} catch (IOException e) {
				sender.sendMessage(ChatColor.RED + "Error while proccessing command: " + e.getMessage());
				SpACore.log(Level.WARNING, "Error while proccessing command", e);
			}
		} else if (args.length == 2) {
			// Store myself in a stash
			try {
				String stash = args[1];
				
				if (args[0].equalsIgnoreCase("save")) {
					PlayerStorage.store(player, stash);
					sender.sendMessage(ChatColor.GREEN + "Player " + player.getName() + " saved to stash " + stash);
				} else if (args[0].equalsIgnoreCase("load")) {
					PlayerStorage.apply(player, stash);
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid syntax. Correct syntax:");
					sender.sendMessage(ChatColor.GRAY + "/" + alias + " <save|load> [stash] [username]");
				}
			} catch (FileNotFoundException e) {
				sender.sendMessage(ChatColor.RED + "Player has not been saved before.");
			} catch (IOException e) {
				sender.sendMessage(ChatColor.RED + "Error while proccessing command: " + e.getMessage());
				SpACore.log(Level.WARNING, "Error while proccessing command", e);
			}

		} else if (args.length == 3) {
			try {
				player = Bukkit.getPlayer(args[2]);
				if (player == null) {
					sender.sendMessage(ChatColor.RED + "Player not online.");
					return;
				}
				
				String stash = args[1];

				if (args[0].equalsIgnoreCase("save")) {
					PlayerStorage.store(player, stash);
					sender.sendMessage(ChatColor.GREEN + "Player " + player.getName() + " saved to stash " + stash);
				} else if (args[0].equalsIgnoreCase("load")) {
					PlayerStorage.apply(player, stash);
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid syntax. Correct syntax:");
					sender.sendMessage(ChatColor.GRAY + "/" + alias + " <save|load> [stash] [username]");
				}
			} catch (FileNotFoundException e) {
				sender.sendMessage(ChatColor.RED + "Player has not been saved before.");
			} catch (IOException e) {
				sender.sendMessage(ChatColor.RED + "Error while proccessing command: " + e.getMessage());
				SpACore.log(Level.WARNING, "Error while proccessing command", e);
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Invalid syntax. Correct syntax:");
			sender.sendMessage(ChatColor.GRAY + "/" + alias + " <save|load> [stash] [username]");
		}
	}

	@Override
	public boolean canUseCommand(CommandSender sender) {
		return sender instanceof Player;
	}

	@Override
	public List<String> getTabCompleteResults(CommandSender sender, String alias, String... args) {
		if (args.length == 2) {
			return null;
		} else if (args.length > 2) {
			return emptyTabResult;
		}

		return this.possibles;
	}

	@Override
	public String[] getHelpMessage(CommandSender sender) {
		return new String[] { this.name + " load/save [player]" };
	}

}
