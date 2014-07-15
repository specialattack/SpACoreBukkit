
package net.specialattack.bukkit.core;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.specialattack.bukkit.core.block.Cuboid;
import net.specialattack.bukkit.core.command.SpACoreCommand;
import net.specialattack.bukkit.core.games.IPlaygroundLoader;
import net.specialattack.bukkit.core.games.Playground;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;

public class SpACore extends JavaPlugin {

    public static SpACore instance;
    private static PluginState state = PluginState.Unloaded;
    private int lastId;
    private PluginDescriptionFile pdf;
    private Logger logger;
    private HashMap<String, IPlaygroundLoader> loaderList;

    public SpACore() {
        super();
        state = PluginState.Initializing;

        instance = this;

        this.loaderList = new HashMap<String, IPlaygroundLoader>();

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

        this.lastId = 0;
        this.loaderList.clear();

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

    private static boolean uuidMethodExists = true;
    private static HashMap<String, String> uuidMap = new HashMap<String, String>();

    // Bridge method
    @Deprecated
    public static Player getPlayer(UUID uuid) {
        if (uuidMethodExists) {
            try {
                return Bukkit.getPlayer(uuid);
            }
            catch (NoSuchMethodError e) {
                uuidMethodExists = false;
            }
        }

        // Check the hashmap first
        String replaced = uuid.toString().replaceAll("-", "");
        String name = uuidMap.get(replaced);
        if (name != null) {
            // That was easy
            return Bukkit.getPlayer(name);
        }

        // Try a little harder
        Player[] players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            if (player.getUniqueId().equals(uuid)) {
                uuidMap.put(replaced, player.getName());
                return player;
            }
        }

        // Gotta try really hard now
        HttpProfileRepository repository = getProfileRepository();
        Profile profile = repository.findProfileByUUID(replaced);
        if (profile == null) {
            // We failed
            return null;
        }
        uuidMap.put(replaced, profile.getName());
        // This should be it
        return Bukkit.getPlayer(profile.getName());
    }

    public static PluginState getState() {
        return state;
    }

    public static int getNextAvailablePlaygroundId() {
        return instance.lastId++;
    }

    public static void registerPlaygroundType(String type, IPlaygroundLoader loader) {
        if (state == PluginState.Enabled) {
            instance.loaderList.put(type, loader);
        }
        else {
            throw new RuntimeException("SpACore is not ready to be enabled yet. A faulty plugin is causing this.");
        }
    }

    public static Playground loadPlayground(String type, Cuboid cuboid) {
        return instance.loaderList.get(type).createInstance(cuboid);
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
