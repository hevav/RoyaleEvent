package dev.hevav.royaleevent.helpers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryHelper {
    public static Integer getFreeSlot(PlayerInventory inventory){
        if(inventory.getItem(1) == null)
            return 1;
        if(inventory.getItem(2) == null)
            return 2;
        if(inventory.getItem(3) == null)
            return 3;
        if(inventory.getItem(4) == null)
            return 4;
        if(inventory.getItem(5) == null)
            return 5;
        return null;
    }

    public static boolean addToFreeSlot(PlayerInventory inventory, ItemStack itemStack){
        Integer freeSlot = getFreeSlot(inventory);
        if(freeSlot == null)
            return false;
        inventory.setItem(freeSlot, itemStack);
        return true;
    }

    public static boolean checkForMaterial(PlayerInventory inventory, Material material){
        return  inventory.getItem(1).getType().equals(material) ||
                inventory.getItem(2).getType().equals(material) ||
                inventory.getItem(3).getType().equals(material) ||
                inventory.getItem(4).getType().equals(material) ||
                inventory.getItem(5).getType().equals(material);
    }
}
