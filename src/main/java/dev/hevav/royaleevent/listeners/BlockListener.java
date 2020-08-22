package dev.hevav.royaleevent.listeners;

import dev.hevav.royaleevent.helpers.BlockHelper;
import dev.hevav.royaleevent.helpers.RoyaleHelper;
import dev.hevav.royaleevent.types.Chunkable;
import dev.hevav.royaleevent.types.Inventorable;
import dev.hevav.royaleevent.types.OtherItems;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockListener implements org.bukkit.event.Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(BlockPlaceEvent event){
        if(RoyaleHelper.isStarted()){
            event.setCancelled(true);
            PlayerInventory inventory = event.getPlayer().getInventory();
            ItemStack item = inventory.getItemInMainHand();
            OtherItems block = OtherItems.getItemByMaterial(item.getType());
            int amount = item.getAmount();
            if(block == null || amount < 2)
                return;

            Chunkable.fromLocation(BlockHelper.nextChunkByPlayer(event.getPlayer().getLocation(), event.getBlock().getLocation())).replaceWith(block.chunkable);
            item.setAmount(amount-1);
            inventory.setItem(block.inventoryNumber, item);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerInteractEvent event) {
        if (RoyaleHelper.isStarted() && event.isBlockInHand()) {
            ItemStack item = event.getItem();
            if (item == null)
                return;
            ItemMeta meta = item.getItemMeta();
            if (meta == null)
                return;
            if(event.getPlayer().getGameMode() == GameMode.SPECTATOR)
                return;
            Inventory inventory = event.getPlayer().getInventory();
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
}
