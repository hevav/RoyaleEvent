package dev.hevav.royaleevent.listeners;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.helpers.BlockHelper;
import dev.hevav.royaleevent.helpers.InventoryHelper;
import dev.hevav.royaleevent.helpers.RoyaleHelper;
import dev.hevav.royaleevent.helpers.WeaponHelper;
import dev.hevav.royaleevent.types.Drinkable;
import dev.hevav.royaleevent.types.Inventorable;
import dev.hevav.royaleevent.types.OtherItems;
import dev.hevav.royaleevent.types.Weapon;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;
import java.util.stream.Collectors;

public class WeaponListener implements org.bukkit.event.Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerInteractEvent event){
        if(RoyaleHelper.isStarted()){
            if(event.getPlayer().getGameMode() == GameMode.SPECTATOR)
                return;
            PlayerInventory inventory = event.getPlayer().getInventory();
            ItemStack patrons = inventory.getItemInOffHand();
            switch(event.getAction()){
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                    if (!event.hasItem())
                        return;
                    Weapon weapon = Weapon.getWeaponByMaterial(event.getItem().getType());
                    if(weapon == null)
                        return;
                    ItemStack stack = event.getItem();
                    if(stack.getAmount() == 1)
                        return;
                    stack.setAmount(stack.getAmount()-1);
                    inventory.setItemInMainHand(stack);
                    Snowball snowball = event.getPlayer().launchProjectile(Snowball.class);
                    snowball.setVelocity(snowball.getVelocity().multiply(weapon.velocity/3));
                    snowball.setMetadata("damage", new FixedMetadataValue(RoyaleEvent.getInstance(), weapon.damage));
                    snowball.setMetadata("killer", new FixedMetadataValue(RoyaleEvent.getInstance(), event.getPlayer().getName()));
                    Location toTeleport = event.getPlayer().getLocation();
                    toTeleport.setYaw(toTeleport.getYaw() + weapon.playerYaw);
                    toTeleport.setPitch(toTeleport.getPitch() + weapon.playerPitch);
                    event.getPlayer().teleport(toTeleport);
                    break;
                case RIGHT_CLICK_BLOCK:
                    Block clickedBlock = event.getClickedBlock();
                    if(clickedBlock.getType() == Material.CHEST){
                        clickedBlock.setType(Material.AIR);
                        WeaponHelper.doRandomWeaponDrop(clickedBlock.getLocation(), 3);
                    }
                case RIGHT_CLICK_AIR:
                    if(event.hasItem()){
                        weapon = Weapon.getWeaponByMaterial(event.getItem().getType());
                        if(weapon == null)
                            return;
                        WeaponHelper.doReload(inventory, weapon);
                    }
                    break;
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerItemDamageEvent event) {
        if(RoyaleHelper.isStarted() && (event.getItem() == null || event.getItem().getType() != Material.STONE_PICKAXE))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerDeathEvent event) {
        if(RoyaleHelper.isStarted()) {
            List<Player> survived = Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL && player.getHealth() > 0).collect(Collectors.toList());
            if (survived.size() == 1) {
                RoyaleHelper.proceedWinner(survived.get(0));
            }
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerRespawnEvent event) {
        if(RoyaleHelper.isStarted()){
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(EntityDamageByEntityEvent event) {
        if(RoyaleHelper.isStarted() && event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.SNOWBALL){
            Player damaged = (Player) event.getEntity();
            int damage = event.getDamager().getMetadata("damage").get(0).asInt()/10;
            String killer = event.getDamager().getMetadata("killer").get(0).asString();
            double health = damaged.getHealth();
            Bukkit.getServer().getPlayer(killer).sendMessage(ChatColor.RED+"[RE] Попадение в " + damaged.getName());
            if(damaged.getHealth() <= damage){
                Bukkit.getServer().getConsoleSender().sendMessage(String.format("%s killed %s", killer, damaged.getName()));
                damaged.sendMessage(ChatColor.RED+"[RE] Вас убил " + killer);

                RoyaleHelper.addKillToStats(killer);
                event.setCancelled(true);
                damaged.setGameMode(GameMode.SPECTATOR);

                List<Player> survived = Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).collect(Collectors.toList());
                if(survived.size() == 1){
                    RoyaleHelper.proceedWinner(survived.get(0));
                }
            }
            else{
                damaged.setHealth(health - damage);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(InventoryOpenEvent event) {
        if(RoyaleHelper.isStarted() && event.getInventory().getType() == InventoryType.CHEST)
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(BlockBreakEvent event) {
        if(RoyaleHelper.isStarted()){
            event.setCancelled(true);
            PlayerInventory inventory = event.getPlayer().getInventory();
            ItemStack item = inventory.getItemInMainHand();
            Block clickedBlock = event.getBlock();
            if(item != null && item.getType() == Material.STONE_PICKAXE){
                switch (clickedBlock.getType()) {
                    case LOG:
                        for(Block block : BlockHelper.getBlocks(clickedBlock, 3, 7, 3)){
                            ItemStack curItem = inventory.getItem(6);
                            if(curItem.getAmount() > 100)
                                break;
                            block.setType(Material.AIR);
                            curItem.setAmount(curItem.getAmount()+1);
                            inventory.setItem(6, curItem);
                        }
                        return;
                    case DIAMOND_BLOCK:
                        event.getPlayer().getServer().broadcastMessage(ChatColor.GOLD + "[RE] Кто-то нашел эйрдроп");
                        WeaponHelper.doRandomWeaponDrop(clickedBlock.getLocation(), 5);
                        clickedBlock.setType(Material.AIR);
                        return;
                }
                OtherItems itemInventorable = OtherItems.getItemByMaterial(clickedBlock.getType());
                if(itemInventorable == null)
                    return;
                ItemStack curItem = inventory.getItem(itemInventorable.inventoryNumber);
                if(curItem.getAmount() > 100)
                    return;
                int setAmount = curItem.getAmount()+1;
                if(clickedBlock.getType() == Material.WOOD_DOUBLE_STEP)
                    ++setAmount;
                curItem.setAmount(setAmount);
                inventory.setItem(itemInventorable.inventoryNumber, curItem);
                clickedBlock.setType(Material.AIR);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public static void onEvent(EntityPickupItemEvent event) {
        if(RoyaleHelper.isStarted() && event.getEntity() instanceof Player) {
            ItemStack pickupItem = event.getItem().getItemStack();
            PlayerInventory inventory = ((Player) event.getEntity()).getInventory();

            Weapon weapon = Weapon.getWeaponByMaterial(pickupItem.getType());
            if(weapon == null) {
                event.setCancelled(true);
                return;
            }

            ItemMeta pickupMeta = pickupItem.getItemMeta();
            pickupMeta.setDisplayName(weapon.name);
            pickupItem.setItemMeta(pickupMeta);
            if(!InventoryHelper.addToFreeSlot(inventory, pickupItem))
                event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerItemConsumeEvent event){
        if(RoyaleHelper.isStarted()){
            Drinkable drinkable = Drinkable.getDrinkableFromMaterial(event.getItem().getType());
            if(drinkable == null)
                return;
            drinkable.drink(event.getPlayer());
        }
    }
}
