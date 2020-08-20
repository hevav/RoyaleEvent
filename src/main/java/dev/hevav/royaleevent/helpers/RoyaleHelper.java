package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class RoyaleHelper {

    private static boolean started = false;
    private static HashMap<String, Integer> killerStats;

    public static void initRoyale(Server server, World initWorld){
        server.broadcastMessage(ChatColor.GOLD + "[RE] Ивент запущен");
        server.broadcastMessage(ChatColor.GOLD + "[RE] Плагин написал hevav");
        Location middleLocation = new Location(initWorld, (Integer) RoyaleEvent.config.get("midXcord"), (Integer) RoyaleEvent.config.get("midYcord"), (Integer) RoyaleEvent.config.get("midZcord"));
        for(Player player : server.getOnlinePlayers()){
            PlayerInventory inventory = player.getInventory();
            inventory.clear();
            inventory.setMaxStackSize(128);

            ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
            ItemMeta pickaxeMeta = pickaxe.getItemMeta();
            pickaxeMeta.setUnbreakable(true);
            pickaxeMeta.setDisplayName("Ломалка");
            pickaxe.setItemMeta(pickaxeMeta);

            ItemStack wood = new ItemStack(Material.WOOD);
            ItemMeta woodMeta = wood.getItemMeta();
            woodMeta.setDisplayName("Поставить доски(ЛКМ для смены)");
            wood.setItemMeta(woodMeta);

            ItemStack brick = new ItemStack(Material.BRICK);
            ItemMeta brickMeta = brick.getItemMeta();
            brickMeta.setDisplayName("Поставить кирпич(ЛКМ для смены)");
            brick.setItemMeta(brickMeta);

            ItemStack iron = new ItemStack(Material.IRON_BLOCK);
            ItemMeta ironMeta = iron.getItemMeta();
            ironMeta.setDisplayName("Поставить железный блок(ЛКМ для смены)");
            iron.setItemMeta(ironMeta);

            ItemStack patron = new ItemStack(Material.TRIPWIRE_HOOK);
            ItemMeta patronMeta = patron.getItemMeta();
            patronMeta.setDisplayName("Патроны(для перезарядки ПКМ с оружием)");

            inventory.setItem(0, pickaxe);
            inventory.setItem(6, wood);
            inventory.setItem(7, brick);
            inventory.setItem(8, iron);
            inventory.setItemInOffHand(patron);

            inventory.setChestplate(new ItemStack(Material.ELYTRA));

            player.teleport(middleLocation);

            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        }
        started = true;
        PeriodsHelper.doPeriod(server, initWorld, middleLocation);
    }

    public static void stopRoyale(Server server){
        server.broadcastMessage(ChatColor.GOLD + "[RE] Ивент закончен");
        PeriodsHelper.stopPeriod();
        started = false;
    }

    public static boolean isStarted(){
        return started;
    }

    public static HashMap<String, Integer> getKillerStats(){
        return killerStats;
    }

    public static void addKillToStats(String nick){
        int currentKills = 0;
        if(killerStats.containsKey(nick)){
            currentKills = killerStats.get(nick);
            killerStats.remove(nick);
        }
        killerStats.put(nick, ++currentKills);
    }
}
