package net.specialattack.spacore;

import com.mojang.api.profiles.HttpProfileRepository;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.specialattack.spacore.command.SpACoreCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SpACore extends JavaPlugin {

    private PluginDescriptionFile pdf;
    private Logger logger;

    private HttpProfileRepository repository;

    @Override
    public void onLoad() {
        this.logger = this.getLogger();
    }

    @Override
    public void onEnable() {
        this.pdf = this.getDescription();

        SpACoreCommand command = new SpACoreCommand(this);

        this.getCommand("spacore").setExecutor(command);
        this.getCommand("spacore").setTabCompleter(command);

        this.log(this.pdf.getFullName() + " is now enabled!");
    }

    @Override
    public void onDisable() {
        this.log(this.pdf.getFullName() + " is now disabled!");
    }

    public static HttpProfileRepository getProfileRepository() {
        SpACore instance = (SpACore) Bukkit.getPluginManager().getPlugin("spacore");
        if (instance.repository == null) {
            instance.repository = new HttpProfileRepository("minecraft");
        }
        return instance.repository;
    }

    public void log(String message) {
        this.logger.log(Level.INFO, message);
    }

    public void log(Level level, String message) {
        this.logger.log(level, message);
    }

    public void log(Level level, String message, Throwable throwable) {
        this.logger.log(level, message, throwable);
    }
}
