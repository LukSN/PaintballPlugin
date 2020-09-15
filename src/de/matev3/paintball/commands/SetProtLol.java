package de.matev3.paintball.commands;

import de.matev3.paintball.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetProtLol implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("matev3.setprot")) {
                FileConfiguration config = Main.getPlugin().getConfig();
                config.set("prot", player.getLocation().getY());
            } else {
                player.sendMessage("Knecht");
            }
        } else {
            sender.sendMessage("nope");
        }

        return false;
    }
}
