package dev.hevav.royaleevent.listeners;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.helpers.RoyaleHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryListener implements org.bukkit.event.Listener{
    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(InventoryClickEvent event){
        if(RoyaleHelper.isStarted())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(InventoryDragEvent event){
        if(RoyaleHelper.isStarted())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerSwapHandItemsEvent event){
        if(RoyaleHelper.isStarted())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(EntityPickupItemEvent event){
        if(RoyaleHelper.isStarted() && event.getEntity() instanceof Player){
            event.setCancelled(true);
            ItemStack pickupItem = event.getItem().getItemStack();
            PlayerInventory inventory = ((Player) event.getEntity()).getInventory();
            event.getItem().remove();

            int number;
            switch (pickupItem.getType()){
                case WOOD:
                case WOOD_STEP:
                case WOOD_DOUBLE_STEP:
                case WOOD_STAIRS:
                    number = 6;
                    break;
                case BRICK:
                case BRICK_STAIRS:
                    number = 7;
                    break;
                case IRON_BLOCK:
                case STONE_SLAB2:
                case IRON_FENCE:
                    number = 8;
                    break;
                case TRIPWIRE_HOOK:
                    ItemStack itemStack = inventory.getItemInOffHand();
                    itemStack.setAmount(itemStack.getAmount()+16);
                    inventory.setItemInOffHand(itemStack);
                    return;
                default:
                    return;
            }
            ItemStack curItem = inventory.getItem(number);
            int setAmount = curItem.getAmount()+1;
            if(pickupItem.getType() == Material.WOOD_DOUBLE_STEP)
                ++setAmount;
            if(setAmount == 100)
                return;
            curItem.setAmount(setAmount);
            inventory.setItem(number, curItem);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerMoveEvent event){
        if(RoyaleHelper.isStarted()){
            PlayerInventory inventory = event.getPlayer().getInventory();
            if(inventory.getChestplate() != null &&
                    !event.getPlayer().isFlying() &&
                    inventory.getChestplate().getType() == Material.ELYTRA &&
                    event.getFrom().getY() < event.getTo().getY() &&
                    event.getTo().getY()+2 < (Integer) RoyaleEvent.config.get("midYcord"))
                inventory.setChestplate(new ItemStack(Material.AIR));
        }
    }
}
