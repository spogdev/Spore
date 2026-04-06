package dev.spog.spore;

import org.bukkit.plugin.Plugin;

public class Spore {
    private static Plugin plugin;

    public static void init(Plugin inputPlugin) {
        plugin = inputPlugin;
        plugin.getServer().getLogger().info("[Spore] Initialized by " + inputPlugin.getName());
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
