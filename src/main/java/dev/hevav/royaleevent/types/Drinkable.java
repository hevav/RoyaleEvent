package dev.hevav.royaleevent.types;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Drinkable extends Inventorable {
    public int redoHealth;
    public int redoFoodLevel;

    public Drinkable(Material material, String name, int redoHealth, int redoFoodLevel){
        this.material = material;
        this.name = name;
        this.redoHealth = redoHealth;
        this.redoFoodLevel = redoFoodLevel;
    }

    public void drink(Player player){
        player.setHealth(Math.min(player.getHealth()+redoHealth, 20));
        player.setFoodLevel(Math.min(player.getFoodLevel()+redoFoodLevel, 20));
    }

    public static Drinkable Slurp = new Drinkable(Material.GLASS_BOTTLE, "Восстановитель здоровья", 10, 1); //TODO: fix slurp
    public static Drinkable Regen = new Drinkable(Material.MILK_BUCKET, "Восстановитель хавки", 1, 10);

    public static Drinkable getDrinkableFromMaterial(Material material){
        switch (material){
            case GLASS_BOTTLE:
                return Slurp;
            case MILK_BUCKET:
                return Regen;
            default:
                return null;
        }
    }
}
