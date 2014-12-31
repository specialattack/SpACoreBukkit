package net.specialattack.bukkit.core.games;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import net.specialattack.bukkit.core.PluginState;
import net.specialattack.bukkit.core.SpACore;
import net.specialattack.bukkit.core.block.Cuboid;
import net.specialattack.bukkit.core.event.PlaygroundCreateEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mojang.NBT.CompressedStreamTools;
import com.mojang.NBT.NBTTagCompound;

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

			try {
				loadSavedPlaygrounds(type, loader);
			} catch (Exception e) {
				SpACore.log("Failed to load playgrounds for '" + type + "' - " + e.getLocalizedMessage());
			}

		} else {
			throw new RuntimeException("SpACore is not ready to be enabled yet. A faulty plugin is causing this.");
		}
	}

	public Playground createPlayground(String type, Cuboid cuboid, Player player) {
		Playground playground = loaderList.get(type).createInstance(cuboid);

		PlaygroundCreateEvent event = new PlaygroundCreateEvent(playground, player);
		Bukkit.getServer().getPluginManager().callEvent(event);
		
		if (!event.isCancelled()) {
			playgrounds.put(playground.getUniqueId(), playground);
			return playground;
		}
		
		return null;
	}

	private void loadSavedPlaygrounds(String type, IPlaygroundLoader loader) throws FileNotFoundException, IOException {

		final File directory = new File("plugins/SpACore/playgrounds/" + type);

		if (!directory.exists()) {
			SpACore.log(Level.INFO, "Storage folder for  '" + type + "' playgrounds not found. Skipping loading any playgrounds for this type.");
			return;
		}

		for (String file : directory.list(new PlaygroundSaveFilter(directory))) {

			File dataFile = new File(directory, file);

			if (dataFile.isFile()) {
				// We load

				UUID id = UUID.fromString(dataFile.getName().split(".")[0]);
				NBTTagCompound playgroundData = CompressedStreamTools.readCompressed(new FileInputStream(dataFile));

				Playground playground = loader.loadSavedPlayground(id, playgroundData);

				playgrounds.put(playground.getUniqueId(), playground);
			}
		}

	}

	private class PlaygroundSaveFilter implements FilenameFilter {

		private File currentDir;

		public PlaygroundSaveFilter(File currentDir) {
			this.currentDir = currentDir;
		}

		@Override
		public boolean accept(File dir, String name) {
			return dir == currentDir && name.endsWith(".pg");
		}

	}

}
