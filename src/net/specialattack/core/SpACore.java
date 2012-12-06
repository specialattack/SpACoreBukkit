package net.specialattack.core;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.specialattack.core.block.Cuboid;
import net.specialattack.core.games.Playground;
import net.specialattack.core.games.PlaygroundLoader;
import net.specialattack.core.profiler.Profiler;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SpACore extends JavaPlugin {
	public static SpACore instance;
	private static PluginState state = PluginState.Unloaded;
	private int lastId;
	private PluginDescriptionFile pdf;
	private Logger logger;
	private HashMap<String, PlaygroundLoader> loaderList;
	public Profiler profiler;

	public SpACore() {
		super();
		state = PluginState.Initializing;

		instance = this;

		logger = this.getLogger();

		profiler = new Profiler();

		loaderList = new HashMap<String, PlaygroundLoader>();

		state = PluginState.Initialized;
	}

	@Override
	public void onLoad() {
		profiler.startSection("onLoad");
		state = PluginState.Loading;

		state = PluginState.Loaded;
		profiler.endSection();
	}

	@Override
	public void onEnable() {
		profiler.startSection("onEnable");

		state = PluginState.Enabling;

		this.pdf = this.getDescription();

		this.lastId = 0;
		this.loaderList.clear();

		log(this.pdf.getFullName() + " is now enabled!");

		state = PluginState.Enabled;

		profiler.endSection();
	}

	@Override
	public void onDisable() {
		profiler.startSection("onDisable");

		state = PluginState.Disabling;

		log(this.pdf.getFullName() + " is now disabled!");

		state = PluginState.Disabled;

		profiler.endSection();
	}

	public static PluginState getState() {
		return state;
	}

	public static int getNextAvailablePlaygroundId() {
		return instance.lastId++;
	}

	public static void registerPlaygroundType(String type, PlaygroundLoader loader) {
		if (state == PluginState.Enabled) {
			instance.loaderList.put(type, loader);
		} else {
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
