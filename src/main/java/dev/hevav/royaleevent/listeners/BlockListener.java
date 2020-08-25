package dev.hevav.royaleevent.listeners;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.helpers.BlockHelper;
import dev.hevav.royaleevent.helpers.RoyaleHelper;
import dev.hevav.royaleevent.types.Chunkable;
import dev.hevav.royaleevent.types.OtherItems;
import dev.hevav.royaleevent.types.Placeable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class BlockListener implements org.bukkit.event.Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(BlockPlaceEvent event){
        if(RoyaleHelper.isStarted()){
            Player player = event.getPlayer();
            if(!player.hasPermission("royaleevent.interact"))
                return;
            event.setCancelled(true);
            PlayerInventory inventory = player.getInventory();
            ItemStack item = inventory.getItemInMainHand();
            OtherItems block = OtherItems.getItemByMaterial(item.getType());
            int amount = item.getAmount();
            float yaw = event.getPlayer().getLocation().getYaw();
            Chunkable chunkable = Chunkable.fromLocation(BlockHelper.nextChunkByYaw(event.getBlock().getLocation(), yaw), true);
            if(block == null){
                Placeable placeable = Placeable.getPlaceableByMaterial(item.getType());
                if(placeable == null)
                    return;

                chunkable.replaceWith(placeable.chunkable.rotate(yaw));
                item.setAmount(amount-1);
                event.getPlayer().sendMessage(String.format("%s[RE] %s", ChatColor.GREEN, placeable.tutorial));
                return;
            }
            if(amount < 6)
                return;

            chunkable.replaceWith(block.chunkable.rotate(yaw));
            item.setAmount(amount-5);
            inventory.setItem(block.inventoryNumber, item);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (RoyaleHelper.isStarted() && player.hasPermission("royaleevent.interact") && event.isBlockInHand()) {
            ItemStack item = event.getItem();
            if (item == null)
                return;
            ItemMeta meta = item.getItemMeta();
            if (meta == null)
                return;
            if(event.getPlayer().getGameMode() == GameMode.SPECTATOR)
                return;
            Inventory inventory = player.getInventory();
            if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
                OtherItems toPlace = OtherItems.switchItem(item.getType());
                if(toPlace == null)
                    return;
                ItemStack block = new ItemStack(toPlace.material, inventory.getItem(toPlace.inventoryNumber).getAmount());
                ItemMeta blockMeta = block.getItemMeta();
                blockMeta.setDisplayName(toPlace.name);
                block.setItemMeta(blockMeta);
                inventory.setItem(toPlace.inventoryNumber, block);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if(RoyaleHelper.isStarted() && player.hasPermission("royaleevent.interact") && event.isSneaking()){
            Placeable placeable = Placeable.getPlaceableByPlacedMaterial(player.getLocation().add(0, -1, 0).getBlock().getType());
            if(placeable != null){
                switch(placeable.name){
                    case "Костёр":
                        player.sendMessage(ChatColor.GREEN + "[RE] Хилюсь...");
                        Bukkit.getScheduler().scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                            player.setHealth(Math.min(player.getHealth()+4, 20));
                            player.sendMessage(ChatColor.GREEN + "[RE] Захилился :)");
                        }, 20);
                        break;
                    case "Батут":
                        Vector velocity = player.getLocation().getDirection();
                        player.setVelocity(velocity.setX(0).setY(3).setZ(0));
                        PlayerInventory playerInventory = player.getInventory();
                        if(playerInventory.getChestplate() != null && playerInventory.getChestplate().getType() == Material.ELYTRA)
                            return;
                        player.sendMessage(ChatColor.GREEN + "[RE] Выдаю элитры на 1 минуту...");
                        playerInventory.setChestplate(new ItemStack(Material.ELYTRA, 1));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()-> {
                                    player.sendMessage(ChatColor.GREEN + "[RE] Удалил элитры :)");
                                    playerInventory.setChestplate(new ItemStack(Material.AIR));
                                }
                        , 1200);
                        break;
                }
            }
        }
    }
}
