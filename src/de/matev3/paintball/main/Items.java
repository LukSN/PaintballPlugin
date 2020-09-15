package de.matev3.paintball.main;

import de.matev3.paintball.paintball.Weapon;
import de.matev3.paintball.paintball.WeaponHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

    public static ItemStack gun() {
        ItemStack gun = new ItemStack(Material.WOOD_HOE);
        ItemMeta gunmeta = gun.getItemMeta();
        gunmeta.setDisplayName("Gun");
        gunmeta.addEnchant(Enchantment.DAMAGE_ALL, 12, true);
        gun.setItemMeta(gunmeta);

        return gun;
    }

    public static ItemStack shop() {
        ItemStack shop = new ItemStack(Material.SLIME_BALL);
        ItemMeta shopmeta = shop.getItemMeta();
        shopmeta.setDisplayName("Shop");
        shop.setItemMeta(shopmeta);

        return shop;
    }

    public static Inventory shopInv() {
        Inventory inv = Bukkit.createInventory(null, 1*9, "Shop");
        for (Weapon p : WeaponHandler.weapons) {
            ItemStack pi = new ItemStack(p.getMaterial());
            ItemMeta pimeta = pi.getItemMeta();
            pimeta.setDisplayName(p.getName() + " || §6" + p.getPrice() + " Coins");
            pi.setItemMeta(pimeta);

            inv.addItem(pi);
        }
        return inv;
    }

}
