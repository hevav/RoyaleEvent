package dev.hevav.royaleevent.types;

import dev.hevav.royaleevent.helpers.BlockHelper;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
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
        Location chunkLocation = BlockHelper.getChunkableStart(location);
        for (int i = 0; i < 5; i++) {
            List<List<Material>> chunkXY = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                List<Material> chunkX = new ArrayList<>();
                for (int k = 0; k < 5; k++) {
                    Material material = chunkLocation.add(k, j, i).getBlock().getType();
                    chunkX.add(material);
                }
                chunkXY.add(chunkX);
            }
            chunk.add(chunkXY);
        }
        return new Chunkable(chunk, chunkLocation);
    }

    public void replaceWith(Chunkable chunkable) {
        if (BlockHelper.verifyChunk(this)) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        Material setMaterial = chunkable.chunk.get(i).get(j).get(k);
                        if (setMaterial != Material.AIR)
                            startLocation.add(k, i, j).getBlock().setType(setMaterial);
                    }
                }
            }
        }
    }

    public static Chunkable Campfire = new Chunkable(
            Arrays.asList(
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
                                    Material.AIR, Material.AIR, Material.RED_GLAZED_TERRACOTTA, Material.AIR, Material.AIR
                            ),
                            Arrays.asList(
                                    Material.AIR, Material.AIR, Material.ORANGE_GLAZED_TERRACOTTA, Material.RED_GLAZED_TERRACOTTA, Material.AIR
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
                        ),Arrays.asList(
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
                        )
                )
        );
    }
}
