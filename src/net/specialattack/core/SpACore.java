
package net.specialattack.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SpACore extends JavaPlugin {
    public static SpACore instance;
    public static PluginState state = PluginState.Unloaded;
    protected int lastId;
    protected PluginDescriptionFile pdf;
    private Logger logger;

    public SpACore() {
        super();
        state = PluginState.Initializing;

        instance = this;

        logger = this.getLogger();

        state = PluginState.Initialized;
    }

    @Override
    public void onLoad() {
        state = PluginState.Loading;

        state = PluginState.Loaded;
    }

    @Override
    public void onEnable() {
        state = PluginState.Enabling;

        this.pdf = this.getDescription();

        this.lastId = 0;

        state = PluginState.Enabled;
    }

    @Override
    public void onDisable() {
        state = PluginState.Disabling;

        state = PluginState.Disabled;
    }

    public static int getNextAvailablePlaygroundId() {
        return instance.lastId++;
    }

    public static void log(String message) {
        instance.logger.log(Level.INFO, message);
    }

    public static void log(Level level, String message) {
        instance.logger.log(level, message);
    }

    public static void log(Level level, String message, Throwable throwable) {
        instance.logger.log(level, message, throwable);
    }
}
