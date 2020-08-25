package dev.hevav.royaleevent.listeners;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.helpers.BlockHelper;
import dev.hevav.royaleevent.helpers.InventoryHelper;
import dev.hevav.royaleevent.helpers.RoyaleHelper;
import dev.hevav.royaleevent.helpers.WeaponHelper;
import dev.hevav.royaleevent.types.*;
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
        if(RoyaleHelper.isStarted() && event.getPlayer().hasPermission("royaleevent.interact")){
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
                    for(int i = 1; i <= weapon.shootQuantity; i++){
                        Snowball snowball = event.getPlayer().launchProjectile(Snowball.class);
                        snowball.setVelocity(snowball.getVelocity().multiply(weapon.velocity/3*i));
                        snowball.setMetadata("damage", new FixedMetadataValue(RoyaleEvent.getInstance(), weapon.damage));
                        snowball.setMetadata("killer", new FixedMetadataValue(RoyaleEvent.getInstance(), event.getPlayer().getName()));
                        event.getPlayer().setWalkSpeed(0.2f / weapon.playerVelocity);
                    }
                    Location playerLocation = event.getPlayer().getLocation();
                    playerLocation.getWorld().playSound(playerLocation, weapon.sound, 1, 1);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                        event.getPlayer().setWalkSpeed(0.2f);
                    });
                    break;
                case RIGHT_CLICK_BLOCK:
                    Block clickedBlock = event.getClickedBlock();
                    if(clickedBlock.getType().equals(Material.CHEST)){
                        clickedBlock.setType(Material.AIR);
                        WeaponHelper.doRandomWeaponDrop(clickedBlock.getLocation(), 5);
                    }
                case RIGHT_CLICK_AIR:
                    if(event.hasItem()){
                        weapon = Weapon.getWeaponByMaterial(event.getItem().getType());
                        if(weapon == null)
                            return;
                        WeaponHelper.doReload(inventory, weapon, event.getPlayer().getLocation());
                    }
                    break;
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerItemDamageEvent event) {
        if(RoyaleHelper.isStarted() && (event.getItem() == null || event.getItem().getType() != Material.STONE_PICKAXE) && event.getPlayer().hasPermission("royaleevent.interact"))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerDeathEvent event) {
        if(RoyaleHelper.isStarted() && event.getEntity().hasPermission("royaleevent.interact")) {
            RoyaleHelper.proceedKill(event.getEntity());
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerRespawnEvent event) {
        if(RoyaleHelper.isStarted() && event.getPlayer().hasPermission("royaleevent.interact")){
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(EntityDamageByEntityEvent event) {
        if(RoyaleHelper.isStarted() && event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.SNOWBALL){
            Player damaged = (Player) event.getEntity();
            if(!damaged.hasPermission("royaleevent.interact")){
                return;
            }
            int damage = event.getDamager().getMetadata("damage").get(0).asInt()/10;
            String killer = event.getDamager().getMetadata("killer").get(0).asString();
            double health = damaged.getHealth();
            Bukkit.getServer().getPlayer(killer).sendMessage(ChatColor.RED+"[RE] Попадение в " + damaged.getName());
            if(damaged.getHealth() <= damage){
                Bukkit.getServer().getConsoleSender().sendMessage(String.format("%s killed %s", killer, damaged.getName()));
                Bukkit.getServer().broadcastMessage(String.format("%s[RE] %s был убит %s", ChatColor.RED, damaged.getName(), killer));
                damaged.sendTitle(String.format("%sВас убил %s", ChatColor.RED, killer), "RoyaleEvent by hevav", 5, 80, 5);

                RoyaleHelper.addKillToStats(killer);
                event.setCancelled(true);
                damaged.setGameMode(GameMode.SPECTATOR);

                RoyaleHelper.proceedKill(damaged);
            }
            else{
                damaged.setHealth(health - damage);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(InventoryOpenEvent event) {
        if(RoyaleHelper.isStarted() && event.getInventory().getType().equals(InventoryType.CHEST) && event.getPlayer().hasPermission("royaleevent.interact"))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(BlockBreakEvent event) {
        if(RoyaleHelper.isStarted() && event.getPlayer().hasPermission("royaleevent.interact")){
            event.setCancelled(true);
            PlayerInventory inventory = event.getPlayer().getInventory();
            ItemStack item = inventory.getItemInMainHand();
            Block clickedBlock = event.getBlock();
            if(item != null && item.getType().equals(Material.STONE_PICKAXE)){
                switch (clickedBlock.getType()) {
                    case LOG:
                    case LOG_2:
                        for(Block block : BlockHelper.getBlocks(clickedBlock, 3, 21, 3)){
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
                        WeaponHelper.doRandomWeaponDrop(clickedBlock.getLocation(), 8);
                        clickedBlock.setType(Material.AIR);
                        return;
                }
                OtherItems itemInventorable = OtherItems.getItemByMaterial(clickedBlock.getType());
                if(itemInventorable == null) {
                    Placeable placeable = Placeable.getPlaceableByPlacedMaterial(clickedBlock.getType());
                    if(placeable == null)
                        return;

                    placeable.materials.forEach(material -> BlockHelper.deleteChunk(clickedBlock.getLocation(), material));
                    InventoryHelper.addToFreeSlot(inventory, new ItemStack(placeable.material, 1));
                    return;
                }
                ItemStack curItem = inventory.getItem(itemInventorable.inventoryNumber);
                if(curItem.getAmount() > 99)
                    return;
                int setAmount = curItem.getAmount()+BlockHelper.deleteChunk(clickedBlock.getLocation(), clickedBlock.getType());
                curItem.setAmount(setAmount);
                inventory.setItem(itemInventorable.inventoryNumber, curItem);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public static void onEvent(EntityPickupItemEvent event) {
        if(RoyaleHelper.isStarted() && event.getEntity() instanceof Player) {
            ItemStack pickupItem = event.getItem().getItemStack();
            Player player = (Player) event.getEntity();
            if(!player.hasPermission("royaleevent.interact")){
                return;
            }
            PlayerInventory inventory = player.getInventory();

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
        if(RoyaleHelper.isStarted() && event.getPlayer().hasPermission("royaleevent.interact")){
            Drinkable drinkable = Drinkable.getDrinkableFromMaterial(event.getItem().getType());
            if(drinkable == null)
                return;
            drinkable.drink(event.getPlayer());
        }
    }
}
