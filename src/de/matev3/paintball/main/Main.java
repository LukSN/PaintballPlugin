package de.matev3.paintball.main;

import de.matev3.paintball.PaintballEvents;
import de.matev3.paintball.commands.SetProtLol;
import de.matev3.paintball.commands.SetSpawn;
import de.matev3.paintball.commands.addSpawn;
import de.matev3.paintball.events.PlayerDieAct;
import de.matev3.paintball.events.PlayerJoinAct;
import de.matev3.paintball.paintball.WeaponHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    private static Main plugin;
    public static String prefix = "§7[§6Empty§7] §7";
    public static ArrayList<Location> locs = new ArrayList<>();

    public static PaintballEvents event;

    public void onEnable() {
        plugin = this;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(event = new PaintballEvents(true, Bukkit.getWorld("world"), "§7[§6Paintball§7] §7"), this);
        pluginManager.registerEvents(new WeaponHandler(this), this);
        pluginManager.registerEvents(new PlayerJoinAct(), this);
        pluginManager.registerEvents(new PlayerDieAct(), this);
        pluginManager.registerEvents(new Shop(), this);

        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("setprot").setExecutor(new SetProtLol());
        getCommand("addspawn").setExecutor(new addSpawn());
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static ItemStack newItem(Material material, int amount, String name) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(name != null) itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void updateScoreboard(){
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        Scoreboard board = sm.getNewScoreboard();
        Objective score = board.registerNewObjective("aaa", "bbb");

        score.setDisplayName("§aStats (Killstreaks)");
        score.setDisplaySlot(DisplaySlot.SIDEBAR);

        if(event != null && event.eventStatus > 1) {
            score.setDisplayName(event.statusString);
            for(String s : event.eventPoints.keySet()) {
                score.getScore(s).setScore(event.eventPoints.get(s));
            }
        }
        else {
            for(String s : PlayerDieAct.killStreakRekord.keySet()) {
                score.getScore(s).setScore(PlayerDieAct.killStreakRekord.get(s));
            }
        }

        Bukkit.getOnlinePlayers().forEach(p -> p.setScoreboard(board));
    }

}
