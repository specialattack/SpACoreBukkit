package net.specialattack.bukkit.core.command;

public class CommandException extends RuntimeException {

    private static final long serialVersionUID = -7098123466107997666L;

    public final String message;
    public final Object[] params;

    public CommandException(String message, Throwable cause, Object... params) {
        super(String.format(message, params), cause);
        this.message = message;
        this.params = params;
    }

    public CommandException(String message, Object... params) {
        super(String.format(message, params));
        this.message = message;
        this.params = params;
    }

    public CommandException(Throwable cause) {
        super(cause);
        this.message = null;
        this.params = null;
    }

    public CommandException() {
        this.message = null;
        this.params = null;
    }
}
