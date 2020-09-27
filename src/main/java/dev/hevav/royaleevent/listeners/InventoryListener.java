package dev.hevav.royaleevent.listeners;

import dev.hevav.royaleevent.helpers.InventoryHelper;
import dev.hevav.royaleevent.types.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryListener implements org.bukkit.event.Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(InventoryClickEvent event) {
        if (event.getWhoClicked().hasPermission("royaleevent.interact") && (event.getSlot() < 1 || event.getSlot() > 5))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerDropItemEvent event) {
        if (event.getPlayer().hasPermission("royaleevent.interact")) {
            Material type = event.getItemDrop().getItemStack().getType();
            if (OtherItems.getItemByMaterial(type) != null || type == Material.STONE_PICKAXE)
                event.setCancelled(true);
            if (Weapon.getWeaponByMaterial(type) != null) {
                ItemStack prevItemStack = event.getItemDrop().getItemStack();
                prevItemStack.setAmount(1);
                event.getItemDrop().setItemStack(prevItemStack);
                PlayerInventory inventory = event.getPlayer().getInventory();
                if (inventory.getItemInMainHand().getType() == prevItemStack.getType())
                    inventory.setItemInMainHand(new ItemStack(Material.AIR));
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(InventoryDragEvent event) {
        if (event.getWhoClicked().hasPermission("royaleevent.interact") && event.getInventorySlots().stream().anyMatch(slot -> slot < 1 || slot > 5))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerSwapHandItemsEvent event) {
        if (event.getPlayer().hasPermission("royaleevent.interact"))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!player.hasPermission("royaleevent.interact"))
                return;
            ItemStack pickupItem = event.getItem().getItemStack();
            if (!player.hasPermission("royaleevent.interact"))
                return;
            PlayerInventory inventory = player.getInventory();

            if (pickupItem.getType() == Material.TRIPWIRE_HOOK) {
                if (inventory.getItemInOffHand().getAmount() < 100) {
                    ItemStack itemStack = inventory.getItemInOffHand();
                    itemStack.setAmount(Math.min(itemStack.getAmount() + 16, 100));
                    inventory.setItemInOffHand(itemStack);
                } else
                    return;

                event.getItem().remove();
                event.setCancelled(true);
                return;
            }

            OtherItems otherItem = OtherItems.getItemByMaterial(pickupItem.getType());
            if (otherItem == null) {
                Inventorable item2 = Drinkable.getDrinkableFromMaterial(pickupItem.getType());
                if (item2 == null)
                    item2 = Placeable.getPlaceableByMaterial(pickupItem.getType());
                if (item2 == null)
                    return;

                ItemStack itemStack = new ItemStack(pickupItem.getType());
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(item2.name);
                itemStack.setItemMeta(itemMeta);
                if (!InventoryHelper.addToFreeSlot(inventory, itemStack)) {
                    return;
                }

                event.getItem().remove();
                event.setCancelled(true);
                return;
            }
            ItemStack curItem = inventory.getItem(otherItem.inventoryNumber);
            int setAmount = curItem.getAmount() + pickupItem.getAmount();
            if (setAmount >= 100)
                return;

            event.getItem().remove();
            event.setCancelled(true);
            curItem.setAmount(setAmount);
            inventory.setItem(otherItem.inventoryNumber, curItem);
        }
    }
}
