package dev.hevav.royaleevent.listeners;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.helpers.RoyaleHelper;
import dev.hevav.royaleevent.types.Inventorable;
import dev.hevav.royaleevent.types.OtherItems;
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

            if (pickupItem.getType() == Material.TRIPWIRE_HOOK){
                ItemStack itemStack = inventory.getItemInOffHand();
                itemStack.setAmount(itemStack.getAmount()+16);
                inventory.setItemInOffHand(itemStack);
                event.setCancelled(true);
                return;
            }

            Inventorable item = OtherItems.getItemByMaterial(pickupItem.getType());
            if(item == null) {
                event.setCancelled(true);
                return;
            }
            ItemStack curItem = inventory.getItem(item.inventoryNumber);
            int setAmount = curItem.getAmount()+1;
            if(pickupItem.getType() == Material.WOOD_DOUBLE_STEP)
                ++setAmount;
            if(setAmount == 100)
                return;
            curItem.setAmount(setAmount);
            inventory.setItem(item.inventoryNumber, curItem);
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
