package de.matev3.paintball.paintball;

import de.matev3.paintball.main.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class WeaponHandler implements Listener {

    public static ArrayList<Weapon> weapons;

    public WeaponHandler(Main plugin) {
        weapons = new ArrayList<>();

        weapons.add(new Weapons.Gun(plugin, Material.WOOD_HOE, 7, 7));
        weapons.add(new Weapons.RocketLauncher(plugin, Material.BLAZE_ROD, 30, 200));
        weapons.add(new Weapons.Sturmgewehr(plugin, Material.DIAMOND_HOE, 18, 5));
        weapons.add(new Weapons.Minigun(plugin, Material.GOLD_HOE, 1, 1));
        weapons.add(new Weapons.Shotgun(plugin, Material.STONE_HOE, 12, 3));
    }

    @EventHandler
    public void handleWeaponShot(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && event.getItem() != null) {
            Weapon weapon = checkWeaponMaterial(event.getItem().getType());
            if (weapon != null) {
                if (player.getLevel() < weapon.getXs()) {
                    player.playSound(player.getLocation(), Sound.BLOCK_METAL_HIT, 1, 1);
                } else {
                    //player.setLevel(player.getLevel() - weapon.getXs());
                    weapon.shoot(event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void handleWeaponDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Projectile)) return;
        Projectile projectile = (Projectile) event.getDamager();
        if (!(projectile.getShooter() instanceof Player)) return;
        Player player = (Player) projectile.getShooter();
        Weapon weapon = checkWeaponMaterial(player.getItemInHand().getType());
        Player knecht = (Player) event.getEntity();
        FileConfiguration config = Main.getPlugin().getConfig();
        if (knecht.getLocation().getY() > config.getDouble("prot")) return;
        if (player.getLocation().getY() > config.getDouble("prot")) return;
        if (knecht.getHealth() - weapon.getDamage() < 0) {
            knecht.setHealth(0);
        }
        else
            knecht.setHealth(knecht.getHealth() - weapon.getDamage());
    }

    private  Weapon checkWeaponMaterial(Material material) {
        for (Weapon current : weapons) {
            if (current.getMaterial() == material)
                return  current;
        }
        return null;
    }

}
