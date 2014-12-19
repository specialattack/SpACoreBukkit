package net.specialattack.bukkit.core;

/**
 * Utility class for plugins to state in what state they are.
 *
 * @author heldplayer
 */
public enum PluginState {

    /**
     * The plugin hasn't even been initialized. No instances of it exist yet.
     */
    Unloaded,
    /**
     * The plugin is busy initializing, this is the start of its constructor. It
     * is unknown if an instance of the plugin exists at the time.
     */
    Initializing,
    /**
     * The plugin has finished initializing. It has reached the end of its
     * constructor. From thhis point on an instance of the plugin should exist.
     */
    Initialized,
    /**
     * The plugin is being loaded, this is the start of onLoad().
     */
    Loading,
    /**
     * The plugin has finished loading. It has reached the end of onLoad().
     */
    Loaded,
    /**
     * The plugin is busy enabling, this is the start of onEnable().
     */
    Enabling,
    /**
     * The plugin has finished enabling. It has reached the end of onEnable()
     * and should be available for calls.
     */
    Enabled,
    /**
     * The plugin is busy disabling, this is the start of onDisable(). It will
     * no longer accept any calls.
     */
    Disabling,
    /**
     * The plugin is disabled. No calls can be made to it anymore and any
     * instances of it should be discarded.
     */
    Disabled,
    /**
     * The plugin encountered a critical error and it is unsure if calls can be
     * made. Some features might break stuff more.
     */
    Errored
}
