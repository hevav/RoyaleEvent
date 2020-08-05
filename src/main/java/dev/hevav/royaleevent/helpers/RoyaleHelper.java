package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class RoyaleHelper {
    public static void initRoyale(Server server, World initWorld){
        server.getConsoleSender().sendMessage("Event was started");
        Location middleLocation = new Location(initWorld, (Integer) RoyaleEvent.config.get("midXcord"), (Integer) RoyaleEvent.config.get("midXcord"), (Integer) RoyaleEvent.config.get("midXcord"));
        Location startLocation = middleLocation.add(-100, 0, -100);
        Location endLocation = startLocation.add(100, 0, 100);
        for(Player player : server.getOnlinePlayers()){
            PlayerInventory inventory = player.getInventory();
            inventory.clear();
            inventory.setMaxStackSize(512);

            ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
            ItemMeta pickaxeMeta = pickaxe.getItemMeta();
            pickaxeMeta.setUnbreakable(true);
            pickaxeMeta.setDisplayName("Break tool");
            pickaxeMeta.setCustomModelData(22822810);
            pickaxe.setItemMeta(pickaxeMeta);

            ItemStack wood = new ItemStack(Material.OAK_PLANKS);
            ItemMeta woodMeta = wood.getItemMeta();
            woodMeta.setDisplayName("Place wood(Left mouse for change)");
            woodMeta.setCustomModelData(22822820);
            wood.setItemMeta(woodMeta);

            ItemStack brick = new ItemStack(Material.BRICKS);
            ItemMeta brickMeta = brick.getItemMeta();
            brickMeta.setDisplayName("Place bricks(Left mouse for change)");
            brickMeta.setCustomModelData(22822830);
            brick.setItemMeta(brickMeta);

            ItemStack iron = new ItemStack(Material.IRON_BLOCK);
            ItemMeta ironMeta = iron.getItemMeta();
            ironMeta.setDisplayName("Place iron block(Left mouse for change)");
            ironMeta.setCustomModelData(22822840);
            iron.setItemMeta(ironMeta);

            inventory.setItem(0, pickaxe);
            inventory.setItem(6, wood);
            inventory.setItem(7, brick);
            inventory.setItem(8, iron);

            inventory.setChestplate(new ItemStack(Material.ELYTRA));

            player.chat(startLocation.toString());

            Chicken chicken = (Chicken) player.getWorld().spawnEntity(startLocation, EntityType.CHICKEN);
            player.teleport(startLocation);
            player.setGameMode(GameMode.SURVIVAL);
            chicken.addPassenger(player);
            chicken.setGravity(false);
            chicken.setTarget((LivingEntity) player.getWorld().spawnEntity(endLocation, EntityType.CHICKEN));

            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        }
        PeriodsHelper.doPeriod(initWorld, middleLocation);
        RoyaleEvent.config.set("started", true);
    }

    public static void stopRoyale(Server server){
        PeriodsHelper.stopPeriod();
        RoyaleEvent.config.set("started", false);
    }

    public static void addChest(Server server, Location location){
        List<Location> chests = (List<Location>) RoyaleEvent.config.get("chests");
        chests.add(location);
        RoyaleEvent.config.set("chests", chests);
    }

    public static void removeChest(Server server, Location location){
        List<Location> chests = (List<Location>) RoyaleEvent.config.get("chests");
        chests.removeIf(l -> l == location);
        RoyaleEvent.config.set("chests", chests);
    }

    public static boolean isStarted(){
        return (boolean) RoyaleEvent.config.get("started");
    }
}
