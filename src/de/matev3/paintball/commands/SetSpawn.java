package de.matev3.paintball.commands;

import de.matev3.paintball.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("matev3.setspawn")) {
                FileConfiguration config = Main.getPlugin().getConfig();
                config.set("spawn.world", player.getWorld().getName());
                config.set("spawn.X", player.getLocation().getX());
                config.set("spawn.Y", player.getLocation().getY());
                config.set("spawn.Z", player.getLocation().getZ());
                config.set("spawn.Yaw", player.getLocation().getYaw());
                config.set("spawn.Pitch", player.getLocation().getPitch());
                Main.getPlugin().saveConfig();
                player.sendMessage(Main.prefix +"§aSpawn wurde erfolgreich gesetzt.");
            } else {
                player.sendMessage(Main.prefix +"§4Dazu hast du keine Rechte.");
            }
        } else {
            sender.sendMessage("NOPE");
        }
        return false;
    }

}
