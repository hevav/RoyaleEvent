package dev.hevav.royaleevent.commands;

import dev.hevav.royaleevent.RoyaleEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoyaleMiddle implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Location location = ((Player) commandSender).getLocation();
            RoyaleEvent.config.set("midXcord", (int) location.getX());
            RoyaleEvent.config.set("midYcord", (int) location.getY());
            RoyaleEvent.config.set("midZcord", (int) location.getZ());
            commandSender.getServer().broadcastMessage(ChatColor.GOLD + "[RE] Центр поставлен");
        }
        return true;
    }
}
