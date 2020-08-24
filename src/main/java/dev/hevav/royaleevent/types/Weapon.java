package dev.hevav.royaleevent.types;

import org.bukkit.Material;

public class Weapon extends Inventorable{
    public int reloadSize;
    public int reloadTicks;

    public int velocity;
    public float playerVelocity;
    public int damage;
    public float cooldown;
    public int shootQuantity;

    public Weapon(Material material, int reloadSize, int reloadTicks, int velocity, float playerVelocity, int damage, float cooldown, int shootQuantity, String name){
        this.reloadSize = reloadSize;
        this.reloadTicks = reloadTicks;
        this.velocity = velocity;
        this.playerVelocity = playerVelocity;
        this.damage = damage;
        this.cooldown = cooldown;
        this.name = name;
        this.material = material;
        this.shootQuantity = shootQuantity;
    }

    public static Weapon Tactical = new Weapon(Material.WOOD_HOE, 7, 12, 6, 4, 45,1.5f, 3, "Дробовик(ПКМ для перезаряда)");
    public static Weapon Pistol = new Weapon(Material.STONE_HOE, 21, 3, 8, 1, 25,6.75f, 1,"Пистолет(ПКМ для перезаряда)");
    public static Weapon SCAR = new Weapon(Material.IRON_HOE, 41, 4, 6, 2, 35,5.5f, 1, "Автомат(ПКМ для перезаряда)");
    public static Weapon Sniper = new Weapon(Material.GOLD_HOE, 2, 18, 15, 10, 60,5.5f, 1, "Снайперская винтовка(ПКМ для перезаряда)");
    public static Weapon UZI = new Weapon(Material.DIAMOND_HOE, 26, 5, 8, 2, 35,5.5f, 1, "Пистолет-пулемет(ПКМ для перезаряда)");

    public static Weapon getWeaponByMaterial(Material material){
        switch (material){
            case WOOD_HOE:
                return Tactical;
            case STONE_HOE:
                return Pistol;
            case IRON_HOE:
                return SCAR;
            case GOLD_HOE:
                return Sniper;
            case DIAMOND_HOE:
                return UZI;
            default:
                return null;
        }
    }
}
