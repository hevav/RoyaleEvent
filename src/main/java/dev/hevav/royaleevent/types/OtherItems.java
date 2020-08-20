package dev.hevav.royaleevent.types;

import org.bukkit.Material;

public class OtherItems extends Inventorable {

    public OtherItems(Material material, Integer inventoryNumber, String name){
        this.material = material;
        this.inventoryNumber = inventoryNumber;
        this.name = name;
    }

    public static OtherItems Patron = new OtherItems(Material.TRIPWIRE_HOOK, null, "Патроны");

    public static OtherItems Wood = new OtherItems(Material.WOOD, 6, "Поставить доски(ЛКМ для смены)");
    public static OtherItems WoodStep = new OtherItems(Material.WOOD_STEP, 6, "Поставить деревянный полублок(ЛКМ для смены)");
    public static OtherItems WoodStairs = new OtherItems(Material.WOOD_STAIRS, 6, "Поставить деревянную лестницу(ЛКМ для смены)");

    public static OtherItems Bricks = new OtherItems(Material.BRICK, 7, "Поставить кирпич(ЛКМ для смены)");
    public static OtherItems BricksStairs = new OtherItems(Material.BRICK_STAIRS, 7, "Поставить кирпичную лестницу(ЛКМ для смены)");

    public static OtherItems Iron = new OtherItems(Material.IRON_BLOCK, 8, "Патроны");
    public static OtherItems IronSlab = new OtherItems(Material.STONE_SLAB2, 8, "Поставить железный полублок(ЛКМ для смены)");
    public static OtherItems IronFence = new OtherItems(Material.IRON_FENCE, 8, "Поставить железный забор(ЛКМ для смены)");

    public static OtherItems getItemByMaterial(Material material){
        switch (material) {
            case WOOD:
                return OtherItems.Wood;
            case WOOD_STAIRS:
                return OtherItems.WoodStairs;
            case WOOD_STEP:
            case WOOD_DOUBLE_STEP:
                return OtherItems.WoodStep;
            case BRICK:
                return OtherItems.Bricks;
            case BRICK_STAIRS:
                return OtherItems.BricksStairs;
            case IRON_BLOCK:
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
