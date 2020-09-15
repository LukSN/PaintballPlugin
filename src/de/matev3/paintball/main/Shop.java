package de.matev3.paintball.main;

import de.matev3.paintball.paintball.Weapon;
import de.matev3.paintball.paintball.WeaponHandler;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Shop implements Listener {

    @EventHandler
    public void onOpenShop(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getMaterial() == Material.SLIME_BALL) {
                Player player = event.getPlayer();
                player.openInventory(Items.shopInv());
            }
        }
    }

    @EventHandler
    public void onBuy(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory().getTitle().equalsIgnoreCase("Shop")) {
            if (event.getCurrentItem().getEnchantments() == null) return;
            event.setCancelled(true);
            for (Weapon p : WeaponHandler.weapons) {
                if (event.getCurrentItem().getType() == p.getMaterial()) {
                    if (player.getLevel() >= p.getPrice()) {
                        if (p.getPrice() == 0) {
                            player.sendMessage(Main.prefix + "Du hast diese Waffe bereits.");
                        } else {
                            player.getInventory().addItem(getWeapon(p));
                            player.closeInventory();
                            player.setLevel(player.getLevel() - p.getPrice());
                        }
                    } else {
                        int e = player.getLevel();
                        int r = p.getPrice();
                        int i = r - e;
                        player.sendMessage(Main.prefix + "Du brauchst noch §6" + i + " Coins");
                    }
                }
            }
        }
    }

    public static ItemStack getWeapon(Weapon p){
        ItemStack i = new ItemStack(p.getMaterial());
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(p.getName());
        im.addEnchant(Enchantment.DAMAGE_ALL, 12, true);
        i.setItemMeta(im);
        return i;
    }
}
