package dev.hevav.royaleevent.listeners;

import dev.hevav.royaleevent.helpers.RoyaleHelper;
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
            PlayerInventory inventory = event.getPlayer().getInventory();
            ItemStack item = inventory.getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            if (meta == null || !meta.hasCustomModelData()){
                event.setCancelled(true);
                return;
            }
            int cmd = meta.getCustomModelData();
            if(cmd >= 22822820 && cmd <= 22822842){
                int amount = item.getAmount();
                if(amount < 2){
                    event.setCancelled(true);
                    return;
                }
                item.setAmount(amount-1);
                switch (cmd/10){
                    case 2282282:
                        inventory.setItem(6, item);
                        break;
                    case 2282283:
                        inventory.setItem(7, item);
                        break;
                    case 2282284:
                        inventory.setItem(8, item);
                        break;
                }
            }
            else
                event.setCancelled(true);
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
            Inventory inventory = event.getPlayer().getInventory();
            if (event.getAction() == Action.LEFT_CLICK_BLOCK && meta.hasCustomModelData()) {
                switch (meta.getCustomModelData()) {
                    case 22822820:
                        ItemStack wood = new ItemStack(Material.OAK_STAIRS, inventory.getItem(6).getAmount());
                        ItemMeta woodMeta = wood.getItemMeta();
                        woodMeta.setDisplayName("Place wooden stairs(Left mouse for change)");
                        woodMeta.setCustomModelData(22822821);
                        wood.setItemMeta(woodMeta);
                        inventory.setItem(6, wood);
                        return;
                    case 22822821:
                        wood = new ItemStack(Material.OAK_SLAB, inventory.getItem(6).getAmount());
                        woodMeta = wood.getItemMeta();
                        woodMeta.setDisplayName("Place wooden slab(Left mouse for change)");
                        woodMeta.setCustomModelData(22822822);
                        wood.setItemMeta(woodMeta);
                        inventory.setItem(6, wood);
                        return;
                    case 22822822:
                        wood = new ItemStack(Material.OAK_PLANKS, inventory.getItem(6).getAmount());
                        woodMeta = wood.getItemMeta();
                        woodMeta.setDisplayName("Place wood(Left mouse for change)");
                        woodMeta.setCustomModelData(22822820);
                        wood.setItemMeta(woodMeta);
                        inventory.setItem(6, wood);
                        return;
                    case 22822830:
                        ItemStack bricks = new ItemStack(Material.BRICK_STAIRS, inventory.getItem(7).getAmount());
                        ItemMeta bricksMeta = bricks.getItemMeta();
                        bricksMeta.setDisplayName("Place brick stairs(Left mouse for change)");
                        bricksMeta.setCustomModelData(22822831);
                        bricks.setItemMeta(bricksMeta);
                        inventory.setItem(7, bricks);
                        return;
                    case 22822831:
                        bricks = new ItemStack(Material.BRICK_SLAB, inventory.getItem(7).getAmount());
                        bricksMeta = bricks.getItemMeta();
                        bricksMeta.setDisplayName("Place brick slabs(Left mouse for change)");
                        bricksMeta.setCustomModelData(22822832);
                        bricks.setItemMeta(bricksMeta);
                        inventory.setItem(7, bricks);
                        return;
                    case 22822832:
                        bricks = new ItemStack(Material.BRICKS, inventory.getItem(7).getAmount());
                        bricksMeta = bricks.getItemMeta();
                        bricksMeta.setDisplayName("Place bricks(Left mouse for change)");
                        bricksMeta.setCustomModelData(22822830);
                        bricks.setItemMeta(bricksMeta);
                        inventory.setItem(7, bricks);
                        return;
                    case 22822840:
                        ItemStack iron = new ItemStack(Material.SMOOTH_STONE_SLAB, inventory.getItem(8).getAmount());
                        ItemMeta ironMeta = iron.getItemMeta();
                        ironMeta.setDisplayName("Place iron slab(Left mouse for change)");
                        ironMeta.setCustomModelData(22822841);
                        iron.setItemMeta(ironMeta);
                        inventory.setItem(8, iron);
                    case 22822841:
                        iron = new ItemStack(Material.IRON_BARS, inventory.getItem(8).getAmount());
                        ironMeta = iron.getItemMeta();
                        ironMeta.setDisplayName("Place iron bars(Left mouse for change)");
                        ironMeta.setCustomModelData(22822842);
                        iron.setItemMeta(ironMeta);
                        inventory.setItem(8, iron);
                        return;
                    case 22822842:
                        iron = new ItemStack(Material.IRON_BLOCK, inventory.getItem(8).getAmount());
                        ironMeta = iron.getItemMeta();
                        ironMeta.setDisplayName("Place iron block(Left mouse for change)");
                        ironMeta.setCustomModelData(22822840);
                        iron.setItemMeta(ironMeta);
                        inventory.setItem(8, iron);
                        return;
                }
            }
        }
    }
}
