package dev.hevav.royaleevent.helpers;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class BlockHelper {

    public static ArrayList<Block> getBlocks(Block start, int radius){
        ArrayList<Block> blocks = new ArrayList<>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
                    Block block = start.getWorld().getBlockAt(new Location(start.getWorld(), x, y, z));
                    if(block.getType() == start.getType())
                        blocks.add(block);
                }
            }
        }
        return blocks;
    }
}
