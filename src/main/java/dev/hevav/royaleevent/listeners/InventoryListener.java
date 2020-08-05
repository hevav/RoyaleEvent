package dev.hevav.royaleevent.listeners;

import dev.hevav.royaleevent.helpers.RoyaleHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

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
}
