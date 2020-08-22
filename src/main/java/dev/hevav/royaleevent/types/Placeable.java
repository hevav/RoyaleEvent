package dev.hevav.royaleevent.types;

import org.bukkit.Material;

public class Placeable extends Inventorable {
    Chunkable chunkable;

    public Placeable(Material material, String name, Chunkable chunkable){
        this.material = material;
        this.name = name;
        this.chunkable = chunkable;
    }

    public static Placeable Campfire = new Placeable(Material.FURNACE, "Костер", Chunkable.Campfire);
}
