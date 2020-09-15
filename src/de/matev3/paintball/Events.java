package de.matev3.paintball;

import de.matev3.paintball.events.PlayerJoinAct;
import de.matev3.paintball.main.Items;
import de.matev3.paintball.main.Main;
import de.matev3.paintball.main.Shop;
import de.matev3.paintball.paintball.WeaponHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class Events implements CommandExecutor {
    public int eventStatus = 0;
    public String statusString;
    public World world;
    public int endPoints;
    public String timeString = "00:00";
    public boolean timer;

    public int delayMinutes;
    public int minutes;

    public String prefix;
    public int minute, second;

    public HashMap<String, Integer> eventPoints = new HashMap<String, Integer>();
    public Integer taskID;

    public Events(boolean timer, World world, String prefix){
        this.prefix = prefix;
        this.world = world;
        this.timer = timer;

        onRun();
    }

    public void onRun() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
            minute = Integer.valueOf(timeString.split(":")[0]);
            second = Integer.valueOf(timeString.split(":")[1]) - 1;

            if (second < 0) {
                second = 59;
                minute--;
            }

            String minuteString = minute < 10 ? "0" + minute : String.valueOf(minute);
            String secondString = second < 10 ? "0" + second : String.valueOf(second);

            if(minute >= 0 && eventStatus != 3) {
                timeString = minuteString + ":" + secondString;
                statusString = "§aEvent - " + timeString;
            }
            if(minute == 0 && second == 0) onEventEnd();
        }, 0, 20);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args){
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if(player.getWorld() != world) return false;

        if(args[0].toLowerCase().equalsIgnoreCase("cancel") && eventStatus > 0) {
            Bukkit.getScheduler().cancelTask(taskID);
            eventStatus = 0;
            sender.sendMessage("Event cancled");
            return false;
        } else if (args[0].toLowerCase().equalsIgnoreCase("cancel") && eventStatus == 0){
            sender.sendMessage("Auf dieser Welt wurde kein Event gestartet!");
            return false;
        }

        setArguments(args);
        sendStartMessage(delayMinutes, player);

        eventStatus = 1;
        eventPoints = new HashMap<>();

        try { Bukkit.getScheduler().cancelTask(taskID); } catch (Exception ignored) { }

        taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
            timeString = (minutes < 10 ? "0" + minutes : String.valueOf(minutes)) + ":00";
            eventStatus = 2;

            for (Player p : world.getPlayers()) {
                p.sendMessage(prefix + "§7Das Event wurde soeben ein gestartet!");
                p.setLevel(0);
                teleportToSpawn(p);
                eventPoints.put(p.getName(), 0);
            }

            if(timer) statusString = "§aEvent - " + timeString;
            else statusString = "§aEvent - Läuft gerade";

        }, 20*delayMinutes);

        return false;
    }

    public abstract void teleportToSpawn(Player player);
    public abstract void sendStartMessage(int delayMinute, Player player);
    public abstract void setArguments(String args[]);

    public void onEventEnd(){
        PaintballEvents.standardWeapon = 0;

        for(Player player : world.getPlayers()){
            player.sendTitle("§6Das Event ist vorbei!", "§7Sehe dir auf dem Scoreboard den Gewinner an");
            player.sendMessage(prefix + "§7Das Event ist vorbei! Guckt auf's Scoreboard, um zu sehen, wer gewonen hat!");
            player.sendMessage(prefix + "§7Der normale Modus startet wieder in 15 Sekunden.");
        }

        eventStatus = 3;
        statusString = "§aEvent - Auswertung";

        taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
            eventStatus = 0;

            for (Player p : world.getPlayers()) {
                p.sendMessage(prefix + "§7Der normale Modus hat wieder gestartet!");
                PlayerJoinAct.spawn(p);
            }

        }, 20*15);
    }
}
