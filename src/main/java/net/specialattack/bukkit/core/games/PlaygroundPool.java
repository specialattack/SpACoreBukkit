package net.specialattack.bukkit.core.games;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import net.specialattack.bukkit.core.PluginState;
import net.specialattack.bukkit.core.SpACore;
import net.specialattack.bukkit.core.block.Cuboid;

/**
 * Author: Matt Date: 30 Dec 2014 Time: 11:38:14 pm (C) mbl111 2014
 **/

public class PlaygroundPool {

	private HashMap<String, IPlaygroundLoader> loaderList;
	private HashMap<UUID, Playground> playgrounds;

	public PlaygroundPool() {

		this.loaderList = new HashMap<String, IPlaygroundLoader>();
		this.playgrounds = new HashMap<UUID, Playground>();
	}

	public void onEnable() {
		this.loaderList.clear();
	}

	public void registerPlaygroundType(String type, IPlaygroundLoader loader) {
		if (SpACore.getState() == PluginState.Enabled) {
			loaderList.put(type, loader);

			loadSavedPlaygrounds(type);

		} else {
			throw new RuntimeException("SpACore is not ready to be enabled yet. A faulty plugin is causing this.");
		}
	}

	public Playground createPlayground(String type, Cuboid cuboid) {
		return loaderList.get(type).createInstance(cuboid);
	}

	private void loadSavedPlaygrounds(String type) {

		File directory = new File("plugins/SpACore/playgrounds/" + type);

		if (!directory.exists()) {
			SpACore.log(Level.INFO, "Storage folder for  '" + type + "' playgrounds not found. Skipping loading any playgrounds for this type.");
			return;
		}

	}

}
