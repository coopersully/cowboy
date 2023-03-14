package me.coopersully.cowboy;

import org.bukkit.plugin.java.JavaPlugin;

public final class Cowboy extends JavaPlugin {

    public static final String permissionPrefix = "cb.";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerMount(), this);
    }

}
