package de.matev3.paintball.paintball;

import de.matev3.paintball.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class Weapon {

    private Main plugin;
    private Material material;
    private long reloadTime;
    private String name;
    private double damage;
    private int price;
    private int xs;

    private ArrayList<String> shotWeapon;

    public Weapon(Main plugin, Material material, long reloadTime, double damage, String name, int price, int xs) {
        this.plugin = plugin;
        this.material = material;
        this.reloadTime = reloadTime;
        this.damage = damage;
        this.name = name;
        this.price = price;
        this.xs = xs;

        shotWeapon = new ArrayList<>();
    }

    public void shoot(Player player) {
        if (!shotWeapon.contains(player.getName())) {
            shotWeapon.add(player.getName());
            shooteffects(player);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    shotWeapon.remove(player.getName());
                }
            }, reloadTime);
        }
    }

    public  abstract void  shooteffects(Player player);

    public Material getMaterial() {
        return material;
    }

    public double getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getXs() {
        return xs;
    }
}
