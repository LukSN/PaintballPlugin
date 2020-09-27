package de.matev3.paintball.commands;

import de.matev3.paintball.main.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class addSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("matev3.addloc")) {
                
            } else {
                player.sendMessage("Knecht");
            }
        } else {
            commandSender.sendMessage("nope");
        }

        return false;
    }
}
