package dev.hevav.royaleevent;

import dev.hevav.royaleevent.commands.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class RoyaleEvent extends JavaPlugin {
    public static FileConfiguration config;
    private static RoyaleEvent instance;

    public static RoyaleEvent getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();
        config = this.getConfig();

        this.getCommand("royalestart").setExecutor(new RoyaleStart());
        this.getCommand("royalemiddle").setExecutor(new RoyaleMiddle());
        this.getCommand("royalestop").setExecutor(new RoyaleStop());
    }

    @Override
    public void onDisable(){
        this.saveConfig();
    }
}
