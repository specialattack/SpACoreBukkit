package net.specialattack.spacore.api.command;

import java.util.ArrayList;
import java.util.List;
import net.specialattack.spacore.api.command.parameter.IEasyParameterHandler;
import net.specialattack.spacore.util.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class AbstractEasyCommand {

    private List<IEasyParameterHandler<?>> parameters;
    private boolean lastTakesAll;
    private boolean finished;
    private String help;

    public AbstractEasyCommand() {
        this.parameters = new ArrayList<>();
    }

    /**
     * Adds a parameter to this command for parsing, this should be done in the constructor of the command.
     *
     * @param parameter
     *         The parameter to add to the command
     */
    public void addParameter(IEasyParameterHandler<?> parameter) {
        if (this.lastTakesAll) {
            throw new IllegalStateException("Can't add any more parameters as the last added parameter takes all.");
        }
        if (this.finished) {
            throw new IllegalStateException("Can't add any more parameters as the command has been finished.");
        }
        this.parameters.add(parameter);
        if (parameter.takesAll()) {
            this.lastTakesAll = true;
        }
    }

    /**
     * Finishes up the command and generates the help message
     */
    public void finish() {
        this.finished = true;

        this.help = ChatColor.GRAY.toString();

        for (int i = 0; i < this.parameters.size(); i++) {
            if (i != 0) {
                this.help += " ";
            }

            this.help += this.parameters.get(i).getHelpDisplay();
        }
    }

    /**
     * Gives the parameters to the command for parsing, this fills in the parameters and runs the command.
     *
     * @param sender
     *         The sender of the command.
     * @param alias
     *         The used alias for the command.
     * @param params
     *         The parameters of the command.
     *
     * @throws CommandException
     *         When there was a problem parsing parameters (usually user error)
     */
    public void parseParameters(CommandSender sender, String alias, String[] params) {
        if (!this.finished) {
            throw new IllegalStateException("The command hasn't been finished yet, it can't be used!");
        }
        if (this.lastTakesAll) {
            if (params.length < this.parameters.size()) {
                throw new CommandException("Expected %s or more parameters.", this.parameters.size());
            }
        } else {
            if (params.length != this.parameters.size()) {
                throw new CommandException("Expected %s parameters. No more, no less.", this.parameters.size());
            }
        }

        for (int i = 0; i < this.parameters.size(); i++) {
            IEasyParameterHandler<?> parameter = this.parameters.get(i);
            String param = params[i];
            if (i + 1 == this.parameters.size()) {
                for (int j = i + 1; j < params.length; j++) {
                    param += " " + params[j];
                }
            }
            if (!parameter.parse(sender, param)) {
                throw new CommandException("Failed parsing parameter %s: %s", i + 1, param);
            }
        }

        this.runCommand(sender);
    }

    /**
     * Method that runs the command. The parameters registered to the command will be filled in at this point.
     *
     * @param sender
     *         The sender of the command.
     */
    public abstract void runCommand(CommandSender sender);

    public List<String> getTabCompleteResults(CommandSender sender, String[] params) {
        for (int i = 0; i < this.parameters.size() - 1 && i < params.length - 1; i++) {
            IEasyParameterHandler<?> parameter = this.parameters.get(i);
            String param = params[i];
            if (i + 1 == this.parameters.size()) {
                for (int j = i + 1; j < params.length; j++) {
                    param += " " + params[j];
                }
            }
            if (!parameter.parse(sender, param)) {
                return ChatUtil.TAB_RESULT_EMPTY;
            }
        }

        IEasyParameterHandler<?> handler = null;
        String param = params[params.length - 1];
        if (params.length <= this.parameters.size()) {
            handler = this.parameters.get(params.length - 1);
        } else if (this.lastTakesAll) {
            handler = this.parameters.get(this.parameters.size() - 1);
        }

        if (handler != null) {
            return handler.getTabComplete(sender, param);
        } else {
            return ChatUtil.TAB_RESULT_EMPTY;
        }
    }

    public String[] getHelpMessage(CommandSender sender) {
        return new String[] { this.help };
    }
}
