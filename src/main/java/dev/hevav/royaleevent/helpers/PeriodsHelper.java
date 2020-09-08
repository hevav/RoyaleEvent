package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
                        server.broadcastMessage(String.format("%s[RE] "+RoyaleEvent.config.getString("strings.bordersStartMoving"), ChatColor.GOLD, time));
                        worldBorder.setSize(radius, time);
                    }, tickForBorder);
                    break;
                case "wait":
                    scheduler.scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                        server.broadcastMessage(String.format("%s[RE] "+RoyaleEvent.config.getString("strings.bordersStopMoving"), ChatColor.GOLD, time));
                    }, tickForBorder);
                    break;
            }
            tickForBorder += time*20 + 100;
        }

        int tickForDrop = 0;
        for (HashMap<Object, Object> drop : dropPeriod){
            int time = (Integer) drop.get("time");
            tickForDrop += time*20;
            switch ((String) drop.get("type")){
                case "airdrop":
                    scheduler.scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                        server.broadcastMessage(String.format("%s[RE] "+RoyaleEvent.config.getString("strings.airdropCountdown"), ChatColor.GOLD, time));
                    }, tickForDrop - (time*20));
                    scheduler.scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                        Location location = BlockHelper.getRandomDropPoint(world);
                        scheduler.scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->
                            location.getBlock().setType(Material.DIAMOND_BLOCK)
                        , 60);
                        location.add(0, 60, 0).getBlock().setType(Material.ANVIL);
                        server.broadcastMessage(String.format("%s[RE] "+RoyaleEvent.config.getString("strings.airdrop"), ChatColor.GOLD, location.getBlockX(), location.getBlockY(), location.getBlockZ()));
                    }, tickForDrop);
                   break;
                case "wait":
                    scheduler.scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                        server.broadcastMessage(String.format("%s[RE] %s", ChatColor.GOLD, RoyaleEvent.config.getString("strings.airdropWarn")));
                    }, tickForDrop);
                    break;
            }
        }

        scheduler.scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
            for (Player player : server.getOnlinePlayers()){
                PlayerInventory inventory = player.getInventory();
                inventory.setChestplate(new ItemStack(Material.AIR));
            }
            for(Block block : BlockHelper.getBlocks(middleLocation.getBlock(), 60, 10, 60))
                block.setType(Material.AIR);
        }, 1800);
    }

    public static void stopPeriod(){
        if(scheduler != null){
            scheduler.cancelAllTasks();
        }
    }
}
