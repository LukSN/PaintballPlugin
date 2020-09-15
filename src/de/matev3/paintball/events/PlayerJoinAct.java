package de.matev3.paintball.events;

import de.matev3.paintball.PaintballEvents;
import de.matev3.paintball.main.Items;
import de.matev3.paintball.main.Main;
import de.matev3.paintball.main.Shop;
import de.matev3.paintball.paintball.WeaponHandler;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinAct implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(Main.prefix + "§6" + event.getPlayer().getName() + " §7hat das Spiel betreten.");

        spawn(player);

        Main.updateScoreboard();
    }

    public static void spawn(Player player){
        FileConfiguration config = Main.getPlugin().getConfig();
        World world = Bukkit.getWorld(config.getString("spawn.world"));
        double x = config.getDouble("spawn.X");
        double y = config.getDouble("spawn.Y");
        double z = config.getDouble("spawn.Z");
        float yaw = (float) config.getDouble("spawn.Yaw");
        float pitch = (float) config.getDouble("spawn.Pitch");
        Location loc = new Location(world, x, y, z, yaw, pitch);
        player.teleport(loc);

        ItemStack gun = Items.gun();
        ItemStack shop = Items.shop();

        player.getInventory().clear();
        player.getInventory().addItem(Shop.getWeapon(WeaponHandler.weapons.get(PaintballEvents.standardWeapon)));
        player.getInventory().setItem(8, shop);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(Main.prefix + "§6" + event.getPlayer().getName() + " §7hat das Spiel verlassen.");
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        FileConfiguration config = Main.getPlugin().getConfig();
        World world = Bukkit.getWorld(config.getString("spawn.world"));
        if (!(event.getEntity().getWorld() == world)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void fallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        }
    }

}
