package net.specialattack.bukkit.core.command.easy.parameter;

import java.util.List;
import net.specialattack.bukkit.core.util.IDataSource;
import org.bukkit.command.CommandSender;

public interface IEasyParameterHandler<T> extends IDataSource<T> {

    /**
     * Parses the parameter.
     *
     * @param sender
     *         The command sender.
     * @param value
     *         The value to parse.
     *
     * @return True if parsing succeeded, false otherwise.
     */
    boolean parse(CommandSender sender, String value);

    /**
     * Returns the last parsed value.
     *
     * @return The last parsed value, or null if parsing failed.
     */
    T getValue();

    /**
     * Gets the tab complete results based on the input.
     *
     * Any tab complete results that do not match the input will be filtered out by the owner of the root command.
     *
     * @param sender
     *         The command sender.
     * @param input
     *         The input string for this parameter.
     *
     * @return The possible tab results or null if it should be a connected player.
     */
    List<String> getTabComplete(CommandSender sender, String input);

    /**
     * Returns whether this parameter consumes all parameters that come after it.
     *
     * @return True if the parameter consumes all other parameters following it, false otherwise.
     */
    boolean takesAll();

    /**
     * Returns the part of the help message that is generated for this parameter.
     *
     * @return The help message of this parameter.
     */
    String getHelpDisplay();

    /**
     * Returns the name of this parameter
     *
     * @return The name of the parameter
     */
    String getName();

}
