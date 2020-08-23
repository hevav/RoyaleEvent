package dev.hevav.royaleevent.types;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class Placeable extends Inventorable {
    public Chunkable chunkable;
    public List<Material> materials;
    public String tutorial;

    public Placeable(Material material, String name, Chunkable chunkable, List<Material> materials, String tutorial){
        this.material = material;
        this.name = name;
        this.chunkable = chunkable;
        this.materials = materials;
        this.tutorial = tutorial;
    }

    public static final Placeable Campfire = new Placeable(Material.FURNACE, "Костер", Chunkable.Campfire, Arrays.asList(Material.THIN_GLASS, Material.RED_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA, Material.COAL_BLOCK, Material.WOOD, Material.WOOD_STAIRS, Material.WOOD_STEP, Material.STONE), "Встаньте на костер с Shift чтобы захилиться :)");

    public static Placeable getPlaceableByMaterial(Material material){
        switch (material){
            case FURNACE:
                return Campfire;
            default:
                return null;
        }
    }

    public static Placeable getPlaceableByPlacedMaterial(Material material){
        switch (material){
            case RED_GLAZED_TERRACOTTA:
            case YELLOW_GLAZED_TERRACOTTA:
            case ORANGE_GLAZED_TERRACOTTA:
                return Campfire;
            default:
                return null;
        }
    }
}
