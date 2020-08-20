package dev.hevav.royaleevent.listeners;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.helpers.BlockHelper;
import dev.hevav.royaleevent.helpers.RoyaleHelper;
import dev.hevav.royaleevent.helpers.WeaponHelper;
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
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;
import java.util.stream.Stream;

public class WeaponListener implements org.bukkit.event.Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(PlayerInteractEvent event){
        if(RoyaleHelper.isStarted()){
            PlayerInventory inventory = event.getPlayer().getInventory();
            ItemStack patrons = inventory.getItemInOffHand();
            switch(event.getAction()){
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                    if (!event.hasItem())
                        return;
                    int velocity;
                    float playerYaw;
                    float playerPitch;
                    int damage;
                    int inventoryNumber;
                    float cooldown;
                    switch (event.getItem().getType()){
                        case WOOD_HOE:
                            velocity = 6;
                            playerYaw = 4;
                            playerPitch = 2;
                            damage = 70;
                            cooldown = 1.5f;//TODO:RELEASE COOLDOWN
                            inventoryNumber = 1;
                            break;
                        case STONE_HOE:
                            velocity = 8;
                            playerYaw = 1;
                            playerPitch = 1;
                            damage = 25;
                            cooldown = 6.75f;
                            inventoryNumber = 2;
                            break;
                        case IRON_HOE:
                            velocity = 6;
                            playerYaw = 2;
                            playerPitch = 2;
                            damage = 35;
                            cooldown = 5.5f;
                            inventoryNumber = 3;
                            break;
                        case GOLD_HOE:
                            velocity = 10;
                            playerYaw = 10;
                            playerPitch = 2;
                            damage = 25;
                            cooldown = 5.5f;
                            inventoryNumber = 4;
                            break;
                        case DIAMOND_HOE:
                            velocity = 8;
                            playerYaw = 2;
                            playerPitch = 2;
                            damage = 35;
                            cooldown = 5.5f;
                            inventoryNumber = 5;
                            break;
                        default:
                            return;
                    }
                    ItemStack stack = event.getItem();
                    if(stack.getAmount() == 1)
                        return;
                    stack.setAmount(stack.getAmount()-1);
                    inventory.setItem(inventoryNumber, stack);
                    Snowball snowball = event.getPlayer().launchProjectile(Snowball.class);
                    snowball.setVelocity(snowball.getVelocity().multiply(velocity));
                    snowball.setMetadata("damage", new FixedMetadataValue(RoyaleEvent.getInstance(), damage));
                    snowball.setMetadata("killer", new FixedMetadataValue(RoyaleEvent.getInstance(), event.getPlayer().getName()));
                    Location toTeleport = event.getPlayer().getLocation();
                    toTeleport.setYaw(toTeleport.getYaw()+playerYaw);
                    toTeleport.setPitch(toTeleport.getPitch()+playerPitch);
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
                        int number;
                        switch(event.getItem().getType()){
                            case WOOD_HOE:
                                number = 1;
                                break;
                            case STONE_HOE:
                                number = 2;
                                break;
                            case IRON_HOE:
                                number = 3;
                                break;
                            case GOLD_HOE:
                                number = 4;
                                break;
                            case DIAMOND_HOE:
                                number = 5;
                                break;
                            default:
                                return;
                        }
                        WeaponHelper.doReload(number, inventory);
                    }
                    break;
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public static void onEvent(EntityDamageByEntityEvent event) {
        if(RoyaleHelper.isStarted() && event.getEntityType() == EntityType.PLAYER && event.getDamager() instanceof Snowball){
            event.setDamage(event.getDamager().getMetadata("damage").get(0).asDouble()/5);
            Player damaged = (Player) event.getEntity();
            if(damaged.getHealth() <= event.getDamage()){
                String killer = event.getDamager().getMetadata("killer").get(0).asString();
                Bukkit.getServer().getConsoleSender().sendMessage(String.format("%s killed %s", killer, damaged.getName()));
                damaged.sendMessage(ChatColor.RED+"[RE] Вас убил " + killer);

                RoyaleHelper.addKillToStats(killer);
                event.setCancelled(true);
                damaged.setGameMode(GameMode.SPECTATOR);

                Stream<? extends Player> survived = Bukkit.getServer().getOnlinePlayers().stream().filter((Player player) -> player.getGameMode() == GameMode.SURVIVAL);
                if(survived.count() == 1){
                    damaged.sendMessage(ChatColor.GOLD+"[RE] И у нас есть победитель! Это... " + survived.findFirst() + "!!!!");
                    damaged.sendMessage(ChatColor.GOLD+"[RE] Подсчитываю топ по киллам....");
                    HashMap<String, Integer> killerStats = RoyaleHelper.getKillerStats();
                    List<Integer> killsTop = new ArrayList<>(killerStats.values());
                    Collections.sort(killsTop);
                    Collections.reverse(killsTop);

                    for(int i = 0; i < 3; i++){
                        StringBuilder nicks = new StringBuilder();
                        int needNum = killsTop.get(i);
                        killerStats.forEach((String nick, Integer num)->{
                            if(num == needNum){
                                nicks.append(nick).append(" ");
                            }
                        });

                        damaged.sendMessage(String.format("%s[RE] %d Место %s с %d убийствами", ChatColor.GOLD, i, nicks, needNum));
                    }

                    RoyaleHelper.stopRoyale(Bukkit.getServer());
                }
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
            ItemMeta meta = item.getItemMeta();
            if (meta == null)
                return;
            Block clickedBlock = event.getBlock();
            if(meta.hasDisplayName() && meta.getDisplayName().equals("Ломалка")){
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
                        break;
                    case WOOD:
                    case WOOD_STEP:
                    case WOOD_DOUBLE_STEP:
                    case WOOD_STAIRS:
                        ItemStack curItem = inventory.getItem(6);
                        if(curItem.getAmount() > 100)
                            break;
                        int setAmount = curItem.getAmount()+1;
                        if(clickedBlock.getType() == Material.WOOD_DOUBLE_STEP)
                            ++setAmount;
                        curItem.setAmount(setAmount);
                        inventory.setItem(6, curItem);
                        clickedBlock.setType(Material.AIR);
                        break;
                    case BRICK:
                    case BRICK_STAIRS:
                        curItem = inventory.getItem(7);
                        if(curItem.getAmount() > 100)
                            break;
                        curItem.setAmount(curItem.getAmount()+1);
                        inventory.setItem(7, curItem);
                        clickedBlock.setType(Material.AIR);
                        break;
                    case IRON_BLOCK:
                    case STONE_SLAB2:
                    case IRON_FENCE:
                        curItem = inventory.getItem(8);
                        if(curItem.getAmount() > 100)
                            break;
                        curItem.setAmount(curItem.getAmount()+1);
                        inventory.setItem(8, curItem);
                        clickedBlock.setType(Material.AIR);
                        break;
                    case DIAMOND_BLOCK:
                        event.getPlayer().getServer().broadcastMessage(ChatColor.GOLD + "[RE] Кто-то нашел эйрдроп");
                        WeaponHelper.doRandomWeaponDrop(clickedBlock.getLocation(), 5);
                        clickedBlock.setType(Material.AIR);
                        break;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public static void onEvent(EntityPickupItemEvent event) {
        if(RoyaleHelper.isStarted() && event.getEntity() instanceof Player) {
            event.setCancelled(true);
            ItemStack pickupItem = event.getItem().getItemStack();
            PlayerInventory inventory = ((Player) event.getEntity()).getInventory();
            int inventoryNumber;
            String weaponName;
            switch (pickupItem.getType()){
                case WOOD_HOE:
                    inventoryNumber = 1;
                    weaponName = "Дробовик(ПКМ для перезаряда)";
                    break;
                case STONE_HOE:
                    inventoryNumber = 2;
                    weaponName = "Пистолет(ПКМ для перезаряда)";
                    break;
                case IRON_HOE:
                    inventoryNumber = 3;
                    weaponName = "Автомат(ПКМ для перезаряда)";
                    break;
                case GOLD_HOE:
                    inventoryNumber = 4;
                    weaponName = "Снайперская винтовка(ПКМ для перезаряда)";
                    break;
                case DIAMOND_HOE:
                    inventoryNumber = 5;
                    weaponName = "Пистолет-пулемкт(ПКМ для перезаряда)";
                    break;
                default:
                    return;
            }
            if(inventory.getItem(inventoryNumber) == null) {
                ItemMeta pickupMeta = pickupItem.getItemMeta();
                pickupMeta.setDisplayName(weaponName);
                pickupItem.setItemMeta(pickupMeta);
                inventory.setItem(inventoryNumber, pickupItem);
            }
        }
    }
}
