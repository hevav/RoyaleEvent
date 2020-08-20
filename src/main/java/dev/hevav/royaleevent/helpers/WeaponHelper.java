package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.types.Inventorable;
import dev.hevav.royaleevent.types.OtherItems;
import dev.hevav.royaleevent.types.Weapon;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

public class WeaponHelper {
    public static void doRandomWeaponDrop(Location location, int threads){
        Random random = new Random();
        for(int i = 0; i<threads; i++) {
            Inventorable inventorable;
            switch(random.nextInt(12)){
                case 0:
                case 1:
                    inventorable = Weapon.Tactical;
                    break;
                case 2:
                case 3:
                    inventorable = Weapon.Pistol;
                    break;
                case 4:
                case 5:
                    inventorable = Weapon.SCAR;
                    break;
                case 6:
                    inventorable = Weapon.Sniper;
                    break;
                case 7:
                    inventorable = Weapon.UZI;
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                    inventorable = OtherItems.Patron;
                    break;
                default:
                    inventorable = OtherItems.Bricks;
            }
            location.getWorld().dropItem(location, new ItemStack(inventorable.material, 1));
        }
    }

    public static void doReload(int inventoryNumber, PlayerInventory inventory){
        ItemStack reloadWeapon = inventory.getItem(inventoryNumber);
        ItemStack patrons = inventory.getItemInOffHand();
        Weapon weapon = Weapon.getWeaponByMaterial(reloadWeapon.getType());
        if(patrons.getAmount() < 2 || weapon == null)
            return;
        int leftToFull = weapon.reloadSize - reloadWeapon.getAmount();
        reloadWeapon.setAmount(reloadWeapon.getAmount() + Math.min(leftToFull, patrons.getAmount() - 1));
        patrons.setAmount(patrons.getAmount() - Math.min(leftToFull, patrons.getAmount() - 1));
        inventory.setItemInOffHand(patrons);

        RoyaleEvent plugin = RoyaleEvent.getInstance();
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()->{
            inventory.setItem(inventoryNumber, reloadWeapon);
        }, weapon.reloadTicks);
    }
}
