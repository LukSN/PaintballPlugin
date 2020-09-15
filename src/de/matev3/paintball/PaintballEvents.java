package de.matev3.paintball;

import de.matev3.paintball.main.Main;
import de.matev3.paintball.paintball.WeaponHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
public class PaintballEvents extends Events implements Listener {
    public static int standardWeapon = 0;
    boolean change;
    boolean lucky;

    public PaintballEvents(boolean timer, World world, String prefix) {
        super(timer, world, prefix);
    }

    @EventHandler
    public void onOpenShop(PlayerInteractEvent event){
        if(event.getItem() != null && event.getItem().getType() == Material.DIAMOND){
            Inventory inventory  = Bukkit.createInventory(null, 9*3, "§6§lWähle Dauer");

            inventory.setItem(10, Main.newItem(Material.WATCH, 3, "§6♢§b 3 Minuten §6♢"));
            inventory.setItem(11, Main.newItem(Material.WATCH, 5, "§6♢§b 5 Minuten §6♢"));
            inventory.setItem(12, Main.newItem(Material.WATCH, 10, "§6♢§b 10 Minuten §6♢"));
            inventory.setItem(13, Main.newItem(Material.WATCH, 15, "§6♢§b 15 Minuten §6♢"));
            inventory.setItem(16, Main.newItem(Material.BARRIER, 1, "§6♢§b Close §6♢"));

            event.getPlayer().openInventory(inventory);
        }
    }

    private int commandMinutes;
    private int commandGun;

    @EventHandler
    public void onShopClicked(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player) || event.getCurrentItem() == null || event.getWhoClicked().getWorld() != world) return;
        Player player = (Player) event.getWhoClicked();

        if(player.getInventory().getItemInMainHand() != null){
            if(player.getInventory().getItemInMainHand().getType() != Material.DIAMOND) return;
        } else return;

        event.setCancelled(true);

        if(event.getCurrentItem().getType() == Material.BARRIER) {
            player.closeInventory();
        }
        else if(event.getCurrentItem().getType() == Material.WATCH){
            commandMinutes = event.getCurrentItem().getAmount();
            player.closeInventory();
            openSecondShop(player);
        }
        else {
            commandGun = event.getSlot() - 10;
            player.closeInventory();
            player.performCommand("event " + commandGun + " 30 "+  commandMinutes);
        }
    }

    public void openSecondShop(Player player){
        Inventory inventory  = Bukkit.createInventory(null, 9*3, "§6§lWähle Standartwaffe");

        inventory.setItem(10, Main.newItem(Material.WOOD_HOE, 1, "§6♢§b Gun §6♢"));
        inventory.setItem(11, Main.newItem(Material.BLAZE_ROD, 1, "§6♢§b Rocket Launcher §6♢"));
        inventory.setItem(12, Main.newItem(Material.DIAMOND_HOE, 1, "§6♢§b Sturmgewehr §6♢"));
        inventory.setItem(13, Main.newItem(Material.GOLD_HOE, 1, "§6♢§b Minigun §6♢"));
        inventory.setItem(14, Main.newItem(Material.STONE_HOE, 1, "§6♢§b Shotgun §6♢"));
        inventory.setItem(16, Main.newItem(Material.BARRIER, 1, "§6♢§b Close §6♢"));

        player.openInventory(inventory);
    }

    @Override
    public void teleportToSpawn(Player player) {
        player.setHealth(0);
    }

    @Override
    public void sendStartMessage(int delayMinute, Player player) {
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Main.prefix + "§6" +  player.getName() + " §7hat ein Paintball Event gestartet. Es beginnt in §6" + delayMinute +
                " §7Sekunden! Das Event wird §6" + minutes + " §7Minute dauern und " + "die Standartwaffe ist " +
                (standardWeapon == 0 ? "§6♢§b Gun §6♢" : WeaponHandler.weapons.get(standardWeapon).getName())));
    }

    @Override
    public void setArguments(String[] args) {
        standardWeapon = Integer.valueOf(args[0]);
        delayMinutes = Integer.valueOf(args[1]);
        minutes = Integer.valueOf(args[2]);

        if(args.length > 3) {
            if(args[3].equalsIgnoreCase("change")) {
                lucky = false;
                change = true;
            }
            else if(args[3].equalsIgnoreCase("lucky")) {
                lucky = true;
                change = false;
            }
            else {
                lucky = false;
                change = false;
            }
        }

        if(args.length > 4) {
            if(args[4].equalsIgnoreCase("change")) {
                change = true;
            }
            else if(args[4].equalsIgnoreCase("lucky")) {
                lucky = true;
            }
        }
    }

    /*@Override
    public int onRun() {
        if(timeString.equalsIgnoreCase("00:00")) return 1;

        super.onRun();

        //if(lucky && eventStatus == 2 && (second == 15 || second == 45)) paintball.luckyBlocks.spawnLuckBlock();

        if(change && second == 0 && eventStatus == 2) {
            while(true) {
                Random random = new Random();
                final int ran = random.nextInt(5);
                if(ran != standardWeapon) {
                    standardWeapon = ran;
                    FileConfiguration config = Main.getPlugin().getConfig();

                    for (Player p : world.getPlayers()) {
                        p.sendMessage(paintball.prefix + "§7Die Waffe wurde geändert!");
                        String dir = "players." + p.getName() + ".paintball.invsort.";
                        if(standardWeapon == 0) {
                            ItemStack weapon = new ItemStack(Material.WOOD_HOE);
                            ItemMeta weaponMeta = weapon.getItemMeta();
                            weaponMeta.setDisplayName("§6♢§b Gun §6♢");
                            weapon.setItemMeta(weaponMeta);
                            int slotW = config.getInt(dir + "weapon1");
                            p.getInventory().setItem(config.contains(dir + "weapon1") ? slotW : 1, weapon);
                        }
                        else {
                            int slotW = config.getInt(dir + "weapon1");
                            p.getInventory().setItem(config.contains(dir + "weapon1") ? slotW : 1, ((ItemStack) paintball.shop.items.keySet().toArray()[standardWeapon - 1]));
                        }
                    }
                    break;
                }
            }
        }
        else if(change && second == 5 && eventStatus == 2) world.getPlayers().forEach(p -> p.sendMessage(paintball.prefix + "§7Achtung! In 5 Sekunden ist Waffenwechsel!"));

        return 20;
    }*/
}
