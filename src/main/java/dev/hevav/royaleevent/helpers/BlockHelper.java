package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.types.Chunkable;
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

        int x = (int) (Math.random()*dist - dist/2);
        int z = (int) (Math.random()*dist - dist/2);

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

    public static Location getChunkableStart(Location fromLocation){
        int xId = fromLocation.getBlockX()/5;
        int yId = fromLocation.getBlockY()/5;
        int zId = fromLocation.getBlockZ()/5;
        return new Location(fromLocation.getWorld(), xId*5, yId*5, zId*5);
    }

    public static int deleteChunk(Location fromLocation, Material material){
        Location removeLocation = getChunkableStart(fromLocation);
        int count = 0;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                for(int k = 0; k < 5; k++){
                    Block block = removeLocation.add(k, j, i).getBlock();
                    if(block.getType() == material) {
                        block.setType(Material.AIR);
                        ++count;
                    }
                }
            }
        }
        return count/25;
    }

    public static boolean verifyChunk(Chunkable chunkable){
        for (int i = 1; i < 4; i++){
            for (int j = 1; j < 4; j++){
                for(int k = 1; k < 4; k++){
                    if(chunkable.chunk.get(i).get(j).get(k) != Material.AIR)
                        return false;
                }
            }
        }
        return true;
    }

    public static Location nextChunkByPlayer(Location playerLocation, Location chunkLocation){
        float playerYaw = playerLocation.getYaw();
        int x = 0;
        int z = 0;
        if (playerYaw >= 315 && playerYaw < 45){
            z = -5;
        }
        else if (playerYaw >= 45 && playerYaw < 135){
            x = 5;
        }
        else if (playerYaw >= 155 && playerYaw < 225){
            z = 5;
        }
        else{
            x = -5;
        }
        return chunkLocation.add(x, 1, z);
    }
}
