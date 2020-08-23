package dev.hevav.royaleevent.types;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.helpers.BlockHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Chunkable {
    public List<List<List<Material>>> chunk;
    public Location startLocation;

    public Chunkable(List<List<List<Material>>> chunk, Location startLocation) {
        this.chunk = chunk;
        this.startLocation = startLocation;
    }

    public Chunkable(List<List<List<Material>>> chunk) {
        this.chunk = chunk;
    }

    public static Chunkable fromLocation(Location location) {
        List<List<List<Material>>> chunk = new ArrayList<>();
        Location chunkLocation = BlockHelper.getChunkableStart(location).add(-5, 0, 0);
        World world = chunkLocation.getWorld();
        for (int i = chunkLocation.getBlockY()-1; i < chunkLocation.getBlockY() + 5; i++) {
            List<List<Material>> chunkXY = new ArrayList<>();
            for (int j = chunkLocation.getBlockX()-1; j < chunkLocation.getBlockX() + 5; j++) {
                List<Material> chunkX = new ArrayList<>();
                for (int k = chunkLocation.getBlockZ()-1; k < chunkLocation.getBlockZ() + 5; k++) {
                    Material material = world.getBlockAt(j, i, k).getType();
                    chunkX.add(material);
                }
                chunkXY.add(chunkX);
            }
            chunk.add(chunkXY);
        }
        return new Chunkable(chunk, chunkLocation);
    }

    public void replaceWith(Chunkable chunkable) {
        World world = startLocation.getWorld();
        int x = startLocation.getBlockX();
        int y = startLocation.getBlockY();
        int z = startLocation.getBlockZ();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    Material setMaterial = chunkable.chunk.get(i).get(j).get(k);
                    Block block = world.getBlockAt(x+j, y+i, z+k);
                    if (setMaterial != Material.AIR && (block.getType() == Material.AIR || block.getType() == Material.LONG_GRASS)){
                        Bukkit.getScheduler().scheduleSyncDelayedTask(RoyaleEvent.getInstance(), ()->{
                            block.setType(setMaterial);
                        });
                    }
                }
            }
        }
    }

    public Chunkable rotate(int yaw){
        List<List<List<Material>>> chunk = new ArrayList<>();
        switch (BlockHelper.yawToFace(yaw)){
            case WEST:
                for(int i = 0; i < 5; i++){
                    List<List<Material>> chunkXY = new ArrayList<>();
                    List<List<Material>> thischunkXY = this.chunk.get(i);
                    for(int j = 4; j >= 0; j--){
                        List<Material> thischunkX = thischunkXY.get(j);
                        List<Material> chunkX = new ArrayList<>();
                        for (int k = 4; k >= 0; k--) {
                            chunkX.add(thischunkX.get(k));
                        }
                        chunkXY.add(chunkX);
                    }
                    chunk.add(chunkXY);
                }
                break;
            case SOUTH:
                for(int i = 0; i < 5; i++){
                    List<List<Material>> thischunkXY = this.chunk.get(i);
                    chunk.add(BlockHelper.transpose(thischunkXY));
                }
                break;
            case NORTH:
                for(int i = 0; i < 5; i++){
                    List<List<Material>> chunkXY = new ArrayList<>();
                    List<List<Material>> thischunkXY = this.chunk.get(i);
                    for(int j = 4; j >= 0; j--){
                        List<Material> thischunkX = thischunkXY.get(j);
                        List<Material> chunkX = new ArrayList<>();
                        for (int k = 4; k >= 0; k--) {
                            chunkX.add(thischunkX.get(k));
                        }
                        chunkXY.add(chunkX);
                    }
                    chunk.add(BlockHelper.transpose(chunkXY));
                }
                break;
        }
        if(chunk.size() > 0)
            return new Chunkable(chunk, this.startLocation);
        return this;
    }

    public static Chunkable Campfire = new Chunkable(
            Arrays.asList(
                    Arrays.asList(
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.STONE, Material.COAL_BLOCK, Material.STONE, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.STONE, Material.COAL_BLOCK, Material.STONE, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.COAL_BLOCK, Material.COAL_BLOCK, Material.STONE, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            )
                    ),
                    Arrays.asList(
                            Arrays.asList(
                                    Material.AIR, Material.WOOD_STEP, Material.WOOD_STAIRS, Material.WOOD_STEP, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.WOOD_STEP, Material.THIN_GLASS, Material.ORANGE_GLAZED_TERRACOTTA, Material.THIN_GLASS, Material.WOOD_STEP
                            ),
                            Arrays.asList(
                                    Material.WOOD_STAIRS, Material.ORANGE_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.RED_GLAZED_TERRACOTTA, Material.WOOD_STAIRS
                            ),
                            Arrays.asList(
                                    Material.WOOD_STEP, Material.YELLOW_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.THIN_GLASS, Material.WOOD_STEP
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.WOOD_STEP, Material.WOOD_STAIRS, Material.WOOD, Material.AIR
                            )
                    ),
                    Arrays.asList(
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.ORANGE_GLAZED_TERRACOTTA, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.YELLOW_GLAZED_TERRACOTTA, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            )
                    ),
                    Arrays.asList(
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.THIN_GLASS, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            )
                    ),
                    Arrays.asList(
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                            )
                    )
            )
    );

    public static Chunkable Stairs(Material material) {
        return new Chunkable(
                Arrays.asList(
                        Arrays.asList(
                                Arrays.asList(
                                        material, material, material, material, material
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        ),Arrays.asList(
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        material, material, material, material, material
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        ),Arrays.asList(
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        material, material, material, material, material
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        ),Arrays.asList(
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        material, material, material, material, material
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        ),Arrays.asList(
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        material, material, material, material, material
                                )
                        )
                )
        );
    }
    public static Chunkable Walls(Material material) {
        return new Chunkable(
                Arrays.asList(
                        Arrays.asList(
                                Arrays.asList(
                                        material, material, material, material, material
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        ),Arrays.asList(
                                Arrays.asList(
                                        material, material, material, material, material
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        ),Arrays.asList(
                                Arrays.asList(
                                        material, material, material, material, material
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        ),Arrays.asList(
                                Arrays.asList(
                                        material, material, material, material, material
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        ),Arrays.asList(
                                Arrays.asList(
                                        material, material, material, material, material
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        )
                )
        );
    }
    public static Chunkable Steps(Material material) {
        return new Chunkable(
                Arrays.asList(
                        Arrays.asList(
                            Arrays.asList(
                                    material, material, material, material, material
                            ),
                            Arrays.asList(
                                    material, material, material, material, material
                            ),
                            Arrays.asList(
                                    material, material, material, material, material
                            ),
                            Arrays.asList(
                                    material, material, material, material, material
                            ),
                            Arrays.asList(
                                    material, material, material, material, material
                            )
                        ),
                        Arrays.asList(
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        ),Arrays.asList(
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        ),Arrays.asList(
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        ),Arrays.asList(
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                ),
                                Arrays.asList(
                                        Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
                                )
                        )
                )
        );
    }
}
