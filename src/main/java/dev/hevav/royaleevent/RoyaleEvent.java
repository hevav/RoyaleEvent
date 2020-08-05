package dev.hevav.royaleevent;

import dev.hevav.royaleevent.commands.*;
import dev.hevav.royaleevent.listeners.BlockListener;
import dev.hevav.royaleevent.listeners.InventoryListener;
import dev.hevav.royaleevent.listeners.WeaponListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RoyaleEvent extends JavaPlugin {
    public static FileConfiguration config;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = this.getConfig();

        this.getCommand("royalestart").setExecutor(new RoyaleStart());
        this.getCommand("royalemiddle").setExecutor(new RoyaleMiddle());
        this.getCommand("royalestop").setExecutor(new RoyaleStop());
        this.getCommand("royaleaddchest").setExecutor(new RoyaleAddChest());
        this.getCommand("royaleremovechest").setExecutor(new RoyaleRemoveChest());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new BlockListener(), this);
        pluginManager.registerEvents(new InventoryListener(), this);
        pluginManager.registerEvents(new WeaponListener(), this);
    }

    @Override
    public void onDisable(){
        this.saveConfig();
    }
}
