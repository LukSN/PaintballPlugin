package de.matev3.paintball.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatWrite implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String Message  = event.getMessage();
        Message.replace("%", "Prozant");

        if (player.getName().equals("Le_Crore")) {
            event.setFormat("§7[§cOWNER§7] " + player.getName() + "§8 > §f" + Message);
        } else if (player.getName().equals("_LukSN_") || player.getName().equals("Pauyl")) {
            event.setFormat("§7[§3DEV§7] " + player.getName() + "§8 > §7" + Message);
        }else if (player.getName().equals("Phileas13") || player.getName().equals("TheTitanico") || player.getName().equals("_SaloKing_") || player.getName().equals("Titaanicooo")) {
            event.setFormat("§7[§3TEAM§7] " + player.getName() + "§8 > §7" + Message);
        } else {
            event.setFormat("§7[§8Spieler§7] " + player.getName() + "§8 > §7" + Message);
        }
    }

}