package de.matev3.paintball.events;

import de.matev3.paintball.PaintballEvents;
import de.matev3.paintball.main.Items;
import de.matev3.paintball.main.Main;
import de.matev3.paintball.main.Shop;
import de.matev3.paintball.paintball.Weapon;
import de.matev3.paintball.paintball.WeaponHandler;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;

public class PlayerDieAct implements Listener {

    public static HashMap<String, Integer> killStreaks = new HashMap<>();
    public static HashMap<String, Integer> killStreakRekord = new HashMap<>();

    @EventHandler
    public void onDie(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        player.setHealth(20);
        Player killer = event.getEntity().getKiller();

        String name = "";

        if(killer != null) {
            name = killer.getName();
            if (killStreaks.containsKey(name)) killStreaks.put(name, killStreaks.get(name) + 1);
            else killStreaks.put(name, 1);

            if (!killStreakRekord.containsKey(name) || killStreakRekord.get(name) < killStreaks.get(name))
                killStreakRekord.put(name, killStreaks.get(name));

            if (killStreaks.containsKey(player.getName())) killStreaks.put(player.getName(), 0);
        }

        if (killer == null)
            event.setDeathMessage(Main.prefix + "§6" + player.getName() + " §7ist gestorben.");
        else {
            killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            event.setDeathMessage(Main.prefix + "§6" + player.getName() + " §7wurde von §6" + killer.getName() + " §7hops genommen. " +
                    "(§6" + killStreaks.get(name) + "er §7Killstreak)");
            killer.setLevel(killer.getLevel() + 15);
            killer.setHealth(20);
        }

        Random r = new Random();
        int low = 0;
        int high = Main.locs.size();
        int result = r.nextInt(high-low) + low;

        Location loc = Main.locs.get(result);
        player.teleport(loc);

        player.getInventory().clear();

        player.getInventory().addItem(Shop.getWeapon(WeaponHandler.weapons.get(PaintballEvents.standardWeapon)));
        player.getInventory().setItem(8, Items.shop());

        player.setHealth(20);

        Main.updateScoreboard();
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (player.getLocation().getY() < 60) {
            player.setHealth(0);
        }
    }

}
