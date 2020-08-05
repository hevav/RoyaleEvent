package dev.hevav.royaleevent.listeners;

import dev.hevav.royaleevent.helpers.BlockHelper;
import dev.hevav.royaleevent.helpers.RoyaleHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponListener implements org.bukkit.event.Listener {
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
            if(meta.hasCustomModelData() && meta.getCustomModelData() == 22822810){
                switch (clickedBlock.getType()) {
                    case OAK_LOG:
                        for(Block block : BlockHelper.getBlocks(clickedBlock, 7)){
                            ItemStack curItem = inventory.getItem(6);
                            if(curItem.getAmount() > 100)
                                break;
                            block.setType(Material.AIR);
                            curItem.setAmount(curItem.getAmount()+1);
                            inventory.setItem(6, curItem);
                        }
                        return;
                    case OAK_SLAB:
                    case OAK_PLANKS:
                    case OAK_STAIRS:
                        ItemStack curItem = inventory.getItem(6);
                        if(curItem.getAmount() > 100)
                            break;
                        curItem.setAmount(curItem.getAmount()+1);
                        inventory.setItem(6, curItem);
                        clickedBlock.setType(Material.AIR);
                        return;
                    case BRICKS:
                    case BRICK_SLAB:
                    case BRICK_STAIRS:
                        curItem = inventory.getItem(7);
                        if(curItem.getAmount() > 100)
                            break;
                        curItem.setAmount(curItem.getAmount()+1);
                        inventory.setItem(7, curItem);
                        clickedBlock.setType(Material.AIR);
                        return;
                    case IRON_BLOCK:
                    case SMOOTH_STONE_SLAB:
                    case IRON_BARS:
                        curItem = inventory.getItem(8);
                        if(curItem.getAmount() > 100)
                            break;
                        curItem.setAmount(curItem.getAmount()+1);
                        inventory.setItem(8, curItem);
                        clickedBlock.setType(Material.AIR);
                        return;
                }
            }
        }
    }

}
