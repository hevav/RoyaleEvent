package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.List;

public class PeriodsHelper {

    private static BukkitScheduler scheduler;

    public static void doPeriod(Server server, World world, Location middleLocation){
        List<HashMap<Object, Object>> borderPeriod = (List<HashMap<Object, Object>>) RoyaleEvent.config.get("periods.worldborder");
        List<HashMap<Object, Object>> dropPeriod = (List<HashMap<Object, Object>>) RoyaleEvent.config.get("periods.loot");
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(middleLocation);

        scheduler = server.getScheduler();
        int tickForBorder = 0;
        for (HashMap<Object, Object> border : borderPeriod){
            int time = (Integer) border.get("time");
            switch ((String) border.get("type")){
                case "radius":
                    scheduler.scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                        int radius = (Integer) border.get("radius");
                        server.broadcastMessage(String.format("%s[RE] Зона сокращается, у вас есть %d секунд чтоб убежать!", ChatColor.GOLD, time));
                        worldBorder.setSize(radius, time);
                    }, tickForBorder);
                    break;
                case "wait":
                    scheduler.scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                        server.broadcastMessage(String.format("%s[RE] Зона сократилась :) Держим спокойствие целых %d секунд", ChatColor.GOLD, time));
                    }, tickForBorder);
                    break;
            }
            tickForBorder += time*20;
        }

        int tickForDrop = 0;
        for (HashMap<Object, Object> drop : dropPeriod){
            int time = (Integer) drop.get("time");
            tickForDrop += time*20;
            switch ((String) drop.get("type")){
                case "airdrop":
                    scheduler.scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                        server.broadcastMessage(String.format("%s[RE] Эйрдроп появится через %d секунд :)", ChatColor.GOLD, time));
                    }, tickForDrop - (time*20));
                    scheduler.scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                        Location location = BlockHelper.getRandomDropPoint(world);
                        location.getBlock().setType(Material.ENDER_CHEST);
                        server.broadcastMessage(String.format("%s[RE] Появился эйрдроп на %d %d %d", ChatColor.GOLD, location.getBlockX(), location.getBlockY(), location.getBlockZ()));
                    }, tickForDrop);
                   break;
                case "wait":
                    scheduler.scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                        server.broadcastMessage((ChatColor.GOLD) + "[RE] Эх а ведь скоро эйрдроп...");
                    }, tickForDrop);
                    break;
            }
        }
    }

    public static void stopPeriod(){
        if(scheduler != null){
            scheduler.cancelAllTasks();
        }
    }
}
