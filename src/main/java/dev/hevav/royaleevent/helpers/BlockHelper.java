package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.types.Chunkable;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

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

        if(under.getType().equals(Material.LAVA) || under.getType().equals(Material.STATIONARY_LAVA)) {
            under.setType(Material.IRON_BLOCK);
            location.add(0, 1, 0);
        }

        return location;
    }

    public static Location getChunkableStart(Location fromLocation){
        int xId = (fromLocation.getBlockX()+1)/5;
        int yId = (fromLocation.getBlockY()+1)/5;
        int zId = (fromLocation.getBlockZ()+1)/5;
        return new Location(fromLocation.getWorld(), xId*5, yId*5, zId*5);
    }

    public static Location nextChunkByYaw(Location fromLocation, float yaw){
        int xId = fromLocation.getBlockX();
        int yId = fromLocation.getBlockY()-1;
        int zId = fromLocation.getBlockZ();
        switch (yawToFace(yaw)){
            case NORTH:
                zId -= 1;
            case EAST:
                xId += 1;
            case SOUTH:
                zId += 1;
            case WEST:
                xId -= 1;
                break;
        }
        return new Location(fromLocation.getWorld(), xId, yId, zId);
    }

    public static int deleteChunk(Location fromLocation, Material material){
        Location removeLocation = getChunkableStart(fromLocation);
        World world = removeLocation.getWorld();
        int count = 4;
        for (int i = removeLocation.getBlockX()-5; i < removeLocation.getBlockX()+1; i++){
            for (int j = removeLocation.getBlockY()-1; j < removeLocation.getBlockY()+5; j++){
                for(int k = removeLocation.getBlockZ()-1; k < removeLocation.getBlockZ()+5; k++){
                    Block block = world.getBlockAt(i, j, k);
                    if(block.getType().equals(material)) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                            block.setType(Material.AIR);
                        });
                        ++count;
                    }
                }
            }
        }
        return count/5;
    }

    public static <T> List<List<T>> transpose(List<List<T>> table) {
        List<List<T>> ret = new ArrayList<>();
        final int N = table.get(0).size();
        for (int i = 0; i < N; i++) {
            List<T> col = new ArrayList<T>();
            for (List<T> row : table) {
                col.add(row.get(i));
            }
            ret.add(col);
        }
        return ret;
    }

    public static BlockFace yawToFace(float yaw) {
        return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
    }

    private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
}
