package dev.hevav.royaleevent.helpers;

import org.bukkit.*;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class BlockHelper {

    public static ArrayList<Block> getBlocks(Block start, int radiusX, int radiusY, int radiusZ){
        ArrayList<Block> blocks = new ArrayList<>();
        for(double x = start.getLocation().getX() - radiusX; x <= start.getLocation().getX() + radiusX; x++){
            for(double y = start.getLocation().getY() - radiusY; y <= start.getLocation().getY() + radiusY; y++){
                for(double z = start.getLocation().getZ() - radiusZ; z <= start.getLocation().getZ() + radiusZ; z++){
                    Block block = start.getWorld().getBlockAt(new Location(start.getWorld(), x, y, z));
                    if(block.getType() == start.getType())
                        blocks.add(block);
                }
            }
        }
        return blocks;
    }

    public static Location getRandomDropPoint(World world){
        WorldBorder border = world.getWorldBorder();
        Location borderCenter = border.getCenter();
        int dist = (int) border.getSize()/3;

        int x = (int) (Math.random()*dist + dist);
        int z = (int) (Math.random()*dist + dist);

        Location location = borderCenter.add(x, 0, z);
        Chunk chunk = location.getChunk();
        if(!chunk.isLoaded()) {
            chunk.load(true);
        }

        location.setY(world.getHighestBlockYAt(location));
        Block under = world.getBlockAt(location);

        if(under.getType() == Material.LAVA || under.getType() == Material.STATIONARY_LAVA) {
            under.setType(Material.IRON_BLOCK);
            location.add(0, 1, 0);
        }

        return location;
    }
}
