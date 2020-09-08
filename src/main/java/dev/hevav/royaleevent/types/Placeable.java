package dev.hevav.royaleevent.types;

import dev.hevav.royaleevent.RoyaleEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Placeable extends Inventorable {
    public Chunkable chunkable;
    public List<Material> materials;
    public String tutorial;
    public Predicate<Player> onSneak;

    public Placeable(Material material, String name, Chunkable chunkable, List<Material> materials, String tutorial, Predicate<Player> onSneak){
        this.material = material;
        this.name = name;
        this.chunkable = chunkable;
        this.materials = materials;
        this.tutorial = tutorial;
        this.onSneak = onSneak;
    }

    public static final Placeable Campfire = new Placeable(Material.FURNACE, RoyaleEvent.config.getString("strings.healTool"), Chunkable.Campfire, Arrays.asList(Material.THIN_GLASS, Material.RED_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA, Material.COAL_BLOCK, Material.WOOD, Material.WOOD_STAIRS, Material.WOOD_STEP, Material.STONE), RoyaleEvent.config.getString("strings.healDescription"), player -> {
        player.sendMessage(String.format("%s[RE] %s", ChatColor.GREEN, RoyaleEvent.config.getString("strings.healStarted")));
        Bukkit.getScheduler().scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
            player.setHealth(Math.min(player.getHealth()+4, 20));
            player.sendMessage(String.format("%s[RE] %s", ChatColor.GREEN, RoyaleEvent.config.getString("strings.healComplete")));
        }, 20);
        return true;
    });
    public static final Placeable Jumppad = new Placeable(Material.SLIME_BLOCK, RoyaleEvent.config.getString("strings.jumpTool"), Chunkable.Jumppad, Collections.singletonList(Material.SLIME_BLOCK), RoyaleEvent.config.getString("strings.jumpDescription"), player -> {
        Vector velocity = player.getLocation().getDirection();
        player.setVelocity(velocity.setX(0).setY(3).setZ(0));
        PlayerInventory playerInventory = player.getInventory();
        if(playerInventory.getChestplate() != null && playerInventory.getChestplate().getType() == Material.ELYTRA)
            return false;
        player.sendMessage(String.format("%s[RE] %s", ChatColor.GREEN, RoyaleEvent.config.getString("strings.jumpStarted")));
        playerInventory.setChestplate(new ItemStack(Material.ELYTRA, 1));
        Bukkit.getScheduler().scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()-> {
                    player.sendMessage(ChatColor.GREEN + "[RE] "+RoyaleEvent.config.getString("strings.jumpStarted"));
                    playerInventory.setChestplate(new ItemStack(Material.AIR));
                }, 1200);
        return true;
    });

    public static Placeable getPlaceableByMaterial(Material material){
        switch (material){
            case FURNACE:
                return Campfire;
            case SLIME_BLOCK:
                return Jumppad;
            default:
                return null;
        }
    }

    public static Placeable getPlaceableByPlacedMaterial(Material material){
        switch (material){
            case RED_GLAZED_TERRACOTTA:
            case YELLOW_GLAZED_TERRACOTTA:
            case ORANGE_GLAZED_TERRACOTTA:
                return Campfire;
            case CARPET:
            case SLIME_BLOCK:
                return Jumppad;
            default:
                return null;
        }
    }
}
