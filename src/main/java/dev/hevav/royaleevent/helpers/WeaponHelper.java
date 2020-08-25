package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.types.*;
import org.bukkit.Location;
import org.bukkit.Sound;
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
                    inventorable = Weapon.Pistol;
                    break;
                case 3:
                    inventorable = Weapon.SCAR;
                    break;
                case 4:
                    inventorable = Weapon.Sniper;
                    break;
                case 5:
                    inventorable = Weapon.UZI;
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                    inventorable = OtherItems.Patron;
                    break;
                case 10:
                    inventorable = OtherItems.Iron;
                    break;
                case 11:
                    inventorable = Drinkable.Regen;
                    break;
                case 12:
                    inventorable = Placeable.Campfire;
                    break;
                case 13:
                    inventorable = Placeable.Jumppad;
                    break;
                default:
                    inventorable = OtherItems.Bricks;
            }
            location.getWorld().dropItem(location, new ItemStack(inventorable.material, 1));
        }
    }

    public static void doReload(PlayerInventory inventory, Weapon weapon, Location soundLocation){
        ItemStack reloadWeapon = inventory.getItemInMainHand();
        ItemStack patrons = inventory.getItemInOffHand();
        if(patrons.getAmount() < 2)
            return;
        int leftToFull = weapon.reloadSize - reloadWeapon.getAmount();
        soundLocation.getWorld().playSound(soundLocation, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);

        RoyaleEvent plugin = RoyaleEvent.getInstance();
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()->{
            reloadWeapon.setAmount(reloadWeapon.getAmount() + Math.min(leftToFull, patrons.getAmount() - 1));
            patrons.setAmount(patrons.getAmount() - Math.min(leftToFull, patrons.getAmount() - 1));
            inventory.setItemInOffHand(patrons);
            soundLocation.getWorld().playSound(soundLocation, Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
        }, weapon.reloadTicks);
    }
}
