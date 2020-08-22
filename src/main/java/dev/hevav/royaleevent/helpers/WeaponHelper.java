package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.types.Drinkable;
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
            switch(random.nextInt(15)){
                case 0:
                    inventorable = Weapon.Tactical;
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    inventorable = Weapon.Pistol;
                    break;
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
                case 12:
                    inventorable = OtherItems.Iron;
                    break;
                case 13:
                    inventorable = Drinkable.Slurp;
                    break;
                case 14:
                    inventorable = Drinkable.Regen;
                    break;
                default:
                    inventorable = OtherItems.Bricks;
            }
            location.getWorld().dropItem(location, new ItemStack(inventorable.material, 1));
        }
    }

    public static void doReload(PlayerInventory inventory, Weapon weapon){
        ItemStack reloadWeapon = inventory.getItemInMainHand();
        ItemStack patrons = inventory.getItemInOffHand();
        if(patrons.getAmount() < 2)
            return;
        int leftToFull = weapon.reloadSize - reloadWeapon.getAmount();
        reloadWeapon.setAmount(reloadWeapon.getAmount() + Math.min(leftToFull, patrons.getAmount() - 1));
        patrons.setAmount(patrons.getAmount() - Math.min(leftToFull, patrons.getAmount() - 1));
        inventory.setItemInOffHand(patrons);

        RoyaleEvent plugin = RoyaleEvent.getInstance();
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()->{
            inventory.setItemInMainHand(reloadWeapon);
        }, weapon.reloadTicks);
    }
}
