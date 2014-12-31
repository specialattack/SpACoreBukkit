package net.specialattack.bukkit.core;

import com.mojang.api.profiles.HttpProfileRepository;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.specialattack.bukkit.core.block.Cuboid;
import net.specialattack.bukkit.core.command.SpACoreCommand;
import net.specialattack.bukkit.core.games.IPlaygroundLoader;
import net.specialattack.bukkit.core.games.Playground;
import net.specialattack.bukkit.core.games.PlaygroundPool;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SpACore extends JavaPlugin {

    public static SpACore instance;
    private static PluginState state = PluginState.Unloaded;
    private PluginDescriptionFile pdf;
    private Logger logger;
    private PlaygroundPool playgroundPool;

    public SpACore() {
        super();
        state = PluginState.Initializing;

        instance = this;

        playgroundPool = new PlaygroundPool();

        state = PluginState.Initialized;
    }

    @Override
    public void onLoad() {
        state = PluginState.Loading;

        this.logger = this.getLogger();

        state = PluginState.Loaded;
    }

    @Override
    public void onEnable() {
        state = PluginState.Enabling;

        this.pdf = this.getDescription();

        playgroundPool.onEnable();
        
        SpACoreCommand command = new SpACoreCommand();

        this.getCommand("spacore").setExecutor(command);
        this.getCommand("spacore").setTabCompleter(command);

        log(this.pdf.getFullName() + " is now enabled!");

        state = PluginState.Enabled;
    }

    @Override
    public void onDisable() {
        state = PluginState.Disabling;

        log(this.pdf.getFullName() + " is now disabled!");

        state = PluginState.Disabled;
    }

    private static HttpProfileRepository repository;

    public static HttpProfileRepository getProfileRepository() {
        if (repository == null) {
            repository = new HttpProfileRepository("minecraft");
        }
        return repository;
    }

    public static PluginState getState() {
        return state;
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
