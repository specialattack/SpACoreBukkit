
package net.specialattack.core;

import org.bukkit.plugin.java.JavaPlugin;

public class SpACore extends JavaPlugin {
    public static SpACore instance;
    public static PluginState state = PluginState.Unloaded;

    public SpACore() {
        super();
        state = PluginState.Initializing;
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

        state = PluginState.Enabled;
    }

    @Override
    public void onDisable() {
        state = PluginState.Disabling;

        state = PluginState.Disabled;
    }
}
