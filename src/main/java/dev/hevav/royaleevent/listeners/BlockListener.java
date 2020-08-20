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
            int number;
            switch (item.getType()){
                default:
                    event.setCancelled(true);
                    return;
                case WOOD:
                case WOOD_STEP:
                case WOOD_STAIRS:
                    number = 6;
                    break;
                case BRICK:
                case BRICK_STAIRS:
                    number = 7;
                    break;
                case IRON_BLOCK:
                case IRON_FENCE:
                case STONE_SLAB2:
                    number = 8;
                    break;
            }
            int amount = item.getAmount();
            if(amount < 2){
                event.setCancelled(true);
                return;
            }
            item.setAmount(amount-1);
            inventory.setItem(number, item);
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
            if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
                int number;
                String text;
                Material material;
                switch (item.getType()) {
                    case WOOD:
                        number = 6;
                        text = "Поставить деревянную лестницу(ЛКМ для смены)";
                        material = Material.WOOD_STAIRS;
                        break;
                    case WOOD_STAIRS:
                        number = 6;
                        text = "Поставить деревянный полублок(ЛКМ для смены)";
                        material = Material.WOOD_STEP;
                        break;
                    case WOOD_STEP:
                        number = 6;
                        text = "Поставить доски(ЛКМ для смены)";
                        material = Material.WOOD;
                        break;
                    case BRICK:
                        number = 7;
                        text = "Поставить кирпичную лестницу(ЛКМ для смены)";
                        material = Material.BRICK_STAIRS;
                        break;
                    case BRICK_STAIRS:
                        number = 7;
                        text = "Поставить кирпич(ЛКМ для смены)";
                        material = Material.BRICK;
                        break;
                    case IRON_BLOCK:
                        number = 8;
                        text = "Поставить железный полублок(ЛКМ для смены)";
                        material = Material.STONE_SLAB2;
                    case STONE_SLAB2:
                        number = 8;
                        text = "Поставить железный забор(ЛКМ для смены)";
                        material = Material.IRON_FENCE;
                        break;
                    case IRON_FENCE:
                        number = 8;
                        text = "Поставить железный блок(ЛКМ для смены)";
                        material = Material.IRON_BLOCK;
                        break;
                    default:
                        return;
                }
                ItemStack block = new ItemStack(material, inventory.getItem(number).getAmount());
                ItemMeta blockMeta = block.getItemMeta();
                blockMeta.setDisplayName(text);
                block.setItemMeta(blockMeta);
                inventory.setItem(number, block);
            }
        }
    }
}
