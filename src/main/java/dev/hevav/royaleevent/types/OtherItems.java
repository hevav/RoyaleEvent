package dev.hevav.royaleevent.types;

import dev.hevav.royaleevent.RoyaleEvent;
import org.bukkit.Material;

public class OtherItems extends Inventorable {
    public Integer inventoryNumber;
    public Chunkable chunkable;

    public OtherItems(Material material, Integer inventoryNumber, String name, Chunkable chunkable){
        this.material = material;
        this.inventoryNumber = inventoryNumber;
        this.name = name;
        this.chunkable = chunkable;
    }

    public static OtherItems Patron = new OtherItems(Material.TRIPWIRE_HOOK, null, RoyaleEvent.config.getString("strings.patron"), null);

    public static OtherItems Wood = new OtherItems(Material.WOOD, 6, RoyaleEvent.config.getString("strings.wood"), Chunkable.Walls(Material.WOOD));
    public static OtherItems WoodStep = new OtherItems(Material.WOOD_STEP, 6, RoyaleEvent.config.getString("strings.woodStep"), Chunkable.Steps(Material.WOOD));
    public static OtherItems WoodStairs = new OtherItems(Material.WOOD_STAIRS, 6, RoyaleEvent.config.getString("strings.woodStairs"), Chunkable.Stairs(Material.WOOD));

    public static OtherItems Bricks = new OtherItems(Material.BRICK, 7, RoyaleEvent.config.getString("strings.bricks"), Chunkable.Walls(Material.BRICK));
    public static OtherItems BricksStairs = new OtherItems(Material.BRICK_STAIRS, 7, RoyaleEvent.config.getString("strings.bricksStairs"), Chunkable.Stairs(Material.BRICK));

    public static OtherItems Iron = new OtherItems(Material.IRON_BLOCK, 8, RoyaleEvent.config.getString("strings.iron"), Chunkable.Walls(Material.IRON_BLOCK));
    public static OtherItems IronSlab = new OtherItems(Material.STONE_SLAB2, 8, RoyaleEvent.config.getString("strings.ironStep"), Chunkable.Steps(Material.IRON_BLOCK));
    public static OtherItems IronFence = new OtherItems(Material.IRON_FENCE, 8, RoyaleEvent.config.getString("strings.ironFence"), Chunkable.Walls(Material.IRON_FENCE));

    public static OtherItems getItemByMaterial(Material material){
        switch (material) {
            case WOOD:
            case CACTUS:
            case SUGAR_CANE_BLOCK:
            case DEAD_BUSH:
                return OtherItems.Wood;
            case WOOD_STAIRS:
                return OtherItems.WoodStairs;
            case WOOD_STEP:
                return OtherItems.WoodStep;
            case BRICK:
            case GLASS:
                return OtherItems.Bricks;
            case BRICK_STAIRS:
                return OtherItems.BricksStairs;
            case IRON_BLOCK:
            case QUARTZ_BLOCK:
            case QUARTZ_STAIRS:
                return OtherItems.Iron;
            case STONE_SLAB2:
                return OtherItems.IronSlab;
            case IRON_FENCE:
                return OtherItems.IronFence;
            default:
                return null;
        }
    }

    public static OtherItems switchItem(Material material){
        switch (material) {
            case WOOD:
                return OtherItems.WoodStairs;
            case WOOD_STAIRS:
                return OtherItems.WoodStep;
            case WOOD_STEP:
            case WOOD_DOUBLE_STEP:
                return OtherItems.Wood;
            case BRICK:
                return OtherItems.BricksStairs;
            case BRICK_STAIRS:
                return OtherItems.Bricks;
            case IRON_BLOCK:
                return OtherItems.IronSlab;
            case STONE_SLAB2:
                return OtherItems.IronFence;
            case IRON_FENCE:
                return OtherItems.Iron;
            default:
                return null;
        }
    }
}
