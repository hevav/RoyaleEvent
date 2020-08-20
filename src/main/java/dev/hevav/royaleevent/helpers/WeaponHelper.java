package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

public class WeaponHelper {
    public static void doRandomWeaponDrop(Location location, int threads){
        Random random = new Random();
        for(int i = 0; i<threads; i++) {
            Material material;
            switch(random.nextInt(12)){
                case 0:
                case 1:
                    material = Material.WOOD_HOE;
                    break;
                case 2:
                case 3:
                    material = Material.STONE_HOE;
                    break;
                case 4:
                case 5:
                    material = Material.IRON_HOE;
                    break;
                case 6:
                    material = Material.GOLD_HOE;
                    break;
                case 7:
                    material = Material.DIAMOND_HOE;
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                    material = Material.TRIPWIRE_HOOK;
                    break;
                default:
                    material = Material.BRICK;
            }
            location.getWorld().dropItem(location, new ItemStack(material, 1));
        }
    }

    public static void doReload(int inventoryNumber, PlayerInventory inventory){
        ItemStack reloadWeapon = inventory.getItem(inventoryNumber);
        ItemStack patrons = inventory.getItemInOffHand();
        int reloadSize;
        int reloadTicks;
        switch (reloadWeapon.getType()){
            case WOOD_HOE:
                reloadSize = 6;
                reloadTicks = 120;
                break;
            case STONE_HOE:
                reloadSize = 20;
                reloadTicks = 30;
                break;
            case IRON_HOE:
                reloadSize = 40;
                reloadTicks = 40;
                break;
            case GOLD_HOE:
                reloadSize = 1;
                reloadTicks = 80;
                break;
            case DIAMOND_HOE:
                reloadSize = 25;
                reloadTicks = 45;
                break;
            default:
                return;
        }

        if(patrons.getAmount() < 2)
            return;
        int leftToFull = reloadSize-reloadWeapon.getAmount();
        reloadWeapon.setAmount(Math.min(leftToFull, patrons.getAmount()-1));
        patrons.setAmount(patrons.getAmount() - Math.min(leftToFull, patrons.getAmount()-1));
        inventory.setItemInOffHand(patrons);

        RoyaleEvent plugin = RoyaleEvent.getInstance();
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()->{
            inventory.setItem(inventoryNumber, reloadWeapon);
        }, reloadTicks);
    }
}
