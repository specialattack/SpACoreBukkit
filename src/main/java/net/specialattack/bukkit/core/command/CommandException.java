package net.specialattack.bukkit.core.command;

public class CommandException extends RuntimeException {

    public final String message;
    public final Object[] params;

    public CommandException(String message, Throwable cause, Object... params) {
        super(message, cause);
        this.message = message;
        this.params = params;
    }

    public CommandException(String message, Object... params) {
        super(message);
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
